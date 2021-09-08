package file

import com.google.gson.Gson
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import object.Person


/**
 * json
 */
def list =[new Person(name:'John',age:25), new Person(name:'Tom',age:36)]
/**
 * 使用JsonOutput.toJson 输出json字符串
 */
def json = JsonOutput.toJson(list)
println(json)  //[{"age":25,"name":"John"},{"age":36,"name":"Tom"}]

def print = JsonOutput.prettyPrint(json)
println(print)
//[
//    {
//        "age": 25,
//        "name": "John"
//    },
//    {
//        "age": 36,
//        "name": "Tom"
//    }
//]

/**
 * json字符串转换成实体对象(parseText parse)
 */
def slurper = new JsonSlurper()
def text = slurper.parseText(json)
println(text)  //[[age:25, name:John], [age:36, name:Tom]]

//使用第三方的gson库也可以
Gson gson = new Gson()
def json1 = gson.toJson(list)
println(json1)  //[{"name":"John","age":25},{"name":"Tom","age":36}]


def getNetWorkData(String url) {
    //发送http请求
    def connection = new URL(url).openConnection()
    connection.setRequestMethod("GET")
    connection.connect()  //阻塞当前进程（安卓不能这么做)
    def response = connection.content.text
    //将json转换为实体对象
    def slurper = new JsonSlurper()
    return slurper.parseText(response)
}