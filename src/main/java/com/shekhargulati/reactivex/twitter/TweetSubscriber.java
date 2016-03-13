package com.shekhargulati.reactivex.twitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Subscriber;
import twitter4j.*;
import twitter4j.conf.Configuration;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

class TweetSubscriber extends StatusAdapter implements Observable.OnSubscribe<Status> {

    private final Logger logger = LoggerFactory.getLogger(TweetSubscriber.class);

    private final FilterQuery twitterFilterQuery;
    private final TwitterStream twitterStream;
    private AtomicReference<Subscriber<? super Status>> subscriberRef = new AtomicReference<>(null);


    TweetSubscriber(String[] languages, String[] searchTerms, long[] usersToFollow) {
        this(null, languages, searchTerms, usersToFollow);
    }

    TweetSubscriber(Configuration configuration, String[] languages, String[] searchTerms, long[] usersToFollow) {
        FilterQuery filterQuery = new FilterQuery();
        filterQuery.language(languages);
        filterQuery.track(searchTerms);
        filterQuery.follow(usersToFollow);
        this.twitterFilterQuery = filterQuery;
        twitterStream = Optional.ofNullable(configuration).map(TwitterStreamFactory::new).orElseGet(TwitterStreamFactory::new).getInstance();
    }


    @Override
    public void onException(Exception e) {
        Subscriber<? super Status> subscriber = subscriberRef.get();
        if (subscriber != null && !subscriber.isUnsubscribed()) {
            logger.error("Received exception from Twitter Streaming API", e);
            subscriber.onError(e);
        }
        closeAndCleanup();
    }

    @Override
    public void onStatus(Status status) {
        Subscriber<? super Status> subscriber = subscriberRef.get();
        if (subscriber.isUnsubscribed()) {
            closeAndCleanup();
        } else {
            subscriber.onNext(status);
        }
    }

    @Override
    public void call(Subscriber<? super Status> subscriber) {
        if (!subscriberRef.compareAndSet(null, subscriber)) {
            return;
        }
        twitterStream.addListener(this);
        twitterStream.filter(twitterFilterQuery);

    }

    private void closeAndCleanup() {
        logger.info("Inside closeAndCleanup");
        if (twitterStream != null) {
            twitterStream.shutdown();
        }
        Subscriber<? super Status> subscriber = subscriberRef.get();
        if (subscriber != null && !subscriber.isUnsubscribed()) {
            subscriber.onCompleted();
            subscriber.unsubscribe();
        }
    }

}