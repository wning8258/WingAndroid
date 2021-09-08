package variable
def students = [
        1:[number:'001', name:"zhangsan",score:55,sex:"male"],
        2:[number:"002", name:"lisi",score:62,sex:"female"],
        3:[number:"003", name:"wangwu",score:73,sex:"female"],
        4:[number:"004", name:"zhaoliu",score:66,sex:"male"],
]
//遍历
students.each {
    println it.key + "," + it.value
}
//1,[number:001, name:zhangsan, score:55, sex:male]
//2,[number:002, name:lisi, score:62, sex:female]
//3,[number:003, name:wangwu, score:73, sex:female]
//4,[number:004, name:zhaoliu, score:66, sex:male]

//带索引的遍历
students.eachWithIndex { def entry, int i ->
    println entry.key + "," + entry.value +"," + i
}
//遍历，直接取得key value
students.each{key,value ->
    println key + "," + value
}
//遍历，直接取得key value,index
students.eachWithIndex{key,value,index ->
    println key + "," + value + ",index :" + index
}

//查找操作

// find
def find = students.find { stu ->
    stu.value.score >= 60
}
println(find)  //2={number=002, name=lisi, score=62, sex=female}
//findall
def all = students.findAll { def stu ->
    stu.value.score >= 60
}
println(all) //[2:[number:002, name:lisi, score:62, sex:female], 3:[number:003, name:wangwu, score:73, sex:female], 4:[number:004, name:zhaoliu, score:66, sex:male]]

//count
def count = students.count {
    it.value.score >= 60
}
println count //3
/**
 * collect
 */
def collect = students.findAll {
    it.value.score >= 60
}.collect {
    return it.value.name
}
println(collect) //[lisi, wangwu, zhaoliu]
/**
 * groupBy 分组
 */
def group = students.groupBy {
    it.value.score >= 60? "及格":"不及格"
}
println(group)
//[不及格:[1:[number:001, name:zhangsan, score:55, sex:male]],
// 及格:[2:[number:002, name:lisi, score:62, sex:female], 3:[number:003, name:wangwu, score:73, sex:female], 4:[number:004, name:zhaoliu, score:66, sex:male]]]

/**
 * 排序
 */
def sort = students.sort { def stu1, def stu2 ->
    stu1.value.score > stu2.value.score? 1: -1  //从小到大排序
}
println sort
//[1:[number:001, name:zhangsan, score:55, sex:male],
// 2:[number:002, name:lisi, score:62, sex:female],
// 4:[number:004, name:zhaoliu, score:66, sex:male],
// 3:[number:003, name:wangwu, score:73, sex:female]]