import org.junit.Test;
import rx.Observable;


public class ReactiveTest {

    @Test
    public void tweets() throws Exception {
        Observable<Tweet> tweetObservable = newTweetsStream();

        tweetObservable.subscribe(
                (Tweet tweet) -> System.out.println(tweet)
        );
    }

    private Observable<Tweet> newTweetsStream() {
        return Observable.just(
                new Tweet("zbychu", "Legia rules"),
                new Tweet("Lech fanboy", "Legia kurczak"),
                new Tweet("ultras", "Tylko Legia")
        );
    }


}
