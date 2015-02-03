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
        def aBuck = 1.eur

        then:
        aBuck instanceof MonetaryAmount
        aBuck.number == 1
        aBuck.currency == MonetaryCurrencies.getCurrency("EUR")
    }

    def "can make any supported currency via propertyMissing"() {
        when:
        def aBuck = 1.jpy

        then:
        aBuck instanceof MonetaryAmount
        aBuck.number == 1
        aBuck.currency == MonetaryCurrencies.getCurrency("JPY")
    }

    def "Undefined currency should throw UnknownCurrencyException"() {
        when:
        def aBuck = 1.invalid

        then:
        javax.money.UnknownCurrencyException e = thrown()
        aBuck == null
    }

    def "Works with decimals"() {
        when:
        def aBuck = 0.99.usd

        then:
        aBuck instanceof MonetaryAmount
        aBuck.number == 0.99
        aBuck.currency == MonetaryCurrencies.getCurrency("USD")
    }

}