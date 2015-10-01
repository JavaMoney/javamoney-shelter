/*
 * Copyright (c) 2014-2015, Werner Keil and others by the @author tag.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.javamoney.groovy.MonetaryAmountCategory
import org.javamoney.groovy.NumberCategory
import org.javamoney.moneta.Money
import spock.lang.Specification
import spock.util.mop.Use

import javax.money.MonetaryAmount

/**
 * Tests JavaMoney API and Groovy extensions with Spock.
 * @author Sean Gilligan
 * @author Werner Keil
 */
@Use(NumberCategory)
class GroovyMoneyDemoSpec extends Specification {

    def "Create two MonetaryAmounts and add them with + operator" () {
        when:
        def sum = 10.eur + 0.99.eur

        then:
        sum.number == 10.99
        sum.currency.currencyCode == "EUR"
        sum instanceof MonetaryAmount
        sum.class == Money.class
    }
	
	def "Create two MonetaryAmounts and subtract them with - operator" () {
		when:
		def diff = 10.eur - 1.99.eur

		then:
		diff.number == 8.01
		diff.currency.currencyCode == "EUR"
		diff instanceof MonetaryAmount
		diff.class == Money.class
	}

	
    def "USD + EUR throws exception" () {
        when:
        def sum = 1.usd + 2.eur

        then:
        javax.money.MonetaryException e = thrown()
        e.message == "Currency mismatch: USD/EUR"
    }

}