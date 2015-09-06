/**
 *
 */


import org.javamoney.groovy.MonetaryAmountCategory
import org.javamoney.groovy.NumberValueCategory
import org.javamoney.moneta.Money
import org.javamoney.moneta.spi.DefaultNumberValue
import spock.lang.Ignore
import spock.lang.Specification
import spock.util.mop.ConfineMetaClassChanges
import spock.util.mop.Use

import javax.money.CurrencyUnit
import javax.money.MonetaryAmount
import javax.money.Monetary
import javax.money.NumberValue

/**
 * Demonstrate and test JavaMoney methods using the Groovy language.
 *
 * Particular attention is paid to the issue of using Groovy operator overloading
 * to be able to write things like:
 *
 * { @code def sum = amount1 + amount2 }
 *
 *
 * Groovy Categories are used to support some of the operator methods used by Groovy for arithmetic.
 *
 */
class JavaMoneySpec extends Specification {

    def "get currency unit" () {
        when:
        CurrencyUnit usDollar = Monetary.getCurrency("USD")

        then:
        usDollar.currencyCode == "USD"
    }

    def "create currency amount" () {
        when:
        MonetaryAmount tenUsDollar = Money.of(10, "USD")

        then:
        tenUsDollar.currency.currencyCode == "USD"
        tenUsDollar.number == 10
    }

    def "add MonetaryAmount objects with the add() method" () {
        when:
        MonetaryAmount amount1 = Money.of(10, "USD")
        MonetaryAmount amount2 = Money.of(1, "USD")
        MonetaryAmount sum = amount1.add(amount2)

        then:
        sum.number == 11
        sum == Money.of(11, "USD")
    }
}
