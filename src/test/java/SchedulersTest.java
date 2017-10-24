import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Test;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class SchedulersTest {

    ExecutorService poolA = Executors.newFixedThreadPool(10, newThreadFactory("Sched-A-%d"));
    Scheduler schedulerA = Schedulers.from(poolA);

    @Test
    public void no_schedulers() throws Exception {
        Utils.log("Starting");
        Observable<String> obs = simple();
        Utils.log("Created");
        Observable<String> obs2 = obs
                .map(x -> x)
                .filter(x -> true);
        Utils.log("Transformed");
        obs2.subscribe(
                s -> Utils.log(s),
                throwable -> throwable.printStackTrace(),
                () -> Utils.log("Completed")
        );
        Utils.log("Exiting");
    }

    @Test
    public void subscribe_on() throws Exception {
        Utils.log("Starting");
        Observable<String> obs = simple();
        Utils.log("Created");
        obs
                .subscribeOn(schedulerA)
//                .map(s -> s + "1")
//                .doOnNext(s -> Utils.log(s))
                .subscribe(
                        s -> Utils.log(s),
                        throwable -> throwable.printStackTrace(),
                        () -> Utils.log("Completed")
                );
        Utils.log("Exiting");
        Thread.sleep(500);
    }

    @Test
    public void observe_on() throws Exception {
        Utils.log("Starting");
        Observable<String> obs = simple();
        Utils.log("Created");
        obs
                .doOnNext(s -> Utils.log("1: " + s))
                .map(s -> s + "1")
                .observeOn(schedulerA)
                .doOnNext(s -> Utils.log("2: " + s))
                .subscribe(
                        s -> Utils.log(s),
                        throwable -> throwable.printStackTrace(),
                        () -> Utils.log("Completed")
                );
        Utils.log("Exiting");
        Thread.sleep(500);
    }

    private Observable<String> simple() {
        return Observable.unsafeCreate(subscriber -> {
            subscriber.onNext("A");
            subscriber.onNext("B");
            subscriber.onCompleted();
        });
    }

    private ThreadFactory newThreadFactory(String format) {
        return new ThreadFactoryBuilder().setNameFormat(format).build();
    }


}
