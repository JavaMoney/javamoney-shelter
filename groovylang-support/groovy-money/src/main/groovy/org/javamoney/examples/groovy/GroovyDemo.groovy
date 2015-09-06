/*
 * Copyright (c) 2014-2015, Werner Keil and others by the @author tag.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.javamoney.examples.groovy

import javax.money.MonetaryAmount
import org.javamoney.moneta.Money
import org.javamoney.groovy.MonetaryAmountCategory

/**
 * Simple Demo of using Moneta from Groovy
 *
 * Example of arithmetic using MonetaryAmounts with operator overloading added
 * though Groovy Categories and the mixin() method
 *
 * Note: (For now) most of the action is in the Spock tests.
 *
 * @author Sean Gilligan
 * @author Werner Keil
 */
public class GroovyDemo {

    public static void main(String[] args) {

        // Still need to use .mixin here as long as Extensions in on same subproject/build as the demo class
        MonetaryAmount.mixin(MonetaryAmountCategory)

        def value1 = Money.of(10, "USD")
        def value2 = Money.of(0.99, "USD")

        def sum = value1 + value2               // Add MonetaryAmounts with '+' operator
        println "${value1} + ${value2} = ${sum}"
		
		def dif = value1 - value2
		println "${value1} - ${value2} = ${dif}"
    }
}
