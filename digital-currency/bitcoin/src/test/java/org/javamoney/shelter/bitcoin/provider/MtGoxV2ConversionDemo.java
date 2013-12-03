/*
 * Copyright (c) 2013, Werner Keil, JUGChennai and others.
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
package org.javamoney.shelter.bitcoin.provider;

import javax.money.CurrencyUnit;
import javax.money.MonetaryException;

import org.javamoney.convert.ExchangeRate;
import org.javamoney.moneta.MoneyCurrency;


/**
 * @author Werner Keil
 *
 */
public class MtGoxV2ConversionDemo {
    public static void main(String... arg) throws MonetaryException {
    	CurrencyUnit btc = BTCCurrency.of();
    	System.out.println("Currency: " + btc);
    	//MtGoxV2ConversionProvider provider = new MtGoxV2ConversionProvider("USD");
    	MtGoxV2ConversionProvider provider = new MtGoxV2ConversionProvider();
        provider.loadRate("USD", true);
        ExchangeRate rate = provider.getExchangeRate(MoneyCurrency.of("USD"), btc);
        System.out.println("Rate: " + rate);
        
        rate = provider.getExchangeRate(MoneyCurrency.of("EUR"), btc);
        System.out.println("Rate2: " + rate);
        
        rate = provider.getExchangeRate(MoneyCurrency.of("CHF"), btc);
        System.out.println("Rate3: " + rate);
    }
}
