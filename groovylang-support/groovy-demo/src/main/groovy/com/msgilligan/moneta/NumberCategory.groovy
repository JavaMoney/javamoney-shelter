package com.msgilligan.moneta

import groovy.transform.CompileStatic
import org.javamoney.moneta.Money

import javax.money.CurrencyUnit
import javax.money.MonetaryAmount
import javax.money.MonetaryCurrencies

/**
 * Category to allow getting MonetaryAmount values as properties of number, for example:
 *
 * <code>1.usd</code> would return <code>Money.of(1, "USD")</code>
 *
 * This is very useful for creating DSLs.
 *
 * @author Sean Gilligan
 */
@CompileStatic
@Category(Number)
class NumberCategory {
    MonetaryAmount getUsd() {
        return Money.of(this, "USD")
    }

    MonetaryAmount getEur() {
        return Money.of(this, "EUR")
    }

    /*
     * Should work for any supported currency code
     *
     * @throws UnknownCurrencyException when currency code not found
     */
    MonetaryAmount get(String name) {
        CurrencyUnit currency = MonetaryCurrencies.getCurrency(name.toUpperCase())
        return Money.of(this, currency)
    }

}
