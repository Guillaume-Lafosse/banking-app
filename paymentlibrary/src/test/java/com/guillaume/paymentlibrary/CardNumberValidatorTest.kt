package com.guillaume.paymentlibrary

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CardNumberValidatorTest {
    @Test
    fun cardNumber_IsValid() {
        //given
        val cardNumber = "1111111111111117"

        //when && then
        assertTrue(CardNumberValidator.isValid(cardNumber))
    }

    @Test
    fun cardNumber_IsNotValid() {
        //given
        val cardNumber = "1111111111111119"

        //when && then
        assertFalse(CardNumberValidator.isValid(cardNumber))
    }
}