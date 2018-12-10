package com.vanrepin.assignment.exceptions

import com.vanrepin.assignment.model.CurrencyCode

class InvalidCurrencyCodeException(currencyCode: CurrencyCode): IllegalArgumentException("Invalid currency code: ${currencyCode.value}")