import org.junit.Test;
import rx.Observable;
import rx.Subscriber;

public class CreateObservableTest {

    @Test
    public void simple() throws Exception {
        Utils.log("Before");
        Observable.range(5, 3)
                .subscribe(i -> Utils.log(i));
        Utils.log("After");
    }

    @Test
    public void simple_2() throws Exception {
        Utils.log("Before");
        intsStream()
            .subscribe(integer -> Utils.log(integer));
        Utils.log("After");
    }

    @Test
    public void just_implementation() throws Exception {

    }

    private Observable<Integer> intsStream() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                Utils.log("create");
                subscriber.onNext(5);
                subscriber.onNext(6);
                subscriber.onNext(7);
                subscriber.onCompleted();
                Utils.log("completed");
            }
        });
    }
}
