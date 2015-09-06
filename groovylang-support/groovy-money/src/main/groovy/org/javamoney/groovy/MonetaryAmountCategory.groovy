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

import javax.money.MonetaryAmount

/**
 * Category to add methods to MonetaryAmount to support operator overloading.
 */
@CompileStatic
@Category(MonetaryAmount)
class MonetaryAmountCategory {

    /**
     * MonetaryAmount has add() not plus(), let's remedy that
     *
     * @param right right operator
     * @return sum as a MonetaryAmount
     */
    MonetaryAmount plus(MonetaryAmount right) {
        return this.add(right)
    }
	
	/**
	 * MonetaryAmount has subtract() not minus(), let's remedy that
	 *
	 * @param right right operator
	 * @return sum as a MonetaryAmount
	 */
	MonetaryAmount minus(MonetaryAmount right) {
		return this.subtract(right)
	}
}
