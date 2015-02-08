import com.msgilligan.moneta.MonetaryAmountCategory
import com.msgilligan.moneta.NumberValueCategory
import org.javamoney.moneta.Money
import org.javamoney.moneta.spi.DefaultNumberValue
import spock.lang.Specification
import spock.lang.Unroll
import spock.util.mop.Use

import javax.money.NumberValue


/**
 * Test of operators added by NumberValueCategory
 */
class NumberValueCategorySpec extends Specification {

    @Unroll
    def "#sum = #left  + #right (nv(#left.class) + nv(#right.class))" () {
        expect:
        sum == DefaultNumberValue.of(left) + DefaultNumberValue.of(right)

        where:
        left  | right  | sum
          10  |     1  |   11
          10G |     1G |   11
        10.1G |   0.9G |   11
        Integer.MAX_VALUE | Integer.MAX_VALUE | ((Long)Integer.MAX_VALUE) + Integer.MAX_VALUE
        Long.MAX_VALUE | Long.MAX_VALUE | new BigInteger(Long.MAX_VALUE) + new BigInteger(Long.MAX_VALUE)
    }

    @Unroll
    def "#sum = #left  + #right (nv(#left.class) + #right.class)" () {
        expect:
        sum == DefaultNumberValue.of(left) + right

        where:
        left  | right  | sum
        10  |     1  |   11
        10G |     1G |   11
        10.1G |   0.9G |   11
        Integer.MAX_VALUE | Integer.MAX_VALUE | ((Long)Integer.MAX_VALUE) + Integer.MAX_VALUE
        Long.MAX_VALUE | Long.MAX_VALUE | new BigInteger(Long.MAX_VALUE) + new BigInteger(Long.MAX_VALUE)
    }


    @Unroll
    def "#diff = #left  - #right (#left.class - #right.class)" () {
        expect:
        diff == DefaultNumberValue.of(left) - DefaultNumberValue.of(right)

        where:
        left  | right  | diff
          10  |     1  |    9
          10G |     1G |    9
         9.9G |   0.9G |    9
    }

}