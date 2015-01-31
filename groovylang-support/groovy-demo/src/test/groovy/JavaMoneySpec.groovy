/**
 *
 */


import org.javamoney.moneta.Money
import org.javamoney.moneta.spi.DefaultNumberValue
import spock.lang.Specification
import spock.util.mop.ConfineMetaClassChanges

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
 * The Groovy MetaClass is used to add some of the operator methods used by Groovy for arithmetic.
 * Note: The Spock {@code @ConfineMetaClassChanges} annotation is used to limit MetaClass changes
 * to a single feature test.
 *
 */
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

    def "add currency amounts using Groovy operator overloading fails" () {
        when:
        MonetaryAmount amount1 = Money.of(10, "USD")
        MonetaryAmount amount2 = Money.of(1, "USD")
        MonetaryAmount sum = amount1 + amount2

        then:
        groovy.lang.MissingMethodException e = thrown()
        e.message != null
    }

    def "add number values using plus method" () {
        when:
        NumberValue value1 = Money.of(10, "USD").number
        NumberValue value2 = Money.of(1, "USD").number
        def sum = value1.plus(value2)

        then:
        sum == 11
        sum.class == Integer.class
    }

    def "add number values using Groovy operator overloading succeeds" () {
        when:
        NumberValue value1 = Money.of(10, "USD").number
        NumberValue value2 = Money.of(1, "USD").number
        def sum = value1 + value2

        then:
        sum == 11
        sum.class == Integer.class
    }

    def "add number values to get a new NumberValue" () {
        when:
        NumberValue value1 = Money.of(10, "USD").number
        NumberValue value2 = Money.of(1, "USD").number
        NumberValue sum = DefaultNumberValue.of(value1 + value2)

        then:
        sum == 11
    }

    @ConfineMetaClassChanges(DefaultNumberValue)
    def "Update the metaclass so NumberValue + NumberValue = NumberValue" () {
        given: "we dynamically add a plus() method using Groovy"
        DefaultNumberValue.metaClass.plus { DefaultNumberValue right ->
            new DefaultNumberValue(longValueExact() + right.longValueExact()) }

        when:
        NumberValue value1 = Money.of(10, "USD").number
        NumberValue value2 = Money.of(1, "USD").number
        def sum = value1 + value2

        then:
        sum == 11
        sum.class == DefaultNumberValue.class
    }

    @ConfineMetaClassChanges(MonetaryAmount)
    def "Update the metaclass so MonetaryAmount + MonetaryAmount = MonetaryAmount" () {
        given: "we dynamically add a plus() method using Groovy"
        MonetaryAmount.metaClass.plus { MonetaryAmount right -> add(right) }

        when:
        def amount1 = Money.of(10, "USD")
        def amount2 = Money.of(1, "USD")
        def sum = amount1 + amount2

        then:
        sum.number == 11
        sum.currency.currencyCode == "USD"
        sum.class == Money.class
    }

    @ConfineMetaClassChanges(MonetaryAmount)
    def "USD + EUR throws exception" () {
        given: "we dynamically add a plus() method using Groovy"
        MonetaryAmount.metaClass.plus { MonetaryAmount right -> add(right) }

        when:
        def amount1 = Money.of(10, "USD")
        def amount2 = Money.of(1, "EUR")
        def sum = amount1 + amount2

        then:
        javax.money.MonetaryException e = thrown()
        e.message == "Currency mismatch: USD/EUR"
    }

}
