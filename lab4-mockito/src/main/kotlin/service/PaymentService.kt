package pl.example.service

interface PaymentService {
    fun processPayment(amount: Double): Boolean
}