package org.javamoney.shelter.bitcoin.provider;

import org.javamoney.moneta.CurrencyUnitBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.money.convert.CurrencyConversionException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

public class CoinbaseRateProviderTest {
    private static CoinbaseRateProvider coinbaseRateProvider;

    @BeforeClass
    public static void setUpBeforeClass() {
        coinbaseRateProvider = new CoinbaseRateProvider();
        CurrencyUnitBuilder.of("BTC", "CoinbaseRateProvider")
                .setDefaultFractionDigits(8)
                .build(true);
    }

    @AfterClass
    public static void tearDownAfterClass() {
        coinbaseRateProvider = null;
    }

    @Test
    public void testGetExchangeRate() {
        assertNotNull(coinbaseRateProvider.getExchangeRate("BTC", "USD"));
    }

    @Test
    public void testGetExchangeRateWithNotSupportedBaseCurrency() {
        assertThrows(CurrencyConversionException.class, () -> coinbaseRateProvider.getExchangeRate("USD", "BTC"));
    }

    @Test
    public void testGetExchangeRateWithNotSupportedTermCurrency() {
        assertThrows(CurrencyConversionException.class, () -> coinbaseRateProvider.getExchangeRate("BTC", "ZWL"));
    }
}