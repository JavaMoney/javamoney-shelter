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
