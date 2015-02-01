package com.msgilligan.moneta

import javax.money.MonetaryAmount

/**
 * Category to add methods to MonetaryAmount to support operator overloading.
 */
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
}
