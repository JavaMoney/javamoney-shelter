import org.javamoney.moneta.Money

import javax.money.MonetaryAmount
import javax.money.NumberValue

/**
 *
 */
class JavaMoneyDemo {
        public static void main(String[] args) {


            NumberValue value1 = Money.of(10, "USD").number
            NumberValue value2 = Money.of(0.99, "USD").number

            def sum = value1 + value2               // Add NumberValue with '+' operator

            println "${value1} + ${value2} = ${sum} <-Oops!"

        }

}
