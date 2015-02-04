package org.javamoney.shelter.bitcoin.provider;

import org.javamoney.moneta.CurrencyUnitBuilder;
import org.javamoney.moneta.Money;
import org.junit.Test;

import javax.money.CurrencyQuery;
import javax.money.CurrencyQueryBuilder;
import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryCurrencies;
import javax.money.UnknownCurrencyException;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * First cut at a test for a BitcoinCurrencyProvider
 *
 * @author Sean Gilligan
 */
public class BitcoinCurrencyProviderTest {

    @Test
    public void canCreateNewInstance() {
        BitcoinCurrencyProvider provider = new BitcoinCurrencyProvider();
        assertNotNull(provider);
    }

    @Test
    public void returnsBitcoinForEmptyQuery() {
        BitcoinCurrencyProvider provider = new BitcoinCurrencyProvider();
        assertNotNull(provider);
        CurrencyQuery query = CurrencyQueryBuilder.of().build();
        Set<CurrencyUnit> currencies = provider.getCurrencies(query);
        assertEquals("returned 1 currency", 1, currencies.size());
        CurrencyUnit btc = (CurrencyUnit) currencies.toArray()[0];
        assertEquals("currency is BTC", "BTC", btc.getCurrencyCode());
    }

    @Test
    public void returnsBitcoinForBTCQuery() {
        BitcoinCurrencyProvider provider = new BitcoinCurrencyProvider();
        assertNotNull(provider);
        CurrencyQuery query = CurrencyQueryBuilder.of().setCurrencyCodes("BTC").build();
        Set<CurrencyUnit> currencies = provider.getCurrencies(query);
        assertEquals("returned 1 currency", 1, currencies.size());
        CurrencyUnit btc = (CurrencyUnit) currencies.toArray()[0];
        assertEquals("currency is BTC", "BTC", btc.getCurrencyCode());
    }

    @Test
    public void returnsEmptyForUSDQuery() {
        BitcoinCurrencyProvider provider = new BitcoinCurrencyProvider();
        assertNotNull(provider);
        CurrencyQuery query = CurrencyQueryBuilder.of().setCurrencyCodes("USD").build();
        Set<CurrencyUnit> currencies = provider.getCurrencies(query);
        assertEquals("returned 0 currencies", 0, currencies.size());
    }

    @Test(expected=UnknownCurrencyException.class)
    public void canNOTBeFoundViaMonetaryCurrencies() {
        // Since we're not registered properly MonetaryCurrencies.getCurrency should throw an exception
        CurrencyUnit btc = MonetaryCurrencies.getCurrency("BTC");
    }

    @Test()
    public void WeCanMakeMoneyNow() {
        // Since we're not registered properly MonetaryCurrencies.getCurrency should throw an exception
        BitcoinCurrencyProvider provider = new BitcoinCurrencyProvider();
        assertNotNull(provider);
        CurrencyQuery query = CurrencyQueryBuilder.of().setCurrencyCodes("BTC").build();
        Set<CurrencyUnit> currencies = provider.getCurrencies(query);
        assertEquals("returned 1 currency", 1, currencies.size());
        CurrencyUnit btc = (CurrencyUnit) currencies.toArray()[0];
        assertEquals("currency is BTC", "BTC", btc.getCurrencyCode());

        MonetaryAmount oneBitcoin = Money.of(1, btc);
        assertNotNull(oneBitcoin);
        assertEquals("number is correct", 1L, oneBitcoin.getNumber().longValueExact());
        assertEquals("units is correct", btc, oneBitcoin.getCurrency());
    }

    @Test
    public void WeDoNotReallyNeedAProviderToMakeMoney() {
        CurrencyUnit btc = CurrencyUnitBuilder.of("BTC", "ad hoc btc provider").build();
        MonetaryAmount oneBitcoin = Money.of(1, btc);
        assertNotNull(oneBitcoin);
        assertEquals("number is correct", 1L, oneBitcoin.getNumber().longValueExact());
        assertEquals("units is correct", btc, oneBitcoin.getCurrency());
    }

}
