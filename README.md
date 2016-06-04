Welcome to the JavaMoney Shelter
================================

JavaMoney-shelter is a sandbox or "sanctuary" for new ideas and modules to be adopted, e.g. via Adopt-a-JSR or similar programs.
The shelter also acts as incubator module for testing out new features or modules before they may enter the official javamoney library.
This allows to gain experience and make components error prone and performant and enables a discussion of new features with a broader community.

[Gimme Shelter](https://youtu.be/6yGFuX2KDQs)

Dependency for **Maven** modules (similar for Gradle where applicable)
```xml
<dependency>
  <groupId>org.javamoney.shelter</groupId>
  <artifactId>javamoney-${module}<artifactId>
  <versionId>the current version</version>
</dependency>
```

Subprojects
-----------
* [javamoney-bitcoin](./digital-currency/bitcoin) Bitcoin Support
* [groovy-money](./groovylang-support/groovy-money) A Groovy Extension Module for JavaMoney providing operator overloading and more.
* groovy-demo: Groovy demo and Spock tests of groovy-money (Coming soon)
* [javamoney-spock](./groovylang-support/groovy-money) Spock tests that demonstrate basic JavaMoney functionality without Groovy extensions

Authors and Contributors
------------------------
Everyone is welcome to contribute. Werner Keil (@keilw) composed this project for you.

[![Build Status](https://api.travis-ci.org/JavaMoney/javamoney-shelter.png?branch=master)](https://travis-ci.org/JavaMoney/javamoney-shelter) [![License](http://img.shields.io/badge/license-Apache2-red.svg)](http://opensource.org/licenses/apache-2.0) 
[![Join the chat at https://gitter.im/JavaMoney/javamoney-shelter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/JavaMoney/javamoney-shelter?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

[![Built with Maven](http://maven.apache.org/images/logos/maven-feather.png)](http://maven.org/)

Waffle.io
-----------
[![Stories in Ready](https://badge.waffle.io/JavaMoney/javamoney-shelter.png?label=ready&title=Ready)](https://waffle.io/JavaMoney/javamoney-shelter)

[Presentation Bitcoin, Payment Instrument or Object of Speculation? from Werner Keil](http://www.slideshare.net/keilw/bitcoin-payment-instrument-or-object-of-speculation-smwhh-2014)
