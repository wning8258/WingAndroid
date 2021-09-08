package variable
/**
 * 闭包（ this owner delegate 委托策略）
 */
def scriptClosure = {
    println "scriptClosure this : ${this}" //this代表闭包定义处的类
    println "scriptClosure owner : ${owner}"  //owner代表闭包定义处的类或对象
    println "scriptClosure delegate : ${delegate}" //delegate 代表任意对象，默认与owner一致
}
scriptClosure.call()
//scriptClosure this : variable.ClosureStudy4@4e423aa2
//scriptClosure owner : variable.ClosureStudy4@4e423aa2
//scriptClosure delegate : variable.ClosureStudy4@4e423aa2


class Person {
    def  classClosure = {
        println "classClosure this : ${this}"
        println "classClosure owner : ${owner}"
        println "classClosure delegate : ${delegate}"
    }
    def  say() {
        def methodClosure = {
            println "methodClosure this : ${this}"
            println "methodClosure owner : ${owner}"
            println "methodClosure delegate : ${delegate}"
        }
        methodClosure.call()
    }
}
Person p = new Person()
p.classClosure.call()
p.say()
//闭包中定义闭包
def nestClosure = {
    def innerClosure = {
        println "innerClosure this :" + this
        println "innerClosure owner "+ owner
        println "innerClosure delegate :" + delegate
    }
    innerClosure()
}
nestClosure.call()
//innerClosure this :variable.07_ClosureStudy4@6a55299e
//innerClosure owner variable.07_ClosureStudy4$_run_closure2@487db668
//innerClosure delegate :variable.07_ClosureStudy4$_run_closure2@487db668

def nestClosure2 = {
    def innerClosure = {
        println "innerClosure this :" + this
        println "innerClosure owner "+ owner
        println "innerClosure delegate :" + delegate
    }
    innerClosure.delegate = p  //修改默认的delegate对象
    innerClosure.call()
}
nestClosure2.call()
//innerClosure this :variable.07_ClosureStudy4@1a6c1270
//innerClosure owner variable.07_ClosureStudy4$_run_closure3@4df5bcb4
//innerClosure delegate :variable.Person@2c1156a7


/**
 * 闭包的委托策略
 */
class Student {
    String name
    def pretty = {"My name is ${name}"}
    String toString() {
        pretty.call()
    }
}
class Teacher {
    String name
}
def stu = new Student(name :"xiaoming")
def tea = new Teacher(name :"teach")
println(stu.toString())  //My name is xiaoming
stu.pretty.delegate  = tea
//修改委托策略，从delegate first中找
stu.pretty.resolveStrategy = Closure.DELEGATE_FIRST
println(stu.toString())  //My name is teach

//仅从delegate中找，如果delegate中没有，会报错
stu.pretty.resolveStrategy = Closure.DELEGATE_ONLY
println(stu.toString())  //My name is teach