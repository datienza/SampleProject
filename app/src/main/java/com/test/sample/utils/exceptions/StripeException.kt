package com.farmdrop.customer.utils.exceptions

class StripeException(val errorCode: Int, errorMessage: String?) : Exception(errorMessage)