import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ThreadFactory;

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

    static void sleepMillis(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static ThreadFactory newThreadFactory(String format) {
        return new ThreadFactoryBuilder().setNameFormat(format).build();
    }
}
