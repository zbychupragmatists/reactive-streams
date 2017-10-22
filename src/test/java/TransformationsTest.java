import org.junit.Test;
import rx.Observable;

public class TransformationsTest {

    @Test
    public void map_and_filter() throws Exception {

        Observable.just(8, 9, 10)
                .filter(integer -> integer % 3 > 0)
                .map(integer -> "#" + integer * 10)
                .filter(s -> s.length() < 4)
                .subscribe(s -> {
                    Utils.log(s);
                })
        ;
    }
}
