package com.shekhargulati.reactivex.twitter;

import rx.Observable;
import twitter4j.Status;
import twitter4j.conf.Configuration;

public interface TweetStream {

    long[] DEFAULT_USERS_TO_TRACK = new long[0];
    String[] DEFAULT_LANGUAGES_TO_TRACK = new String[]{"en"};
    String[] DEFAULT_SEARCH_TERMS_TO_TRACK = new String[0];

    static Observable<Status> of(final String... searchTerms) {
        return of(null, DEFAULT_LANGUAGES_TO_TRACK, searchTerms);
    }

    static Observable<Status> of(final long... usersToFollow) {
        return of(null, usersToFollow);
    }

    static Observable<Status> of(final String[] languages, final String[] searchTerms) {
        return of(null, languages, searchTerms);
    }

    static Observable<Status> of(final Configuration configuration, final String[] languages, final String[] searchTerms) {
        return of(configuration, languages, searchTerms, DEFAULT_USERS_TO_TRACK);
    }

    static Observable<Status> of(final Configuration configuration, final String... searchTerms) {
        return of(configuration, DEFAULT_LANGUAGES_TO_TRACK, searchTerms, DEFAULT_USERS_TO_TRACK);
    }

    static Observable<Status> of(final Configuration configuration, final long... usersToFollow) {
        return of(configuration, DEFAULT_LANGUAGES_TO_TRACK, DEFAULT_SEARCH_TERMS_TO_TRACK, usersToFollow);
    }

    static Observable<Status> of(final Configuration configuration, final String[] languages, final String[] searchTerms, final long[] usersToFollow) {
        return Observable.create(new TweetSubscriber(configuration, languages, searchTerms, usersToFollow)).share();
    }


}