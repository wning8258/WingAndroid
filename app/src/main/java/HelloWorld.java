public class HelloWorld {

    public static int i;

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
    }
}
