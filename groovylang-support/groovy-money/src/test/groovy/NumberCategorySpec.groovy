import com.msgilligan.moneta.NumberCategory
import org.javamoney.moneta.Money
import org.javamoney.moneta.spi.DefaultNumberValue
import spock.lang.Specification
import spock.lang.Unroll
import spock.util.mop.ConfineMetaClassChanges
import spock.util.mop.Use

import javax.money.CurrencyUnit
import javax.money.MonetaryAmount
import javax.money.Monetary


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
        aBuck.currency == Monetary.getCurrency("USD")
    }

    def "can make EUR"() {
        when:
        def amount = 1.eur

        then:
        amount instanceof MonetaryAmount
        amount.number == 1
        amount.currency == Monetary.getCurrency("EUR")
    }

    def "can make any supported currency via propertyMissing"() {
        when:
        def amount = 1.jpy

        then:
        amount instanceof MonetaryAmount
        amount.number == 1
        amount.currency == Monetary.getCurrency("JPY")
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
        amount.currency == Monetary.getCurrency("USD")
    }

    @Unroll
    def "#sum = #left  + #right (#left.class + nv(#right.class))" () {
        expect:
        sum == left + DefaultNumberValue.of(right)

        where:
        left  | right  | sum
        10  |     1  |   11
        10G |     1G |   11
        10.1G |   0.9G |   11
        Integer.MAX_VALUE | Integer.MAX_VALUE | ((Long)Integer.MAX_VALUE) + Integer.MAX_VALUE
        Long.MAX_VALUE | Long.MAX_VALUE | new BigInteger(Long.MAX_VALUE) + new BigInteger(Long.MAX_VALUE)
    }

    @Unroll
    def "bad type #left.class throws exception"() {
        when:
        left + DefaultNumberValue.of(right)

        then:
        RuntimeException rte = thrown()

        where:
        left | right
        10D  | 0
        10F  | 0
    }

}