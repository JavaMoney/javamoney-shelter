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

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.money.CurrencyUnit;
import javax.money.MonetaryException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
// TODO consider switching to JSR 353

import org.javamoney.convert.ConversionProvider;
import org.javamoney.convert.CurrencyConverter;
import org.javamoney.convert.ExchangeRate;
import org.javamoney.convert.ExchangeRateType;
import org.javamoney.convert.provider.DefaultCurrencyConverter;
import org.javamoney.moneta.UnknownCurrencyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * A Currency conversion provider for MtGox service.
 *
 * @author Rajmahendra Hegde <rajmahendra@gmail.com>
 * @author Werner Keil
 */
public class MtGoxV2ConversionProvider implements ConversionProvider {
    public static final ExchangeRateType RATE_TYPE = ExchangeRateType.of("MtGox");
    
    private static final String PROVIDER_URL = "http://data.mtgox.com/api/2/BTC";

    private static final Logger LOGGER = LoggerFactory.getLogger(MtGoxV2ConversionProvider.class);
    
    /**
     * Contains MtGox supported currencyCode types.
     */
    public static enum MtGoxCurrency {

        USD("USD"), AUD("AUD"), CAD("CAD"), CHF("CHF"), CNY("CNY"), DKK("DKK"), EUR("EUR"), GBP("GBP"), HKD("HKD"), JPY("JPY"), NZD("NZD"), PLN("PLN"), RUB("RUB"), SEK("SEK"), SGD("SGD"), THB("THB"), NOK("NOK"), CZK("CZK");
        private final String currencyCode;

        public String code() {
            return currencyCode;
        }

        private MtGoxCurrency(String code) {
            this.currencyCode = code;

        }

        public static boolean isSupported(String newCurrency) {
            try {
                valueOf(newCurrency);
            } catch (IllegalArgumentException e) {
                return false;
            }
            return true;

        }
    }
    
    private final String forCurrency;
    
    private Map<String, Number> currentRates = new ConcurrentHashMap<String, Number>();
    
    private CurrencyConverter currencyConverter = new DefaultCurrencyConverter(this);

    public MtGoxV2ConversionProvider() {
    	this(null);
    }
    
    public MtGoxV2ConversionProvider(String currencyCode) {
    	this.forCurrency = currencyCode;
    	
    	if(forCurrency == null) {
    		for (MtGoxCurrency currency : MtGoxCurrency.values()) {
    			loadRate(currency.code());
    		}
    	} else {
    		loadRate(forCurrency);
    	}
    }

    @Override
    public ExchangeRateType getExchangeRateType() {
        return RATE_TYPE;
    }

    @Override
    public boolean isAvailable(CurrencyUnit base, CurrencyUnit term) {
        return getExchangeRate(base, term) != null;
    }

    @Override
    public boolean isAvailable(CurrencyUnit base, CurrencyUnit term, long timestamp) {
        return getExchangeRate(base, term, timestamp) != null;
    }

    @Override
    public ExchangeRate getExchangeRate(CurrencyUnit base, CurrencyUnit term, long timestamp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ExchangeRate getExchangeRate(CurrencyUnit base, CurrencyUnit term) {
        if (!MtGoxCurrency.isSupported(base.getCurrencyCode()) || MtGoxCurrency.isSupported(term.getCurrencyCode())) {
            return null;
        }
        final Number factor = currentRates.get(base.getCurrencyCode());
        if (factor!=null) {
        	return new ExchangeRate.Builder().withExchangeRateType(RATE_TYPE).setBase(base).setTerm(term).withFactor(factor).withProvider("MtGox").build();
        } else {
        	return null;
        }
    }

    @Override
    public ExchangeRate getReversed(ExchangeRate rate) {
        return getExchangeRate(rate.getTerm(), rate.getBase(),
                rate.getValidFromMillis());
    }

    @Override
    public CurrencyConverter getConverter() {
        return currencyConverter;
    }
    
    /**
     * Looks up the rate for a given currencyCode 
     */
    void loadRate(String curCode, boolean verbose) {
    	if (MtGoxCurrency.isSupported(curCode)) {
	        ObjectMapper m = new ObjectMapper();
	        JsonNode root;
			try {
				root = m.readTree(new URL(PROVIDER_URL + curCode + "/money/ticker").openStream());
			} catch (IOException e) {
				throw new MonetaryException("Lookup Error", e);
			}
	        LOGGER.debug("Result : " +root.path("result"));
	        if (verbose) System.out.println( "Result : " +root.path("result").getTextValue());
	        
	        JsonNode lastNode = root.findValue("last");
	        String value = lastNode.path("value").getTextValue();
	        try {
				currentRates.put(curCode, NumberFormat.getInstance(Locale.ENGLISH).parse(value));
			} catch (ParseException e) {
				LOGGER.warn("Warning", e);
			}
	        if (verbose) System.out.println( "display_short : " +lastNode.path("display_short").getTextValue());
    	} else {
    		throw new UnknownCurrencyException("Currency not supported", curCode);
    	}
    }
    
    /**
     * Looks up the rate for a given currencyCode 
     */
    void loadRate(String curCode) {
    	loadRate(curCode, false);
    }
}
