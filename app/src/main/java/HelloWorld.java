public class HelloWorld {

    public static int i;

    public volatile  int a;

    private long count = 0;

    public void addCount() {
        synchronized (this) {
            count = count+1;
        }
    }

    public static void main(String[] args) {
        System.out.println("hello");
        String a = "hello";

        i++;
        /**
         *     GETSTATIC HelloWorld.i : I
         *     ICONST_1
         *     IADD
         *     PUTSTATIC HelloWorld.i : I
         */
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.work();
    }

    private int work() {
        int x = 3;
        int y = 5;
        int z = (x + y) * 10;
        return z;
    }
}
