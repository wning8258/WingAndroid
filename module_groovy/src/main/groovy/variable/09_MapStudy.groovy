package variable
def map = new HashMap()
//key不带单引号，跟带单引号是等同的
def colors =[red:"ff0000",green:"#00ff00",blue:"#0000ff"]
//索引
println colors["red"] //ff0000
println(colors.red)  //ff0000

//添加
colors.yellow = 'ffff00'
println colors.yellow  //ffff00
println colors.toString()  //[red:ff0000, green:#00ff00, blue:#0000ff, yellow:ffff00]
println colors.toMapString()  //[red:ff0000, green:#00ff00, blue:#0000ff, yellow:ffff00]

//添加其他类型的元素
colors.complex = [a:1, b:2]
println colors.toMapString()   //[red:ff0000, green:#00ff00, blue:#0000ff, yellow:ffff00, complex:[a:1, b:2]]

println( colors.getClass())  //class java.util.LinkedHashMap
println colors.class  //null !!!!这里会取查找key为class的value值，所以输出null




