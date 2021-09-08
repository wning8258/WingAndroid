package variable
/**
 * 闭包
 */
def closure1 = {println("hello closure")}
closure1.call() //hello closure
closure1()  //hello closure

//it是隐式参数，默认叫it
def closure11 = {println("hello ${it}")}
closure11() //hello null
closure11("xiaoming")  //hello xiaoming

def closure2 = {int x,int y ->println("result is ${x+y}")}
closure2(1,2)  // result is 3
//可以不指定类型
def closure3 = { x,y ->println("result is ${x+y}")}
closure3(1,2)  // result is 3

//最后一行，作为返回值
def closure4 = {name -> "hello"}
def result = closure4()
print("result ${result}")