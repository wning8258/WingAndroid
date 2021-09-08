package variable
//单引号
def name = 'a single string'
println(name.class)  //class java.lang.String
//单引号+斜线转义
def name2 = 'a single \'\' string'  //转义用斜线\
println("name2 ${name2}")  //name2 a single '' string
//3引号
def name3 ='''three string '''
def name4 = '''123
456
789
    10'''
println("name4 ${name4}")    //会保留格式

def name1 = "hello"
println(name1.class)  //class java.lang.String
def doubleName = "this is a commonString ${name1}"
println(doubleName)
//可扩展的string (包含$表达式的)
println(doubleName.class)  //class org.codehaus.groovy.runtime.GStringImpl


def sum = "The sum of 2 and 3  euqals ${2+3}"
println(sum)  //The sum of 2 and 3  euqals 5
println(sum.class)  //class org.codehaus.groovy.runtime.GStringImpl

//可以把gstring转成string
def result = echo(sum)
println("result echo :" + result)

String echo(String message) {
    return message
}

