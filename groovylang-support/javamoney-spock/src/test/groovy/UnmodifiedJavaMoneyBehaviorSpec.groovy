/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
import org.javamoney.moneta.Money
import org.javamoney.moneta.spi.DefaultNumberValue
import spock.lang.Ignore
import spock.lang.Specification

import javax.money.MonetaryAmount
import javax.money.NumberValue

/**
 * Demonstrate expected but undesirable behavior that can be remedied with Groovy
 * MetaClass changes.
 *
 * Particular attention is paid to the issue of using Groovy operator overloading
 * to be able to write things like:
 *
 * { @code def sum = amount1 + amount2 }
 *
 * Perhaps undesirable behavior should be indicated by a failing test,
 * but for now the tests are written to "specify" the undesirable behavior with
 * asserts in the "then" clauses.
 */
class UnmodifiedJavaMoneyBehaviorSpec extends Specification {

    def "Add currency amounts using Groovy operator overloading fails" () {
        when: "We add two MonetaryAmounts"
        MonetaryAmount amount1 = Money.of(10, "USD")
        MonetaryAmount amount2 = Money.of(1, "USD")
        def sum = amount1 + amount2

        then: "We get a MissingMethodException because JavaMoney uses add() not plus()"
        groovy.lang.MissingMethodException e = thrown()
        e.message != null
    }

    def "Add number values using Groovy MetaClass plus() method succeeds, but returns unexpected type" () {
        when:
        NumberValue value1 = Money.of(10, "USD").number
        NumberValue value2 = Money.of(1, "USD").number
        def sum = value1.plus(value2)

        then: "We get an Integer, although we expected (wanted) a NumberValue"
        sum == 11
        sum.class == Integer.class
    }

    def "Add number values using Groovy operator overloading succeeds, but returns unexpected type" () {
        when: "We add two NumberValues using the '+' operator and the Groovy Number.plus() method"
        NumberValue value1 = Money.of(10, "USD").number
        NumberValue value2 = Money.of(1, "USD").number
        def sum = value1 + value2

        then: "We get an Integer, although we expected (wanted) a NumberValue"
        sum == 11
        sum.class == Integer.class
    }

    def "Add number values using Groovy + operator incorrectly rounds to integer result" () {
        when: "We add two NumberValues using the '+' operator and the Groovy Number.plus() method"
        NumberValue value1 = Money.of(10, "USD").number
        NumberValue value2 = Money.of(0.99, "USD").number
        def sum = value1 + value2

        then: "We get an incorrect answer - rounded to Integer"
        sum == 10.00    // TODO: Fix this with either upstream changes and/or Groovy category
    }

    def "Add number values and convert from Integer to new NumberValue" () {
        when: "We add two NumberValues using the default Groovy Number.plus() method"
        NumberValue value1 = Money.of(10, "USD").number
        NumberValue value2 = Money.of(1, "USD").number
        def sumInteger = value1 + value2

        and: "We convert the type"
        def sum = DefaultNumberValue.of(sumInteger)

        then: "We get an implementation of NumberValue"
        sum == 11
        sum.class == DefaultNumberValue.class
    }
}
