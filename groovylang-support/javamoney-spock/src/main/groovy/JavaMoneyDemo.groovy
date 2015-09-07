/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
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
