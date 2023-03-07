package com.example;




import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

public class WingProcessor extends AbstractProcessor{

    /** Element操作类 */
    private Elements mElementUtils;
    /** 类信息工具类 */
    private Types mTypeUtils;
    /** 日志工具类 */
    private Messager mMessager;
    /** 文件创建工具类 */
    private Filer mFiler;

    /** 节点信息缓存 */
    private Map<String, List<NodeInfo>> mCache = new HashMap<>();

    /** 初始化 */
    @Override
    public synchronized void init(ProcessingEnvironment environment) {
        super.init(environment);
        // 获取相关工具
        mElementUtils = environment.getElementUtils();
        mTypeUtils = environment.getTypeUtils();
        mMessager = environment.getMessager();
        mFiler = environment.getFiler();

        // 打印Build提示
        mMessager.printMessage(Diagnostic.Kind.NOTE, "开始处理自定义 @Wing 注解");
    }


    /** 处理注解 */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // 判断是否有使用 @BindView 注解
        if (annotations != null && !annotations.isEmpty()) {
            // 获取所有 @BindView 节点
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Wing.class);
            // 判断节点集合不为空
            if (elements != null && !elements.isEmpty()) {
                // 循环遍历节点
                for (Element element : elements) {
                    // 获取节点包信息
                    String packageName = mElementUtils.getPackageOf(element).getQualifiedName().toString();
                    // 获取节点类信息，由于 @BindView 作用于成员属性上，所以这里使用 getEnclosingElement() 获取父节点信息
                    String className = element.getEnclosingElement().getSimpleName().toString();
                    // 获取节点类型
                    String typeName = element.asType().toString();
                    // 获取节点标记的属性名称
                    String simpleName = element.getSimpleName().toString();
                    // 获取注解的值
                    int value = element.getAnnotation(Wing.class).value();

                    // 打印
                    mMessager.printMessage(Diagnostic.Kind.NOTE, "packageName：" + packageName);
                    mMessager.printMessage(Diagnostic.Kind.NOTE, "className：" + className);
                    mMessager.printMessage(Diagnostic.Kind.NOTE, "typeName：" + typeName);
                    mMessager.printMessage(Diagnostic.Kind.NOTE, "simpleName：" + simpleName);
                    mMessager.printMessage(Diagnostic.Kind.NOTE, "value：" + value);

                    // 缓存KEY
                    String key = packageName + "." + className;
                    // 缓存节点信息
                    List<NodeInfo> nodeInfos = mCache.get(key);
                    // 判断是否为空
                    if (nodeInfos == null) {
                        // 初始化
                        nodeInfos = new ArrayList<>();
                        // 载入
                        nodeInfos.add(new NodeInfo(packageName, className, typeName, simpleName, value));
                        // 缓存
                        mCache.put(key, nodeInfos);
                    } else {
                        // 载入
                        nodeInfos.add(new NodeInfo(packageName, className, typeName, simpleName, value));
                    }
                }
                // 判断临时缓存是否不为空
                if (!mCache.isEmpty()) {
                    // 遍历临时缓存文件
                    for (Map.Entry<String, List<NodeInfo>> stringListEntry : mCache.entrySet()) {
                        try {
                            // 创建文件
                            createFile(stringListEntry.getValue());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                return true;
            }
        }

        return false;
    }

    /** 创建Java文件 */
    private void createFile(List<NodeInfo> infos) throws IOException {
        // 获取第一个节点用来获取信息
        NodeInfo info = infos.get(0);
        // 生成的类名称
        String className = info.getClassName() + "$$ViewBinding";
        // JavaFileObject
        JavaFileObject file = mFiler.createSourceFile(info.getClassName() + "." + className);
        // Writer
        Writer writer = file.openWriter();
        // 设置包路径
        writer.write("package " + info.getPackageName() + ";\n\n");
        // 设置类名称
        writer.write("public class " + className + " {\n\n");
        writer.write("\tpublic static void bind(" + info.getClassName() + " target) {\n");
        // 循环遍历设置方法体
        for (NodeInfo node : infos) {
            writer.write("\t\ttarget." + node.getNodeName() + " = (" + node.getTypeName() +
                    ") target.findViewById(" + node.getValue() + ");\n");
        }
        writer.write("\t}\n");
        writer.write("}");
        // 关闭
        writer.close();
    }


//    @Override
//    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        Messager messager = processingEnv.getMessager();
//        Set<? extends Element> routeElements = roundEnv.getElementsAnnotatedWith(Wing.class);
//        for (Element e : routeElements) {
//            messager.printMessage(Diagnostic.Kind.NOTE, "WingProcessor Printing: " + e.toString());
//
//        }
//
////        for (TypeElement te : annotations) {
////            for (Element e : roundEnv.getElementsAnnotatedWith(te)) {
////                messager.printMessage(Diagnostic.Kind.NOTE, "WingProcessor Printing: " + e.toString());
////            }
////        }
//
//        // 创建Java类【你的类名】
//        TypeSpec autoClass = TypeSpec.classBuilder("AutoClass").build();
//
//        // 创建Java文档【这里定义了你的包名，随便写即可】
//        JavaFile javaFile = JavaFile.builder("com.apt.demo", autoClass).build();
//
//        // 将文档写入
//        try {
//            javaFile.writeTo(processingEnv.getFiler());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return true;
//    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(Wing.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
