import org.javamoney.moneta.Money
import org.javamoney.moneta.spi.DefaultNumberValue
import spock.lang.Ignore
import spock.lang.Specification

import javax.money.MonetaryAmount
import javax.money.NumberValue


/**
 * Demonstrate expected but undesirable behavior that can be remedied with Groovy
 * MetaClass changes.
 *
 * Particular attention is paid to the issue of using Groovy operator overloading
 * to be able to write things like:
 *
 * { @code def sum = amount1 + amount2 }
 *
 * Perhaps undesirable (buggy?) behavior should be indicated by a failing test, 
 * but for now the tests are written to "specify" the undersirable behavior with
 * asserts in the "then" clauses.
 */
class UnmodifiedJavaMoneyBehaviorSpec extends Specification {


    def "Add currency amounts using Groovy operator overloading fails" () {
        when: "We add two MonetaryAmounts"
        MonetaryAmount amount1 = Money.of(10, "USD")
        MonetaryAmount amount2 = Money.of(1, "USD")
        def sum = amount1 + amount2

        then: "We get a MissingMethodException because JavaMoney uses add() not plus()"
        groovy.lang.MissingMethodException e = thrown()
        e.message != null
    }

    def "Add number values using Groovy MetaClass plus() method succeeds, but returns unexpected type" () {
        when:
        NumberValue value1 = Money.of(10, "USD").number
        NumberValue value2 = Money.of(1, "USD").number
        def sum = value1.plus(value2)

        then: "We get an Integer, although we expected (wanted) a NumberValue"
        sum == 11
        sum.class == Integer.class
    }


    def "Add number values using Groovy operator overloading succeeds, but returns unexpected type" () {
        when: "We add two NumberValues using the '+' operator and the Groovy Number.plus() method"
        NumberValue value1 = Money.of(10, "USD").number
        NumberValue value2 = Money.of(1, "USD").number
        def sum = value1 + value2

        then: "We get an Integer, although we expected (wanted) a NumberValue"
        sum == 11
        sum.class == Integer.class
    }

    // TODO: Add test that demonstrates truncation problem in above case (converting to Integer)

    def "Add number values and convert from Integer to new NumberValue" () {
        when: "We add two NumberValues using the default Groovy Number.plus() method"
        NumberValue value1 = Money.of(10, "USD").number
        NumberValue value2 = Money.of(1, "USD").number
        def sumInteger = value1 + value2

        and: "We convert the type"
        def sum = DefaultNumberValue.of(sumInteger)

        then: "We get an implementation of NumberValue"
        sum == 11
        sum.class == DefaultNumberValue.class
    }
}
