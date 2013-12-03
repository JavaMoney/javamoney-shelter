/*
 * Copyright (c) 2013, Werner Keil.
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

import java.io.Serializable;
import java.util.Currency;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.javamoney.util.Displayable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO if BTC became part of integral RI consider using JUL, but for now SLF4J is more consistent with provider
import javax.money.CurrencyUnit;

/**
 * Bitcoin implementation of the {@link CurrencyUnit}
 * 
 * @version 0.4
 * @author Werner Keil
 */
public final class BTCCurrency implements CurrencyUnit, Displayable, Serializable,
		Comparable<CurrencyUnit> {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -4745360126748244459L;

	/**
	 * The predefined name space for BTC currencies, similar to {@link Currency}
	 * .
	 */
	public static final String BTC_NAMESPACE = "BTC";

	static final String BASE_CURRENCY_CODE = "BTC";

	/** namespace for this currency. */
	private final String namespace;
	/** currency code for this currency. */
	private final String currencyCode;
	/** numeric code, or -1. */
	private final int numericCode;
	/** fraction digits, or -1. */
	private final int defaultFractionDigits;
	/** valid from, or {@code null}. */
	private final Long validFrom;
	/** valid until, or {@code null}. */
	private final Long validUntil;
	/** true, if legal tender. */
	private final boolean legalTender;
	/** true, if it is a virtual currency. */
	private final boolean virtual;

	private final String displayName;
	
	private static final Map<String, CurrencyUnit> CACHED = new ConcurrentHashMap<String, CurrencyUnit>();

	private static final Logger LOGGER = LoggerFactory.getLogger(BTCCurrency.class);

	/**
	 * Private constructor.
	 * 
	 * @param currency
	 */
	private BTCCurrency(String namespace, String code, int numCode,
			int fractionDigits, Long validFrom, Long validUntil, boolean legal) {
		this.namespace = namespace;
		this.currencyCode = code;
		this.numericCode = numCode;
		this.defaultFractionDigits = fractionDigits;
		this.validFrom = validFrom;
		this.validUntil = validUntil;
		this.legalTender = legal;
		this.virtual = true;
		this.displayName = "Bitcoin";
	}

	/**
	 * Private constructor.
	 * 
	 * @param currency
	 */
	private BTCCurrency(String namespace, String code) {
		this(namespace, code, -1, 8, null, null, false);
	}

	// public static CurrencyUnit of(Currency currency) {
	// String key = BTC_NAMESPACE + ':' + currency.getCurrencyCode();
	// CurrencyUnit cachedItem = CACHED.get(key);
	// if (cachedItem == null) {
	// cachedItem = new JDKCurrencyAdapter(currency);
	// CACHED.put(key, cachedItem);
	// }
	// return cachedItem;
	// }

	public static CurrencyUnit of() {
		return of(BASE_CURRENCY_CODE);
	}

	public static CurrencyUnit of(String currencyCode) {
		return of(BTC_NAMESPACE, currencyCode);
	}

	public static CurrencyUnit of(String namespace, String currencyCode) {
		String key = namespace + ':' + currencyCode;
		CurrencyUnit cu = CACHED.get(key);
		if (cu == null && namespace.equals(BTC_NAMESPACE)) {
			return new BTCCurrency(namespace, currencyCode);
		}
		return cu;
	}

	public boolean isVirtual() {
		return virtual;
	}

	/**
	 * Get the namespace of this {@link CurrencyUnit}.
	 */
	public String getNamespace() {
		return namespace;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CurrencyUnit#getNumericCode()
	 */
	public int getNumericCode() {
		return numericCode;
	}

	public int getDefaultFractionDigits() {
		return defaultFractionDigits;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CurrencyUnit#isLegalTender()
	 */
	public boolean isLegalTender() {
		return legalTender; // TODO this may not work for BTC or other
							// currencies, as e.g. US or Germany declared it
							// Legal Tender, other countries don't yet.
	}

	public int getCashRounding() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public int compareTo(CurrencyUnit currency) {
		int compare = 0;
		if (compare == 0) {
			compare = getCurrencyCode().compareTo(currency.getCurrencyCode());
		}
		return compare;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (BTC_NAMESPACE.equals(namespace)) {
			return currencyCode;
		}
		return namespace + ':' + currencyCode;
	}

	public static final class Builder {
		/** namespace for this currency. */
		private String namespace;
		/** currency code for this currency. */
		private String currencyCode;
		/** numeric code, or -1. */
		private int numericCode = -1;
		/** fraction digits, or -1. */
		private int defaultFractionDigits = -1;
		/** valid from, or {@code null}. */
		private Long validFrom;
		/** valid until, or {@code null}. */
		private Long validUntil;
		/** true, if legal tender. */
		private boolean legalTender = true;

		public Builder() {
		}

		public Builder(String currencyCode) {
			this(BTC_NAMESPACE, currencyCode);
		}

		public Builder(String namespace, String currencyCode) {
			setNamespace(namespace);
			setCurrencyCode(currencyCode);
		}

		public Builder setNamespace(String namespace) {
			if (namespace == null) {
				throw new IllegalArgumentException("namespace may not be null.");
			}
			this.namespace = namespace;
			return this;
		}

		public Builder setCurrencyCode(String currencyCode) {
			if (currencyCode == null) {
				throw new IllegalArgumentException(
						"currencyCode may not be null.");
			}
			this.currencyCode = currencyCode;
			return this;
		}

		public Builder setDefaultFractionDigits(int defaultFractionDigits) {
			if (defaultFractionDigits < -1) {
				throw new IllegalArgumentException(
						"Invalid value for defaultFractionDigits: "
								+ defaultFractionDigits);
			}
			this.defaultFractionDigits = defaultFractionDigits;
			return this;
		}

		public Builder setNumericCode(int numericCode) {
			if (numericCode < -1) {
				throw new IllegalArgumentException(
						"Invalid value for numericCode: " + numericCode);
			}
			this.numericCode = numericCode;
			return this;
		}

		public Builder setValidFrom(Long validFrom) {
			this.validFrom = validFrom;
			return this;
		}

		public Builder setValidUntil(Long validUntil) {
			this.validUntil = validUntil;
			return this;
		}

		public Builder setLegalTender(boolean legalTender) {
			this.legalTender = legalTender;
			return this;
		}

		// public Builder setVirtual(boolean virtual) {
		// this.virtual = virtual;
		// return this;
		// }

		public String getNamespace() {
			return this.namespace;
		}

		public String getCurrencyCode() {
			return this.currencyCode;
		}

		public int getDefaultFractionDigits() {
			return this.defaultFractionDigits;
		}

		public int getNumericCode() {
			return this.numericCode;
		}

		public Long getValidFrom() {
			return this.validFrom;
		}

		public Long getValidUntil() {
			return this.validUntil;
		}

		public boolean isLegalTender() {
			return this.legalTender;
		}

		// public boolean isVirtual() {
		// return this.virtual;
		// }

		public boolean isBuildable() {
			return namespace != null && currencyCode != null;
		}

		public CurrencyUnit build() {
			return build(true);
		}

		public CurrencyUnit build(boolean cache) {
			if (!isBuildable()) {
				throw new IllegalStateException(
						"Can not build CurrencyUnitImpl.");
			}
			if (cache) {
				if (validUntil != null) {
					LOGGER.warn("CurrencyUnit build: Can only cache currencies that have no validity constraints.");
					cache = false;
				}
				if (validFrom != null) {
					if (validFrom.longValue() > System.currentTimeMillis()) {
						LOGGER.warn("CurrencyUnit build: Can only cache currencies that are already valid.");
						cache = false;
					}
				}
			}
			if (cache) {
				String key = namespace + ':' + currencyCode;
				CurrencyUnit current = CACHED.get(key);
				if (current == null) {
					current = new BTCCurrency(namespace, currencyCode,
							numericCode, defaultFractionDigits, validFrom,
							validUntil, legalTender);
					CACHED.put(key, current);
				}
				return current;
			}
			return new BTCCurrency(namespace, currencyCode, numericCode,
					defaultFractionDigits, validFrom, validUntil, legalTender);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Displayable#getDisplayName()
	 */
	@Override
	public String getDisplayName(Locale locale) {
		return displayName;
	}

	public String getDisplayName() {
		return getDisplayName(Locale.getDefault());
	}
}
