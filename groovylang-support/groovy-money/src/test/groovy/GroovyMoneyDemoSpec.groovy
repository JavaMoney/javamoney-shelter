import org.javamoney.groovy.MonetaryAmountCategory
import org.javamoney.groovy.NumberCategory
import org.javamoney.moneta.Money
import spock.lang.Specification
import spock.util.mop.Use

import javax.money.MonetaryAmount


/**
 * Demonstrate JavaMoney API with Groovy extensions.
 *
 */
@Use(NumberCategory)
class GroovyMoneyDemoSpec extends Specification {

    def "Create two MonetaryAmounts with Number properties and add them with + operator" () {
        when:
        def sum = 10.eur + 0.99.eur

        then:
        sum.number == 10.99
        sum.currency.currencyCode == "EUR"
        sum instanceof MonetaryAmount
        sum.class == Money.class
    }

    def "USD + EUR throws exception" () {
        when:
        def sum = 1.usd + 2.eur

        then:
        javax.money.MonetaryException e = thrown()
        e.message == "Currency mismatch: USD/EUR"
    }

}