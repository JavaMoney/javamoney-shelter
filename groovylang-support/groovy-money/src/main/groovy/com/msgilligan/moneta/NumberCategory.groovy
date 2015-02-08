package com.msgilligan.moneta

import groovy.transform.CompileStatic
import org.javamoney.moneta.Money
import org.javamoney.moneta.spi.DefaultNumberValue

import javax.money.CurrencyUnit
import javax.money.MonetaryAmount
import javax.money.MonetaryCurrencies
import javax.money.NumberValue

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

    Number plus(NumberValue right) {
        BigDecimal lbd
        if (this instanceof BigDecimal) {
            lbd = this
        } else if (this instanceof BigInteger) {
            lbd = new BigDecimal((BigInteger) this)
        } else if (this instanceof Long) {
            lbd = new BigDecimal(((Long) this))
        } else if (this instanceof Integer) {
            lbd = new BigDecimal(((Integer) this))
        } else  {
            throw new RuntimeException("Don't use plus(NumberValue) with type" + this.class)
        }
        BigDecimal rbd = (BigDecimal)right.numberValueExact(BigDecimal.class)
        BigDecimal sum = lbd.add(rbd)
        return sum
    }


}
