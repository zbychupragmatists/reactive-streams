import org.junit.Test;
import rx.Observable;
import rx.subscriptions.Subscriptions;
import twitter4j.*;

public class TwitterObservableTest {

    @Test
    public void get_from_twitter() throws Exception {
        Observable<Status> tweetsStream = observe();
        tweetsStream.subscribe(status -> Utils.log(status.getText()));
    }

    private Observable<Status> observe() {
        return Observable.create(subscriber -> {
            TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
            twitterStream.addListener(new StatusListener() {
                @Override
                public void onStatus(Status status) {
                    subscriber.onNext(status);
                }

                @Override
                public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

                }

                @Override
                public void onTrackLimitationNotice(int i) {

                }

                @Override
                public void onScrubGeo(long l, long l1) {

                }

                @Override
                public void onStallWarning(StallWarning stallWarning) {

                }

                @Override
                public void onException(Exception e) {
                    subscriber.onError(e);
                }
            });

            twitterStream.sample();
            subscriber.add(Subscriptions.create(twitterStream::shutdown));
        });
    }
}
