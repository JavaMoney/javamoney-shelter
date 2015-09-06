/*
 * Copyright (c) 2014-2015, Werner Keil, Anatole Tresch and others by the @author tag.
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
package org.javamoney.groovy

import groovy.transform.CompileStatic
import org.javamoney.moneta.Money
import org.javamoney.moneta.spi.DefaultNumberValue

import javax.money.CurrencyUnit
import javax.money.MonetaryAmount
import javax.money.Monetary
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
        CurrencyUnit currency = Monetary.getCurrency(name.toUpperCase())
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
