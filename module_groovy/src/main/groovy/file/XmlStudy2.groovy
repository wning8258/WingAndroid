package file

import groovy.xml.MarkupBuilder

/**
 * The most commonly used approach for creating XML with Groovy is to use a builder, i.e. one of:

 groovy.xml.MarkupBuilder

 groovy.xml.StreamingMarkupBuilder
 */
def writer = new StringWriter()
def xml = new MarkupBuilder(writer)

xml.records() {
    car(name: 'HSV Maloo', make: 'Holden', year: 2006) {
        //不指定属性名，就可以设置value
        country('Australia')
        record(type: 'speed', 'Production Pickup Truck with speed of 271kph')
    }
    car(name: 'Royale', make: 'Bugatti', year: 1931) {
        country('France')
        record(type: 'price', 'Most Valuable Car at $15 million')
    }
}
println(writer.toString())
//<records>
//  <car name='HSV Maloo' make='Holden' year='2006'>
//    <country>Australia</country>
//    <record type='speed'>Production Pickup Truck with speed of 271kph</record>
//  </car>
//  <car name='Royale' make='Bugatti' year='1931'>
//    <country>France</country>
//    <record type='price'>Most Valuable Car at $15 million</record>
//  </car>
//</records>

println("======================通过bean生成xml==================")
/**
 * 通过bean生成xml
 */
def writer2 = new StringWriter()
def xml2 = new MarkupBuilder(writer2)

def records = new Records()
xml2.records() {
    records.cars.forEach{Car car ->
        car111(name: car.name, make: car.make, year: car.year) {
            country(car.country.value)
            record(type :car.record.type,car.record.value)
        }
    }

}
println(writer2.toString())

class Records {
    def cars = [new Car(), new Car()]
}

class Car {
    String name = "Royale"
    String make = "Bugatti"
    int year = 1931
    Country country = new Country(value:"France")
    Record record = new Record(type:"price", value:"Most Valuable Car at \$15 million")
}
class Country {
    String value
}
class Record {
    String type
    String value
}





/**
 * 保存object到文件 withObjectOutputStream
 * @param object
 * @param destPath
 * @return
 */
def saveObject (Object object, String destPath) {
    try {
        //首先创建目标文件
        def destFile = new File(destPath)
        if (!destFile.exists()) {
            destFile.createNewFile()
        }
        //写对象到文件
        destFile.withObjectOutputStream { ObjectOutputStream out ->
            out.writeObject(object)
        }
        return true
    } catch (Exception e) {
        e.printStackTrace()
    }
    return false
}

saveObject(writer2.toString(), "../../cars.xml")