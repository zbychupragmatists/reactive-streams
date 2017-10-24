import org.junit.Test;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

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
        cars
                .flatMap(carPhoto -> recognize(carPhoto))
                .subscribe(licensePlate -> Utils.log(licensePlate), throwable -> Utils.log(throwable));
        Thread.sleep(300);
    }

    @Test
    public void flatMapConcurrent() throws Exception {
        // wrocimy

    }

    private Observable<LicensePlate> recognize(CarPhoto carPhoto) {
        if (carPhoto.name.equals("A")) {
            return Observable.just(new LicensePlate("A 123"));
        } else if (carPhoto.name.equals("B")) {
            return Observable.just(new LicensePlate("B 456"));
//                    .delay(10, TimeUnit.MILLISECONDS); // do not preserve order
        }
        return Observable.error(new RuntimeException("not recognised"));
    }


    private Observable<CarPhoto> cars() {
        return Observable.just(
                new CarPhoto("A"),
                new CarPhoto("B"),
                new CarPhoto("C")
        );
    }

    private class CarPhoto {
        private String name;

        public CarPhoto(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("CarPhoto{");
            sb.append("name='").append(name).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    private class LicensePlate {
        private String number;

        public LicensePlate(String number) {
            this.number = number;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("LicensePlate{");
            sb.append("number='").append(number).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
