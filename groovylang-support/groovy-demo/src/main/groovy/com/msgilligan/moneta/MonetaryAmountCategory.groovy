package com.msgilligan.moneta

import javax.money.MonetaryAmount

/**
 *
 */
@Category(MonetaryAmount)
class MonetaryAmountCategory {
    MonetaryAmount plus(MonetaryAmount right) {
        return this.add(right)
    }
}
