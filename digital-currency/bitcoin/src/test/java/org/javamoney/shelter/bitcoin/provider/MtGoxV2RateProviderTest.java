/*
 * Copyright (c) 2013-2014, Werner Keil and others by the @author tag.
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

import javax.money.convert.ExchangeRateProvider;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Werner Keil
 * @author Rajmahendra Hegde <rajmahendra@gmail.com>
 */
@Ignore
public class MtGoxV2RateProviderTest {

    private static final ExchangeRateProvider MTGOX_PROVIDER = new MtGoxV2RateProvider();
    
    public MtGoxV2RateProviderTest() {
    }
    
    @Test
    public void testMtGoxConversionProviderIsNotNull() {
    	assertNotNull(MTGOX_PROVIDER);
    	//assertEquals("MtGox", MTGOX_PROVIDER.getProviderContext().getProvider());
    }
}