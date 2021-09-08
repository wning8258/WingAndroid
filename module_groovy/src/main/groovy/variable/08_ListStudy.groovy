package variable
/**
 * 数据结构
 */
def list = new ArrayList() //java的定义方式
def list2 = [] //groovy的定义方式
def list3 = [1,2,3,4,5]  //arrayList
def array  = [1,2,3,4,5] as int[] //通过as转成数组
int[] array2 = [1,2,3,4,5] //强类型定义为数组

list3.add(6)
list3.leftShift(7)  //添加元素7
println(list3.toListString())  //[1, 2, 3, 4, 5, 6, 7]
list3<< 8  //添加元素8
println(list3.toListString())  //[1, 2, 3, 4, 5, 6, 7,8]
println( list3 - 7)   //减去元素7
//[1, 2, 3, 4, 5, 6, 8]


/**
 * 列表的排序
 */
def sortList = [6, -3 ,9 ,2 ,-7, 1, 5]
Collections.sort(sortList)  //java api
println(sortList)  //[-7, -3, 1, 2, 5, 6, 9]

Comparator comparator = {
    a,b -> a==b? 0:Math.abs(a)<Math.abs(b)? -1 :1
}
Collections.sort(sortList,comparator)  //java api
println(sortList)  //[1, 2, -3, 5, 6, -7, 9]

/**
 * Groovy的排序
 */
//直接使用DefaultGroovyMethods定义的sort方法
sortList.sort()
println(sortList)  //[-7, -3, 1, 2, 5, 6, 9]
//通过闭包定义排序
sortList.sort {
    a,b -> a==b? 0:Math.abs(a)<Math.abs(b)? 1 :-1
}
println(sortList)  //[9, -7, 6, 5, -3, 2, 1]
//排序字符串list
def sortStringList =['abc','z','hello','groovy','java']
sortStringList.sort()
println(sortStringList)  //[abc, groovy, hello, java, z]
sortStringList.sort{
    return it.size()  //根据字符长度大小排序
}
println(sortStringList)  //sortStringList

/**
 * 列表的查找
 */
def findList = [-3,9,6,2,-7,1,5]
println findList.find {
    it > 5
} // 9
def findAllList = findList.findAll {
    it > 2
}
println(findAllList.toListString())  //[9, 6, 5]
println findList.any{
    it>5
} //true
println findList.every{
    it>5
} //false
println findList.max()  //9
println findList.max {
    it < 0  //小于0的最大值
}  // -3

println findList.count {
    it % 2== 0
}  //2