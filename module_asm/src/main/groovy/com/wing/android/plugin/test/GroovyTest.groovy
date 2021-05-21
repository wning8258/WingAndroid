println("hello groovy!!!")  //执行命令：groovy GroovyTest.groovy,直接输出结果
def age = 23  //不指定类型
def name = "xiaoming"
int num = 3 //指定类型
println(age + "," +name + "," + num)

String testFunction(arg1,arg2){//无需指定参数类型 ..
    //return arg1 + arg2
    arg1 + arg2  //可以不加return
}
//没有指定函数返回类型的话，必须加def,最后一行会作为函数返回值
def testFunction2(arg1,arg2) {
     def a = arg1 +arg2
}

println(testFunction(1,2))
println(testFunction(1,"2"))
println(testFunction2(1,2))
println(testFunction2(1,"2"))

isMale = 111
if (isMale) {
    println("isMale")
} else {
    println("isFemale")
}

int aaaa =3
def str1 = 'str1 $aaaa'  //单引号不会转义，直接输出
def str2 = "str1 $aaaa"   //双引号会进行表达式取值
println("str1 is : " + str1)
println("str2 is :" + str2)
def str3 ='''
111
222
333
'''
println("str3 is :" + str3)