package pl.example.service

import pl.example.exception.PaymentException
import pl.example.exception.ProductUnavailableException
import pl.example.model.Order

class OrderService(
    private val paymentService: PaymentService,
    private val inventoryService: InventoryService,
    private val notificationService: NotificationService
) {
    fun placeOrder(order: Order) {
        if (!inventoryService.isProductAvailable(order.product)) {
            throw ProductUnavailableException("${order.product} is not available")
        }

        try {
            val isPaymentSuccessful = paymentService.processPayment(order.amount)
            if (!isPaymentSuccessful) {
                throw PaymentException("Payment failed")
            }

        } catch (e: IllegalArgumentException) {
            throw PaymentException("Payment failed")
        }

        notificationService.sendNotification(order.email)
    }
}