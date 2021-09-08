package file

import groovy.xml.XmlSlurper
import groovy.xml.slurpersupport.GPathResult

final String xml = '''
    <response version-api="2.0">
        <value>
            <books id="1" classification="android">
                <book available="20" id="1">
                    <title>疯狂Android讲义</title>
                    <author id="1">李刚</author>
                </book>
                <book available="14" id="2">
                   <title>第一行代码</title>
                   <author id="2">郭林</author>
                </book>
                <book available="13" id="3">
                   <title>Android开发艺术探索</title>
                   <author id="3">任玉刚</author>
                </book>
                <book available="5" id="4">
                   <title>Android源码设计模式</title>
                   <author id="4">何红辉</author>
                </book>
           </books>
           <books id="2" classification="web">
               <book available="10" id="1">
                   <title>Vue从入门到精通</title>
                   <author id="4">李刚</author>
               </book>
           </books>
       </value>
    </response>
'''
//开始解析xml数据
def slurper = new XmlSlurper()
def response = slurper.parseText(xml)
println(response.value.books[0].book[0].title.text())  //疯狂Android讲义
println(response.value.books[1].book[0].author.text()) //李刚
/**
 * 直接通过@+属性名，访问具体的属性值
 */
println(response.value.books[1].book[0].@available)  //10
/**
 * 遍历
 */
def list = []
response.value.books.each{ books ->
    //下边开始对书进行遍历
    books.book.each { book ->
        def author = book.author.text()
        if (author.equals("李刚")) {  //取得所有书名为李刚的书
            list.add(book.title.text())
        }
    }
}
println(list.toListString())  //[疯狂Android讲义, Vue从入门到精通]

/**
 * 深度遍历
 */
def all = response.depthFirst().findAll { book ->
     book.author.text().equals("李刚")
}
println(all.toListString())  //[疯狂Android讲义李刚, Vue从入门到精通李刚]
println(all[0].getClass())  //class groovy.xml.slurpersupport.NodeChild

/**
 * 广度遍历
 */
def name = response.value.books.breadthFirst().findAll { node ->
    node.name = "book" && node.@id =='2'  //id为二的书
}.collect {
    it.title.text()
}
println(name)  //[第一行代码]
