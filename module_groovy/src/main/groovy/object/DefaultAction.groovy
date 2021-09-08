package object

trait DefaultAction {
    abstract void eat()
    void play() {
        println("i play")
    }
}