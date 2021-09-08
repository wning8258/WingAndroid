package object

def person = new Person()
def person1 = new Person(name:"张三", age:26)
def person2 = new Person(age:26)

//其实调用的getName,getAge方法，并不是用的属性
println("the person name is ${person.name}, the age is ${person.age}")
println("the person1 name is ${person1.name}, the age is ${person1.age}")
println("the person2 name is ${person2.name}, the age is ${person2.age}")
//the person name is null, the age is 0
//the person1 name is 张三, the age is 26
//the person2 name is null, the age is 26

person.increaseAge(10)
println("the person name is ${person.name}, the age is ${person.age}")
//the person name is null, the age is 10
