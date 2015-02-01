package com.msgilligan.moneta

import org.javamoney.moneta.spi.DefaultNumberValue

import javax.money.NumberValue

/**
 * Category to add improve NumberValue to better support operator overloading.
 */
@Category(NumberValue)
class NumberValueCategory {
    /**
     * Groovy adds plus() to Number but unfortunately reverts to the lowest-common-denominator
     * Integer type for the result. This method fixes that behavior.
     *
     * @param right right operand
     * @return  a newly-created DefaultNumberValue
     */
    NumberValue plus(NumberValue right) {
        return new DefaultNumberValue(this.longValueExact() + right.longValueExact())
    }
}
