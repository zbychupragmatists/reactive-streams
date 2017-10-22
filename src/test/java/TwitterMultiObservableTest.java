import org.junit.Test;
import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.Subscriptions;
import twitter4j.*;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class TwitterMultiObservableTest {

    @Test
    public void get_from_twitter() throws Exception {
        LazyTwitterObservable lazyTwitterObservable = new LazyTwitterObservable();

        Observable<Status> observable = lazyTwitterObservable.observe();
        observable.subscribe(

        );
    }

    class LazyTwitterObservable {
        private final Set<Subscriber<? super Status>> subscribers = new CopyOnWriteArraySet();
        private final TwitterStream twitterStream;

        public LazyTwitterObservable() {
            this.twitterStream = new TwitterStreamFactory().getInstance();
            twitterStream.addListener(new StatusListener() {
                @Override
                public void onStatus(Status status) {
                    subscribers.forEach(s -> s.onNext(status));
                }

                @Override
                public void onException(Exception e) {
                    subscribers.forEach(s -> s.onError(e));
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
            });
        }

        private final Observable<Status> observable = Observable.create(
                subscriber -> {
                    register(subscriber);
                    subscriber.add(Subscriptions.create(() -> this.deregister(subscriber)));
                }
        );

        private synchronized void deregister(Subscriber<? super Status> subscriber) {
            subscribers.remove(subscriber);
            if (subscribers.isEmpty()) {
                twitterStream.shutdown();
            }
        }

        private synchronized void register(Subscriber<? super Status> subscriber) {
            if (subscribers.isEmpty()) {
                subscribers.add(subscriber);
                twitterStream.sample();
            } else {
                subscribers.add(subscriber);
            }
        }

        public Observable<Status> observe() {
            return observable;
        }
    }


}
