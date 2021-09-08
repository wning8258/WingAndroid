package variable
/**
 * 闭包和String结合使用
 */
String str = "the 2 and 3 is 5"
//each的遍历
def result = str.each {
    //每个字符输出两次
    print it.multiply(2)  //tthhee  22  aanndd  33  iiss  55
}
println("\nresult is :${result}" )  //result is :the 2 and 3 is 5

//find
println "str first number is " + str.find {
    it.isNumber()
}  //str first number is 2

//findAll
def list = str.findAll {
    it.isNumber()
}
println("str all number is :${list.toListString()}")  // str all number is :[2, 3, 5]

//any 只要有一个字符满足，就返回true
println "str contains number :" +str.any{
    it.isNumber()
}  //str contains number :true

//every 所有字符满足，才返回true
println"str is all number :" +str.every{
    it.isNumber()
}  //str is all number :false

//对str进行闭包操作，返回一个arrayList
def collectList = str.collect {
    it.toUpperCase()
}
println "str to upper case is :${collectList.toListString()}"
//str to upper case is :[T, H, E,  , 2,  , A, N, D,  , 3,  , I, S,  , 5]
