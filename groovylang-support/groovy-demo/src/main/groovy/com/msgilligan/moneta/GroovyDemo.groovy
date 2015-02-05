package com.msgilligan.moneta


import javax.money.MonetaryAmount
import org.javamoney.moneta.Money

/**
 * Simple Demo of using Moneta from Groovy
 *
 * Example of arithmetic using MonetaryAmounts with operator overloading added
 * though Groovy Categories and the mixin() method
 *
 * Note: (For now) most of the action is in the Spock tests.
 *
 */

public class GroovyDemo {

    public static void main(String[] args) {

        // Still need to use .mixin here as long as Extensions in on same subproject/build as the demo class
        MonetaryAmount.mixin(MonetaryAmountCategory)

        def value1 = Money.of(10, "USD")
        def value2 = Money.of(0.99, "USD")

        def sum = value1 + value2               // Add MonetaryAmounts with '+' operator

        println "${value1} + ${value2} = ${sum}"

    }
}
