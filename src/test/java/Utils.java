public class Utils {

    public static void log(Object o) {
        printWithThread(o.toString());
    }

    public static void noMore() {
        printWithThread("No more");
    }

    private static void printWithThread(String msg) {
        System.out.println(Thread.currentThread().getName() + ": " + msg);
    }
}
