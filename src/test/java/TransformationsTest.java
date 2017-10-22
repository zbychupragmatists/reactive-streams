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

        // subscribe informs upstream wants to receive
        // filter does not have any items by itself so it just subsribes to upstream as well
        // map the same
        // up to Observable just
    }

    @Test
    public void flatMap() throws Exception {

        Observable<CarPhoto> cars = cars();
        recognize(new CarPhoto());
    }

    private Observable<LicensePlate> recognize(CarPhoto carPhoto) {
        return null;
    }


    private Observable<CarPhoto> cars() {
        return null;
    }

    private class CarPhoto {
    }

    private class LicensePlate {
    }
}
