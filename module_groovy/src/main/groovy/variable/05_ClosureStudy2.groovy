package variable
/**
 * 闭包和基本类型结合使用
 */
int x = fab(5)
println(x)  //120
x = fab2(5)
println(x)  //120
x = calc(101)
println(x)  //5050
/**
 * 用来求制定num的阶乘
 * @param number
 * @return
 */
int fab(int number) {
    int result = 1
    1.upto(number,{num-> result *=num})
    return result
}

int fab2(int number) {
    int result = 1
    number.downto(1) {
        num-> result *= num
    }
    return result
}

int calc(int number) {
    int result = 0
    number.times {
        num -> result+= num
    }
    return result
}
