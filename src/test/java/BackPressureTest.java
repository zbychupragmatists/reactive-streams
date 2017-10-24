import org.junit.Test;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BackPressureTest {

    ExecutorService poolA = Executors.newFixedThreadPool(10, Utils.newThreadFactory("Sched-A-%d"));
    Scheduler schedulerA = Schedulers.from(poolA);

    @Test
    public void with_backpressure() throws Exception {
        Observable.range(1, 1_000_000)
                .map(Dish::new)
                .observeOn(schedulerA)
                .subscribe(dish -> {
                    Utils.log(dish.number);
                    Utils.sleepMillis(50);
                });
        Utils.sleepMillis(10000);
    }

    @Test
    public void without_backpressure() throws Exception {
        myRange(1, 1_000_000)
                .map(Dish::new)
//                .onBackpressureBuffer()
                .observeOn(schedulerA)
                .subscribe(dish -> {
                    Utils.log(dish);
                    Utils.sleepMillis(10);
                });
        Utils.sleepMillis(10000);
    }

    class Dish {
        private Integer number;

        public Dish(Integer number) {
            this.number = number;
            Utils.log("Created " + number);
        }
    }

    Observable<Integer> myRange(int from, int count) {
        return Observable.unsafeCreate(subscriber -> {
            int i = from;
            while (i < from + count) {
                if (!subscriber.isUnsubscribed()) {
                    Utils.log(i);
                    subscriber.onNext(i++);
                } else {
                    return;
                }
            }
            subscriber.onCompleted();
        });
    }

}
