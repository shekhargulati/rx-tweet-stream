package com.shekhargulati.reactivex.twitter;

import twitter4j.Status;
import twitter4j.conf.ConfigurationBuilder;

public class Main {

    public static void main(String[] args) {

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("*********************")
                .setOAuthConsumerSecret("******************************************")
                .setOAuthAccessToken("**************************************************")
                .setOAuthAccessTokenSecret("******************************************");

        TweetStream.of(cb.build(), "java").map(Status::getText).take(10).subscribe(System.out::println);
    }
}
