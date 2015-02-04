import com.msgilligan.moneta.MonetaryAmountCategory
import com.msgilligan.moneta.NumberValueCategory
import org.javamoney.moneta.Money
import spock.lang.Specification
import spock.util.mop.Use


/**
 * Test of operators added by MonetaryAmountCategory
 */
@Use(MonetaryAmountCategory)
class MonetaryAmountCategorySpec extends Specification {

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