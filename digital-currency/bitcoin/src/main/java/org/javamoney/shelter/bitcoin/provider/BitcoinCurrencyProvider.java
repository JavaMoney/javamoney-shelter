package org.javamoney.shelter.bitcoin.provider;

import javax.money.CurrencyContext;
import javax.money.CurrencyContextBuilder;
import javax.money.CurrencyQuery;
import javax.money.CurrencyUnit;
import javax.money.MonetaryAmountFactoryQuery;
import javax.money.MonetaryAmountFactoryQueryBuilder;
import javax.money.MonetaryContext;
import javax.money.spi.CurrencyProviderSpi;
import java.util.Collections;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

import org.javamoney.moneta.CurrencyUnitBuilder;
import org.javamoney.moneta.Money;

/**
 *  A clueless attempt at a BitcoinCurrencyProvider based on (out of date?) code
 *  in the Moneta User's Guide.
 *
 * @author Sean Gilligan
 */
public final class BitcoinCurrencyProvider implements CurrencyProviderSpi {

    // Not sure what to do here...
    private final CurrencyContext CONTEXT = CurrencyContextBuilder.of("BitcoinCurrencyContextProvider")
                                                .build();

    private Set<CurrencyUnit> bitcoinSet = new HashSet<>();

    public BitcoinCurrencyProvider(){
        bitcoinSet.add(CurrencyUnitBuilder.of("BTC", CONTEXT).build());
        bitcoinSet = Collections.unmodifiableSet(bitcoinSet);
    }

    @Override
    public String getProviderName(){
        return "bitcoin";
    }

    /**
     * Return a {@link CurrencyUnit} instances matching the given
     * {@link javax.money.CurrencyContext}.
     *
     * @param query the {@link javax.money.CurrencyQuery} containing the parameters determining the query. not null.
     * @return the corresponding {@link CurrencyUnit}s matching, never null.
     */
    @Override
    public Set<CurrencyUnit> getCurrencies(CurrencyQuery query){
        // only ensure BTC is the code, or it is a default query.
        if(query.getCurrencyCodes().contains("BTC") || query.getCurrencyCodes().isEmpty()){
            return bitcoinSet;
        }
        return Collections.emptySet();
    }

}