import org.junit.Test;
import rx.Observable;

public class SchedulersTest {

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
        Utils.log("Completed");
    }

    @Test
    public void subscribe_on() throws Exception {

    }

    private Observable<String> simple() {
        return Observable.unsafeCreate(subscriber -> {
            subscriber.onNext("A");
            subscriber.onNext("B");
            subscriber.onCompleted();
        });
    }
}
