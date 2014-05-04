/*
 * Copyright (c) 2013, 2014, Werner Keil and others by the @author tag.
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
import javax.money.convert.ConversionContext;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.ProviderContext;
import javax.money.convert.RateType;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
// TODO consider switching to JSR 353

import org.javamoney.moneta.spi.DefaultNumberValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * A Currency conversion provider for MtGox service.
 *
 * @author Rajmahendra Hegde <rajmahendra@gmail.com>
 * @author Werner Keil
 * @deprecated MtGox down, implement for other exchange and retire this one in future version.
 */
public class MtGoxV2RateProvider implements ExchangeRateProvider {
    public static final RateType RATE_TYPE = DEFERRED;
    		
    		//ExchangeRateType.of("MtGox");
    
    private static final String PROVIDER_URL = "http://data.mtgox.com/api/2/BTC";

    private static final Logger LOGGER = LoggerFactory.getLogger(MtGoxV2RateProvider.class);
    
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
    
//    private CurrencyConverter currencyConverter = new DefaultCurrencyConverter(this);

    public MtGoxV2RateProvider() {
    	this(null);
    }
    
    public MtGoxV2RateProvider(String currencyCode) {
    	this.forCurrency = currencyCode;
    	
    	if(forCurrency == null) {
    		for (MtGoxCurrency currency : MtGoxCurrency.values()) {
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
        if (!MtGoxCurrency.isSupported(base.getCurrencyCode()) || MtGoxCurrency.isSupported(term.getCurrencyCode())) {
            return null;
        }
        final NumberValue  factor = DefaultNumberValue.of(currentRates.get(base.getCurrencyCode()));
        if (factor!=null) {
        	return new ExchangeRate.Builder("MtGox", RATE_TYPE).setBase(base).setTerm(term).setFactor(factor).build();
        } else {
        	return null;
        }
    }

//    @Override
//    public ExchangeRate getReversed(ExchangeRate rate) {
//        return getExchangeRate(rate.getTerm(), rate.getBase(),
//                rate.getValidFromMillis());
//    }

//    @Override
//    public CurrencyConverter getConverter() {
//        return currencyConverter;
//    }
    
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
	public ProviderContext getProviderContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAvailable(CurrencyUnit base, CurrencyUnit term,
			ConversionContext conversionContext) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAvailable(String baseCode, String termCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAvailable(String baseCode, String termCode,
			ConversionContext conversionContext) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ExchangeRate getExchangeRate(CurrencyUnit base, CurrencyUnit term,
			ConversionContext conversionContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExchangeRate getExchangeRate(String baseCode, String termCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExchangeRate getExchangeRate(String baseCode, String termCode,
			ConversionContext conversionContext) {
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
	public CurrencyConversion getCurrencyConversion(CurrencyUnit term,
			ConversionContext conversionContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CurrencyConversion getCurrencyConversion(String termCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CurrencyConversion getCurrencyConversion(String termCode,
			ConversionContext conversionContext) {
		// TODO Auto-generated method stub
		return null;
	}
}
