package variable
/**
 * 范围Range 其实是list
 * public interface Range<T extends Comparable> extends List<T>
 *
 */
def range = 1..10
println(range[0]) //1
println range.contains(10)  //true
println range.from //1
println range.to //10
//遍历
range.each {
    print(it)  //12345678910
}
println()
for (i in range) {
    print(i) //12345678910
}
println()
def getGrade(Number number) {
    def result
    switch (number) {
        case 0..<60:
            result = "不及格"
            break
        case 60..<70:
            result = "及格"
            break
        case 70..<80:
            result = "良好"
            break
        case 80..100:
            result ="优秀"
            break
    }
    result //return可以考虑
}

def result =getGrade(75)
println(result)  //良好