package org.javamoney.shelter.bitcoin.provider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.javamoney.moneta.convert.ExchangeRateBuilder;
import org.javamoney.moneta.spi.AbstractRateProvider;
import org.javamoney.moneta.spi.DefaultNumberValue;

import javax.money.CurrencyUnit;
import javax.money.MonetaryException;
import javax.money.convert.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CoinbaseRateProvider extends AbstractRateProvider {
    private static final RateType RATE_TYPE = RateType.DEFERRED;
    private static final ProviderContext CONTEXT = ProviderContextBuilder.of("CoinbaseRateProvider", RATE_TYPE)
            .set("providerDescription", "Coinbase - Bitcoin exchange rate provider")
            .build();
    private static final String DEFAULT_BASE_CURRENCY = "BTC";

    private final List<String> supportedCurrencies = new ArrayList<>();
    private final Map<String, Number> rates = new ConcurrentHashMap<>();

    public CoinbaseRateProvider() {
        super(CONTEXT);
        loadSupportedCurrencies();
    }

    @Override
    public ExchangeRate getExchangeRate(ConversionQuery conversionQuery) {
        CurrencyUnit baseCurrency = conversionQuery.getBaseCurrency();
        CurrencyUnit termCurrency = conversionQuery.getCurrency();
        ConversionContext conversionContext = ConversionContext.of(getContext().getProviderName(), RATE_TYPE);

        if (!DEFAULT_BASE_CURRENCY.equals(baseCurrency.getCurrencyCode())) {
            throw new CurrencyConversionException(baseCurrency, termCurrency, conversionContext, "Base currency not supported: " + baseCurrency);
        }

        if (!supportedCurrencies.contains(termCurrency.getCurrencyCode())) {
            throw new CurrencyConversionException(baseCurrency, termCurrency, conversionContext, "Term currency not supported: " + termCurrency);
        }

        loadRates();

        Number rate = rates.get(termCurrency.getCurrencyCode());
        if (rate == null) {
            throw new CurrencyConversionException(baseCurrency, termCurrency, conversionContext, "Rate not available for currency: " + termCurrency);
        }
        return new ExchangeRateBuilder(conversionContext)
                .setBase(baseCurrency)
                .setTerm(termCurrency)
                .setFactor(DefaultNumberValue.of(rate))
                .build();
    }

    private void loadSupportedCurrencies() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()){
            String url = "https://api.coinbase.com/v2/currencies";
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)){
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(response.getEntity().getContent());
                JsonNode dataNode = jsonNode.get("data");
                dataNode.forEach(node -> supportedCurrencies.add(node.get("id").asText()));
            }
        } catch (IOException e) {
            throw new MonetaryException("Failed to load supported currencies from Coinbase API", e);
        }
    }

    private void loadRates() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()){
            String url = "https://api.coinbase.com/v2/exchange-rates?currency=" + DEFAULT_BASE_CURRENCY;
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)){
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(response.getEntity().getContent());
                JsonNode ratesNode = jsonNode.get("data").get("rates");
                ratesNode.fields().forEachRemaining(entry -> rates.put(entry.getKey(), entry.getValue().asDouble()));
            }
        } catch (IOException e) {
            throw new MonetaryException("Failed to load exchange rates from Coinbase API", e);
        }
    }
}
