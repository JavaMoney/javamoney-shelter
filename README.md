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

Retired
-------
Currently the following modules are retired (or archived, this means they are not actively maintained.
They even may not compile):
* [**Formatting**](retired/format) provides an extendble formatting library that allows to define complex formatters, that can be configured in arbitrary ways using `LocalizationStyle` instances.
Also available is a flexible Builder for creating arbitrary complex formatters and parsers based on an ordered set of arbitrary tokens.
* [**javamoney-all**](retired/javamoney-all) provides a pom that imports everything you need (API, RI and the released JavaMoney libraries).
* [**Region API**](retired/regions) provides a forest (a set of trees) of regions. This allows to model regional hierarchies in a more flexible and intuitive way, than adding all functionalities into `java.util.Locale`.
By default the Unicode CLDR region tree, well as ISO countries defined by the 2- or 3-letter country code are available.
Of course, the API is fully extendible, so customer related regions such as legal units, customer segments etc can be mapped easily to this API, also.
* [**Validity API**](retired/validity) This API provides a generic API for accessing historic validity information for arbitrary items, and for relationships between items.
By default the API provides access to the historic relationship of currencies to countries using the Unicode CLDR data.
* [**Data**](retired/cldr-data) JavaMoney Data
* [**Currencies**](retired/currencies) ISO Online Currency Provider

Authors and Contributors
------------------------
Everyone is welcome to contribute. Werner Keil (@keilw) composed this project for you.

[![Build Status](https://api.travis-ci.org/JavaMoney/javamoney-shelter.png?branch=master)](https://travis-ci.org/JavaMoney/javamoney-shelter) [![License](http://img.shields.io/badge/license-Apache2-red.svg)](http://opensource.org/licenses/apache-2.0) 
[![Join the chat at https://gitter.im/JavaMoney/javamoney-shelter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/JavaMoney/javamoney-shelter?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

[![Built with Maven](http://maven.apache.org/images/logos/maven-feather.png)](http://maven.org/)

Waffle.io
-----------
[![Stories in Ready](https://badge.waffle.io/JavaMoney/javamoney-shelter.png?label=ready&title=Ready)](https://waffle.io/JavaMoney/javamoney-shelter)

[Presentation Bitcoin, Payment Instrument or Object of Speculation? by Werner Keil](http://www.slideshare.net/keilw/bitcoin-payment-instrument-or-object-of-speculation-smwhh-2014)
