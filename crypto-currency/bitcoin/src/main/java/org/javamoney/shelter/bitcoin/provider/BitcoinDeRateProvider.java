/*
 * Copyright (c) 2013, 2021, Werner Keil and others by the @author tag.
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

import static javax.money.convert.RateType.DEFERRED;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.money.CurrencyUnit;
import javax.money.MonetaryException;
import javax.money.NumberValue;
import javax.money.UnknownCurrencyException;
import javax.money.convert.ConversionQuery;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.ProviderContext;
import javax.money.convert.RateType;


import org.javamoney.moneta.ExchangeRateBuilder;
import org.javamoney.moneta.spi.DefaultNumberValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
//TODO consider switching to Jakarta JSON Binding

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * A Currency conversion provider for Bitcoin.de service.
 *
 * @author Werner Keil
 */
public class BitcoinDeRateProvider implements ExchangeRateProvider {
    public static final RateType RATE_TYPE = DEFERRED;
    
    private static final String PROVIDER_URL = "https://bitcoinapi.de/v1/${YOUR_API_KEY}/rate.json";

    private static final Logger LOGGER = LoggerFactory.getLogger(BitcoinDeRateProvider.class);
    
    /**
     * Contains supported currency codes.
     */
    public static enum SupportedCurrency {

        EUR("EUR"), USD("USD");
        private final String currencyCode;

        public String code() {
            return currencyCode;
        }

        private SupportedCurrency(String code) {
            this.currencyCode = code;
        }

        public static boolean contains(String newCurrency) {
            try {
                valueOf(newCurrency);
            } catch (IllegalArgumentException e) {
                return false;
            }
            return true;
        }
    }
    
    private final String forCurrency;
    
    private final Map<String, Number> currentRates = new ConcurrentHashMap<>();
    
    public BitcoinDeRateProvider() {
    	this(null);
    }
    
    public BitcoinDeRateProvider(String currencyCode) {
    	this.forCurrency = currencyCode;
    	
    	if(forCurrency == null) {
    		for (SupportedCurrency currency : SupportedCurrency.values()) {
    			loadRate(currency.code());
    		}
    	} else {
    		loadRate(forCurrency);
    	}
    }

    public RateType getRateType() {
        return RATE_TYPE;
    }

    @Override
    public boolean isAvailable(CurrencyUnit base, CurrencyUnit term) {
        return getExchangeRate(base, term) != null;
    }

    public boolean isAvailable(CurrencyUnit base, CurrencyUnit term, long timestamp) {
        return getExchangeRate(base, term, timestamp) != null;
    }
    
    public ExchangeRate getExchangeRate(CurrencyUnit base, CurrencyUnit term, long timestamp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ExchangeRate getExchangeRate(CurrencyUnit base, CurrencyUnit term) {
        if (!SupportedCurrency.contains(base.getCurrencyCode()) || SupportedCurrency.contains(term.getCurrencyCode())) {
            return null;
        }
        final NumberValue  factor = DefaultNumberValue.of(currentRates.get(base.getCurrencyCode()));
        if (factor!=null) {
        	return new ExchangeRateBuilder("Bitcoin.de", RATE_TYPE).setBase(base).setTerm(term).setFactor(factor).build();
        } else {
        	return null;
        }
    }
    
    /**
     * Looks up the rate for a given currencyCode 
     */
    void loadRate(String curCode, boolean verbose) {
    	if (SupportedCurrency.contains(curCode)) {
	        ObjectMapper m = new ObjectMapper();
	        JsonNode root;
			try {
				root = m.readTree(new URL(PROVIDER_URL + curCode + "/money/ticker").openStream());
			} catch (IOException e) {
				throw new MonetaryException("Lookup Error", e);
			}
	        LOGGER.debug("Result : " +root.path("result"));
	        if (verbose) System.out.println( "Result : " +root.path("result").textValue());
	        
	        JsonNode lastNode = root.findValue("last");
	        String value = lastNode.path("value").textValue();
	        try {
				currentRates.put(curCode, NumberFormat.getInstance(Locale.ENGLISH).parse(value));
			} catch (ParseException e) {
				LOGGER.warn("Warning", e);
			}
	        if (verbose) System.out.println( "display_short : " +lastNode.path("rate_weighted").textValue());
    	} else {
    		throw new UnknownCurrencyException(curCode);
    	}
    }
    
    /**
     * Looks up the rate for a given currencyCode 
     */
    void loadRate(String curCode) {
    	loadRate(curCode, false);
    }

	@Override
	public boolean isAvailable(String baseCode, String termCode) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public ExchangeRate getExchangeRate(String baseCode, String termCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExchangeRate getReversed(ExchangeRate rate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CurrencyConversion getCurrencyConversion(CurrencyUnit term) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public CurrencyConversion getCurrencyConversion(String termCode) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public CurrencyConversion getCurrencyConversion(ConversionQuery arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExchangeRate getExchangeRate(ConversionQuery arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAvailable(ConversionQuery arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ProviderContext getContext() {
		// TODO Auto-generated method stub
		return null;
	}
}
