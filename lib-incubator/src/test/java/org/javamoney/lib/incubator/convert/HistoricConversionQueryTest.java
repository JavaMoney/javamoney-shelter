/*
 * Copyright (c) 2013, 2015, Werner Keil and others by the @author tag.
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
package org.javamoney.lib.incubator.convert;

import static org.testng.Assert.assertEquals;

import java.time.LocalDate;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.convert.ConversionQuery;

import org.javamoney.lib.incubator.convert.HistoricConversionQueryBuilder;
import org.testng.annotations.Test;

public final class HistoricConversionQueryTest {

	@Test(expectedExceptions = NullPointerException.class)
	public void shouldReturnErrorWhendCurrencyIsNull() {
		HistoricConversionQueryBuilder.of(null);
	}

	@Test
	public void shouldCreateUsingCurrency() {
		CurrencyUnit real = Monetary.getCurrency("BRL");
		ConversionQuery query = HistoricConversionQueryBuilder.of(real).build();
		assertEquals(query.getCurrency(), real);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void shouldReturnErrorWhenCreateWithDayUsingLocalTimeNull() {
		CurrencyUnit real = Monetary.getCurrency("BRL");
		HistoricConversionQueryBuilder.of(real).withDay(null).build();
	}

	@Test
	public void shouldCreateUsingLocalTime() {
		CurrencyUnit real = Monetary.getCurrency("BRL");
		LocalDate localDate = LocalDate.now();
		ConversionQuery query = HistoricConversionQueryBuilder.of(real).withDay(localDate).build();
		assertEquals(query.getCurrency(), real);
		assertEquals(query.get(LocalDate.class), localDate);
	}


	@Test(expectedExceptions = IllegalArgumentException.class)
	public void shouldReturnErrorWhenCreateWithDaysUsingLocalTimeNull() {
		CurrencyUnit real = Monetary.getCurrency("BRL");
		LocalDate localDate = null;
		HistoricConversionQueryBuilder.of(real).withDays(localDate).build();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void shouldReturnErrorWhenCreateWithDaysEmptyParameters() {
		CurrencyUnit real = Monetary.getCurrency("BRL");
		HistoricConversionQueryBuilder.of(real).withDays().build();
	}

	@Test
	public void shouldCreateUsingDays() {
		CurrencyUnit real = Monetary.getCurrency("BRL");
		LocalDate today = LocalDate.now();
		LocalDate yesterday = today.minusDays(1);
		LocalDate tomorrow = today.plusDays(1);
		ConversionQuery query = HistoricConversionQueryBuilder.of(real).withDays(today, yesterday, tomorrow).build();
		assertEquals(query.getCurrency(), real);
		assertEquals(query.get(LocalDate[].class), new LocalDate[] { tomorrow, today, yesterday });
	}
	//
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void shouldReturnErrorWhenCreateWithDaysPriorityDefinedUsingLocalTimeNull() {
		CurrencyUnit real = Monetary.getCurrency("BRL");
		LocalDate localDate = null;
		HistoricConversionQueryBuilder.of(real).withDaysPriorityDefined(localDate).build();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void shouldReturnErrorWhenCreateWithDaysPriorityDefinedEmptyParameters() {
		CurrencyUnit real = Monetary.getCurrency("BRL");
		HistoricConversionQueryBuilder.of(real).withDaysPriorityDefined().build();
	}

	@Test
	public void shouldCreateWithDaysPriorityDefined() {
		CurrencyUnit real = Monetary.getCurrency("BRL");
		LocalDate today = LocalDate.now();
		LocalDate yesterday = today.minusDays(1);
		LocalDate tomorrow = today.plusDays(1);
		ConversionQuery query = HistoricConversionQueryBuilder.of(real).withDaysPriorityDefined(today, yesterday, tomorrow).build();
		assertEquals(query.getCurrency(), real);
		assertEquals(query.get(LocalDate[].class), new LocalDate[] { today, yesterday, tomorrow });
	}


	@Test(expectedExceptions = NullPointerException.class)
	public void shouldReturnErrorWhenCreateWithDaysBetweenBeginDateIsNull() {
		CurrencyUnit real = Monetary.getCurrency("BRL");
		HistoricConversionQueryBuilder.of(real).withDaysBetween(null, LocalDate.now()).build();
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void shouldReturnErrorWhenCreateWithDaysBetweenEndDateIsNull() {
		CurrencyUnit real = Monetary.getCurrency("BRL");
		HistoricConversionQueryBuilder.of(real).withDaysBetween(LocalDate.now(), null).build();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void shouldReturnErrorWhenCreateWithDaysBetweenMistakePeriod() {
		CurrencyUnit real = Monetary.getCurrency("BRL");
		HistoricConversionQueryBuilder.of(real).withDaysBetween(LocalDate.now(), LocalDate.now().minusDays(2)).build();
	}

	@Test
	public void shouldReturnCreateWithDaysBetween() {
		CurrencyUnit real = Monetary.getCurrency("BRL");
		LocalDate today = LocalDate.now();
		LocalDate yesterday = today.minusDays(1);
		LocalDate tomorrow = today.plusDays(1);
		ConversionQuery query = HistoricConversionQueryBuilder.of(real).withDaysBetween(yesterday, tomorrow).build();
		assertEquals(query.getCurrency(), real);
		assertEquals(query.get(LocalDate[].class), new LocalDate[] { tomorrow, today, yesterday });
	}
}
