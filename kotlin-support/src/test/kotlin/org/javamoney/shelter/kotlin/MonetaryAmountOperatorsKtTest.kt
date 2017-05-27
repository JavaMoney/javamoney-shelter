package org.javamoney.shelter.kotlin

import org.javamoney.moneta.Money.of
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal
import javax.money.MonetaryAmount

val EUR = "EUR"

class MonetaryAmountOperatorsKtTest {
    val valueA = BigDecimal(123)
    val valueB = BigDecimal(42)
    val valueC = BigDecimal(3)
    val moneyA: MonetaryAmount = of(valueA, EUR)
    val moneyB: MonetaryAmount = of(valueB, EUR)

    @Test fun testUnaryPlus() {
        val expected = moneyA
        assertEquals(expected, +moneyA)
    }
    @Test fun testUnaryMinus() {
        val expected = of(-valueA, EUR)
        assertEquals(expected, -moneyA)
    }

    @Test fun testPlusAddsTwoValues() {
        val expected = of(valueA + valueB, EUR)
        assertEquals(expected, moneyA + moneyB)
    }
    @Test fun testMinusSubtractsTwoValues() {
        val expected = of(valueA - valueB, EUR)
        assertEquals(expected, moneyA - moneyB)
    }

    @Test fun testTimesMultipliesTwoValues() {
        val expected = of(valueA.multiply(valueC), EUR)
        assertEquals(expected, moneyA * valueC)
    }
    @Test fun testDivDividesTwoValues() {
        val expected = of(valueA.divide(valueC), EUR)
        assertEquals(expected, moneyA / valueC)
    }
}
