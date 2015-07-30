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
package org.javamoney.lib.incubator;

import java.util.Collection;
import java.util.Locale;

import javax.money.spi.Bootstrap;
import javax.money.spi.CurrencyProviderSpi;
import javax.money.spi.RoundingProviderSpi;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Tests for {@link javax.money.spi.Bootstrap}.
 */
@SuppressWarnings("unchecked")
@Test
public class BootstrapTest {

    @Test
    public void testGetServices() throws Exception {
    	Collection<CurrencyProviderSpi> services = Collection.class.cast(Bootstrap.getServices(CurrencyProviderSpi.class));
        assertNotNull(services);
        assertFalse(services.isEmpty());
    }

    @Test
    public void testGetService() throws Exception {
    	RoundingProviderSpi rounding = Bootstrap.getService(RoundingProviderSpi.class);
        assertNotNull(rounding);
        //assertTrue(num.equals(5));
    }

    @Test
    public void testGetService_BadCase() throws Exception {
        assertNull(Bootstrap.getService(Locale.class));
    }
}
