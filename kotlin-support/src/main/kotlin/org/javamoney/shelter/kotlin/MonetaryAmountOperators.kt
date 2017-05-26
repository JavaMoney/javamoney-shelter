package org.javamoney.shelter.kotlin

import javax.money.MonetaryAmount

// Unaries
operator fun MonetaryAmount.unaryPlus() = this.plus()
operator fun MonetaryAmount.unaryMinus() = this.negate()

// MonetaryAmount x MonetaryAmount
operator fun MonetaryAmount.plus(b: MonetaryAmount): MonetaryAmount = this.add(b)
operator fun MonetaryAmount.minus(b: MonetaryAmount): MonetaryAmount = this.subtract(b)

// MonetaryAmount x Number
operator fun MonetaryAmount.times(b: Number): MonetaryAmount = this.multiply(b)
operator fun MonetaryAmount.div(b: Number): MonetaryAmount = this.divide(b)
