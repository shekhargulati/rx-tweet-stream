rx-twitter-stream [![Build Status](https://travis-ci.org/shekhargulati/rx-tweet-stream.svg?branch=master)](https://travis-ci.org/shekhargulati/rx-tweet-stream) [![License](https://img.shields.io/:license-mit-blue.svg)](./LICENSE.txt)
-----

RxJava observable for tweet stream. It is a wrapper around Twitter4J Streaming Library. rx-twitter-stream API uses JDK 8.

Getting Started
--------

To use `rx-tweet-stream` in your application, you have to add `rx-tweet-stream` in your classpath. rx-tweet-stream is available on Maven Central so you just need to add dependency to your favorite build tool as show below.

For Apache Maven users, please add following to your pom.xml.

```xml
<dependencies>
    <dependency>
        <groupId>com.shekhargulati.reactivex</groupId>
        <artifactId>rx-tweet-stream</artifactId>
        <version>0.1.0</version>
        <type>jar</type>
    </dependency>
</dependencies>
```

Gradle users can add following to their build.gradle file.

```groovy
compile(group: 'com.shekhargulati.reactivex', name: 'rx-tweet-stream', version: '0.1.0', ext: 'jar')
```

## Usage

The example shown below uses the Twitter4j environment variables
 
```
export twitter4j.debug=true
export twitter4j.oauth.consumerKey=*********************
export twitter4j.oauth.consumerSecret=******************************************
export twitter4j.oauth.accessToken=**************************************************
export twitter4j.oauth.accessTokenSecret=******************************************
```

```java
import com.shekhargulati.reactivex.twitter.TweetStream;

TweetStream.of("java").map(Status::getText).take(10).subscribe(System.out::println);
```

If you don't want to use environment variables, then you can use the overloaded method that allows you to pass configuration object.

```java
ConfigurationBuilder cb = new ConfigurationBuilder();
cb.setDebugEnabled(true)
        .setOAuthConsumerKey("*********************")
        .setOAuthConsumerSecret("******************************************")
        .setOAuthAccessToken("**************************************************")
        .setOAuthAccessTokenSecret("******************************************");

TweetStream.of(cb.build(), "java").map(Status::getText).take(10).subscribe(System.out::println);
```

The other `of` factory methods present in `TweetStream` are:

```java
Observable<Status> of(final long... usersToFollow)
Observable<Status> of(final String[] languages, final String[] searchTerms)
Observable<Status> of(final Configuration configuration, final String[] languages, final String[] searchTerms)
Observable<Status> of(final Configuration configuration, final String... searchTerms)
Observable<Status> of(final Configuration configuration, final long... usersToFollow)
Observable<Status> of(final Configuration configuration, final String[] languages, final String[] searchTerms, final long[] usersToFollow)
```
 

License
-------

rx-tweet-stream is licensed under the MIT License - see the `LICENSE` file for details.
