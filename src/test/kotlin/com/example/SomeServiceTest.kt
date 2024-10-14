package com.example

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class SomeServiceTest {
    private val someService = SomeService()

    @Test
    fun firstNumberIsNotGreaterThanSecond() {
        val result = someService.isFirstNumberGreaterThanSecond(1, 2)

        assertFalse(result)
    }

    @Test
    fun firstNumberIsGreaterThanSecond() {
        val result = someService.isFirstNumberGreaterThanSecond(2, 1)

        assertTrue(result)
    }

    @Test
    fun shouldAddTwoNumbers() {
        val result = someService.addTwoNumbers(1, 2)

        assertEquals(3, result)
    }

    @Test
    fun shouldThrowWhenSecondNumberIsZero() {
        assertThrows(IllegalArgumentException::class.java) {
            someService.divideTwoNumbers(1f, 0f)
        }
    }

    @Test
    fun shouldDivideTwoNumbers() {
        val result = someService.divideTwoNumbers(4f, 2f)

        assertEquals(2f, result)
    }

    @Test
    fun shouldDivideTwoNumbersWithFraction() {
        val result = someService.divideTwoNumbers(5f, 2f)

        assertEquals(2.5f, result)
    }

    @Test
    fun shouldMultiplyTwoNumbers() {
        val result = someService.multiplyTwoNumbers(2, 3)

        assertEquals(6, result)
    }

    @Test
    fun shouldSubtractTwoNumbers() {
        val result = someService.subtractTwoNumbers(5, 3)

        assertEquals(2, result)
    }
}