package com.msgilligan.moneta

import org.javamoney.moneta.Money

import javax.money.MonetaryAmount

/**
 * Simple Demo of using Moneta from Groovy
 *
 * Note: (For now) the real action is in the Spock tests.
 *
 */

// Configure this Groovy Meta-class for MonetaryAmount
// In the future this should be done automatically/invisibly

MonetaryAmount.metaClass.plus { MonetaryAmount right -> add(right) }

// Example of arithmetic using MonetaryAmounts with operator overloading added
// though Groovy and Groovy Meta-class

def value1 = Money.of(10, "USD")
def value2 = Money.of(0.99, "USD")
def sum = value1 + value2
println "${value1} + ${value2} = ${sum}"
