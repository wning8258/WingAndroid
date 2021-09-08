package object

def person = new Person()
//MissingMethodException
println person.cry()  //Caught: groovy.lang.MissingMethodException: No signature of method: object.Person.cry()

/**
 * 为类动态的添加一个属性
 */
Person.metaClass.sex = 'male'

def person2 = new Person()
println("the person2 name is ${person2.name}, the age is ${person2.age}, the sex is ${person2.sex}")
//the person2 name is null, the age is 0, the sex is male

//修改类属性
person2.sex = "female"
println("the person2 name is ${person2.name}, the age is ${person2.age}, the sex is ${person2.sex}")
//the person2 name is null, the age is 0, the sex is female

/**
 * 为类动态的添加方法(metaClass)
 */
Person.metaClass.sexUpperCase = {sex.toUpperCase()}
println person2.sexUpperCase()  //FEMALE
/**
 * 为类动态的添加静态方法(metaClass.static)
 */
Person.metaClass.static.createPerson = { def name1,def age1 ->
    new Person(name:name1,age:age1)
}
def person3 = Person.createPerson("laowu", 15)
println("the person3 name is ${person3.name}, the age is ${person3.age}, the sex is ${person3.sex}")
//the person3 name is laowu, the age is 15, the sex is male