package variable
/**
 * groovy没有基本类型，都会装包成对象类型
 */
byte x = 1
println(x.class)  //class java.lang.Byte
int a = 10
println a.class  //class java.lang.Integer
double b = 10.0
println b.class  //class java.lang.Double

def x1 = 11
println(x1.class)  //class java.lang.Integer

x1 = "abc"   //def可以随时转换类型
println(x1.class)  //class java.lang.String

def y1 = 3.14f
println(y1.class)   //class java.lang.Float
def y2 = 3.14d
println(y2.class)  //class java.lang.Double
def y3 = 3.14
println(y3.class)  //class java.math.BigDecimal


