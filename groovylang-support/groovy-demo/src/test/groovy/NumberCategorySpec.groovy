import com.msgilligan.moneta.NumberCategory
import org.javamoney.moneta.Money
import spock.lang.Specification
import spock.util.mop.ConfineMetaClassChanges
import spock.util.mop.Use

import javax.money.CurrencyUnit
import javax.money.MonetaryAmount
import javax.money.MonetaryCurrencies


/**
 * Tests of currency properties added in NumberCategory
 */
@Use(NumberCategory)
class NumberCategorySpec extends Specification {
    def "can make USD"() {
        when:
        def aBuck = 1.usd

        then:
        aBuck instanceof MonetaryAmount
        aBuck.number == 1
        aBuck.currency == MonetaryCurrencies.getCurrency("USD")
    }

    def "can make EUR"() {
        when:
        def amount = 1.eur

        then:
        amount instanceof MonetaryAmount
        amount.number == 1
        amount.currency == MonetaryCurrencies.getCurrency("EUR")
    }

    def "can make any supported currency via propertyMissing"() {
        when:
        def amount = 1.jpy

        then:
        amount instanceof MonetaryAmount
        amount.number == 1
        amount.currency == MonetaryCurrencies.getCurrency("JPY")
    }

    def "Undefined currency should throw UnknownCurrencyException"() {
        when:
        def amount = 1.invalid

        then:
        javax.money.UnknownCurrencyException e = thrown()
        amount == null
    }

    def "Works with decimals"() {
        when:
        def amount = 0.99.usd

        then:
        amount instanceof MonetaryAmount
        amount.number == 0.99
        amount.currency == MonetaryCurrencies.getCurrency("USD")
    }

}