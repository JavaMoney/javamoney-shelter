import com.msgilligan.moneta.MonetaryAmountCategory
import com.msgilligan.moneta.NumberValueCategory
import org.javamoney.moneta.Money
import org.javamoney.moneta.spi.DefaultNumberValue
import spock.lang.Specification
import spock.util.mop.Use

import javax.money.NumberValue


/**
 * Test of operators added by NumberValueCategory
 */
@Use(NumberValueCategory)
class NumberValueCategorySpec extends Specification {
    def "NumberValue + NumberValue = NumberValue" () {
        when:
        NumberValue value1 = Money.of(10, "USD").number
        NumberValue value2 = Money.of(1, "USD").number
        def sum = value1 + value2

        then:
        sum == 11
        sum.class == DefaultNumberValue.class
    }
}