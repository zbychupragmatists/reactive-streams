import org.junit.Test;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReactiveBasicsTest {

    @Test
    public void tweets() throws Exception {
        Observable<Tweet> tweetObservable = newTweetsStream();

        tweetObservable.subscribe(
                (Tweet tweet) -> Utils.log(tweet)
        );
        // check also full version
    }

    @Test
    public void tweets_with_error() throws Exception {
        Observable<Tweet> tweetsWithError = newTweetsWithError();

    }

    @Test
    public void tweets_with_observer() throws Exception {
        Observable<Tweet> tweetObservable = newTweetsStream();

    }

    @Test
    public void tweets_with_subscription() throws Exception {
        Observable<Tweet> tweetObservable = newTweetsStream();

    }

    @Test
    public void tweets_with_subscriber() throws Exception {
        // unsubscribe on Lech
        Observable<Tweet> tweetObservable = newTweetsStream();

    }

    private Observable<Tweet> newTweetsStream() {
        return Observable.just(
                new Tweet("zbychu", "Legia rules"),
                new Tweet("Lech fanboy", "Legia kurczak"),
                new Tweet("ultras", "Tylko Legia")
        );
    }

    private Observable<Tweet> newTweetsWithError() {
        return Observable.error(new RuntimeException("Some error"));
    }

}
