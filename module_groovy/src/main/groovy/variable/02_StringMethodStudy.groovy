package variable
/**===========================字符串的方法========**/
def str = "groovy"
// 用空格字符把str填充到10个字符，原有的str保持居中
//center不传第二个参数，默认使用空格
println(str.center(10))   //  groovy
// 用a字符把str填充到10个字符，原有的str保持居中
println(str.center(10,"a"))  //aagroovyaa

// 用a字符把str填充到10个字符,追加的a在左边
println(str.padLeft(10,"a"))  //aaaagroovy
// 用a字符把str填充到10个字符,追加的a在右边
println(str.padRight(10,"a"))  //groovyaaaa

def str2 = "Hello"
println(str > str2)  //true
//直接通过中括号下标，可以获取str的字符
println(str[1])  //r
println(str[0..1]) //gr

def str3 = "Hello Groovy"
def str4 = "Hello Groovy Hello"
def str5 = "Hello"

//从str3中减str5，剩下的是groovy
println(str3.minus(str5)) // Groovy
println(str3 - str5) // Groovy
//只减去一个hello
println(str4.minus(str5)) // Groovy Hello
//倒序
println(str.reverse())  //yvoorg
//首字母大写
println(str.capitalize())  //Groovy
//是否是数字
println(str.isNumber())  //false

