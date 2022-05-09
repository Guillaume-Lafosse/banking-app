package com.guillaume.paymentlibrary

class CardNumberValidator {
    companion object {
        //We use Luhn algorithm to validate a card number
        fun isValid(cardNumber: String): Boolean {
            val nDigits = cardNumber.length

            var nSum = 0
            var isSecond = false
            for (i in nDigits - 1 downTo 0) {

                var d = cardNumber[i] - '0'
                if (isSecond) d *= 2
                nSum += d / 10
                nSum += d % 10

                isSecond = !isSecond
            }
            return (nSum % 10 == 0)
        }
    }
}