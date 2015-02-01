/**
 *
 */


import com.msgilligan.moneta.MonetaryAmountCategory
import com.msgilligan.moneta.NumberValueCategory
import org.javamoney.moneta.Money
import org.javamoney.moneta.spi.DefaultNumberValue
import spock.lang.Ignore
import spock.lang.Specification
import spock.util.mop.ConfineMetaClassChanges
import spock.util.mop.Use

import javax.money.CurrencyUnit
import javax.money.MonetaryAmount
import javax.money.MonetaryCurrencies
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
@Use([MonetaryAmountCategory, NumberValueCategory])
class JavaMoneySpec extends Specification {

    def "create currency unit" () {
        when:
        CurrencyUnit usDollar = MonetaryCurrencies.getCurrency("USD")

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

    def "add currency amounts" () {
        when:
        MonetaryAmount amount1 = Money.of(10, "USD")
        MonetaryAmount amount2 = Money.of(1, "USD")
        MonetaryAmount sum = amount1.add(amount2)

        then:
        sum.number == 11
        sum == Money.of(11, "USD")
    }


    def "NumberValue + NumberValue = NumberValue" () {
        when:
        NumberValue value1 = Money.of(10, "USD").number
        NumberValue value2 = Money.of(1, "USD").number
        def sum = value1 + value2

        then:
        sum == 11
        sum.class == DefaultNumberValue.class
    }

    def "MonetaryAmount + MonetaryAmount = MonetaryAmount" () {
        when:
        def amount1 = Money.of(10, "USD")
        def amount2 = Money.of(1, "USD")
        def sum = amount1 + amount2

        then:
        sum.number == 11
        sum.currency.currencyCode == "USD"
        sum.class == Money.class
    }

    def "USD + EUR throws exception" () {
        when:
        def amount1 = Money.of(10, "USD")
        def amount2 = Money.of(1, "EUR")
        def sum = amount1 + amount2

        then:
        javax.money.MonetaryException e = thrown()
        e.message == "Currency mismatch: USD/EUR"
    }
}
