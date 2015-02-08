package com.msgilligan.moneta

import groovy.transform.CompileStatic
import org.javamoney.moneta.spi.DefaultNumberValue

import javax.money.NumberValue

/**
 * Category to add improve NumberValue to better support operator overloading.
 */
@CompileStatic
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
        return new DefaultNumberValue(this.numberValueExact(BigDecimal.class) + right.numberValueExact(BigDecimal.class))
    }

    /**
     * Groovy adds plus() to Number but unfortunately reverts to the lowest-common-denominator
     * Integer type for the result. This method fixes that behavior.
     *
     * @param right right operand
     * @return  a newly-created DefaultNumberValue
     */
    NumberValue plus(Number right) {
        BigDecimal rbd = DefaultNumberValue.of(right).numberValueExact(BigDecimal.class)
        return new DefaultNumberValue(this.numberValueExact(BigDecimal.class) + rbd)
    }

    /**
     * Groovy adds minus() to Number but unfortunately reverts to the lowest-common-denominator
     * Integer type for the result. This method fixes that behavior.
     *
     * @param right right operand
     * @return  a newly-created DefaultNumberValue
     */
    NumberValue minus(NumberValue right) {
        return new DefaultNumberValue(this.numberValueExact(BigDecimal.class) - right.numberValueExact(BigDecimal.class))
    }

}
