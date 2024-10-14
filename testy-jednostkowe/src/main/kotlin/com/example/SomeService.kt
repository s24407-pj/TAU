package com.example

class SomeService {
    fun isFirstNumberGreaterThanSecond(first: Int, second: Int): Boolean =
        first > second


    fun addTwoNumbers(first: Int, second: Int): Int =
        first + second


    fun divideTwoNumbers(first: Float, second: Float): Float {
        if (second == 0f) throw IllegalArgumentException("Cannot divide by zero")

        return first / second
    }

    fun multiplyTwoNumbers(first: Int, second: Int): Int =
        first * second

    fun subtractTwoNumbers(first: Int, second: Int): Int =
        first - second

    fun isNumberEven(number: Int): Boolean =
        number % 2 == 0
}