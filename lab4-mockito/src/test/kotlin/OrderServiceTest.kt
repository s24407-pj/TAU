package pl.example

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import pl.example.exception.PaymentException
import pl.example.exception.ProductUnavailableException
import pl.example.model.Order
import pl.example.service.InventoryService
import pl.example.service.NotificationService
import pl.example.service.OrderService
import pl.example.service.PaymentService

class OrderServiceTest {

    @Test
    fun `should process the order`() {
        // Given
        val paymentService: PaymentService = mock()
        val inventoryService: InventoryService = mock()
        val notificationService: NotificationService = mock()

        val underTest = OrderService(paymentService, inventoryService, notificationService)
        val order = Order("Laptop", 1000.0, "email@wp.pl")

        `when`(inventoryService.isProductAvailable(order.product)).thenReturn(true)
        `when`(paymentService.processPayment(order.amount)).thenReturn(true)

        // When
        underTest.placeOrder(order)

        // Then
        verify(inventoryService).isProductAvailable(order.product)
        verify(paymentService).processPayment(order.amount)
        verify(notificationService).sendNotification(order.email)
    }

    @Test
    fun `test place order product not available`() {
        // Given
        val paymentService: PaymentService = mock()
        val inventoryService: InventoryService = mock()
        val notificationService: NotificationService = mock()

        val underTest = OrderService(paymentService, inventoryService, notificationService)
        val order = Order("Laptop", 1000.0, "email@wp.pl")

        `when`(inventoryService.isProductAvailable(order.product)).thenReturn(false)

        // When & Then
        assertThrows<ProductUnavailableException> {
            underTest.placeOrder(order)
        }


        verify(inventoryService).isProductAvailable(order.product)
        verifyNoInteractions(paymentService)
        verifyNoInteractions(notificationService)
    }

    @Test
    fun `test place order payment failed`() {
        // Given
        val paymentService: PaymentService = mock()
        val inventoryService: InventoryService = mock()
        val notificationService: NotificationService = mock()

        val underTest = OrderService(paymentService, inventoryService, notificationService)
        val order = Order("Laptop", 1000.0, "email@wp.pl")

        `when`(inventoryService.isProductAvailable(order.product)).thenReturn(true)
        `when`(paymentService.processPayment(order.amount)).thenReturn(false)

        // When & Then

        assertThrows<PaymentException> {
            underTest.placeOrder(order)
        }


        verify(inventoryService).isProductAvailable(order.product)
        verify(paymentService).processPayment(order.amount)
        verifyNoInteractions(notificationService)
    }

    @Test
    fun `test place order payment throws exception`() {
        // Given
        val paymentService: PaymentService = mock()
        val inventoryService: InventoryService = mock()
        val notificationService: NotificationService = mock()

        val underTest = OrderService(paymentService, inventoryService, notificationService)
        val order = Order("Laptop", 1000.0, "email@wp.pl")

        `when`(inventoryService.isProductAvailable(order.product)).thenReturn(true)
        doThrow(IllegalArgumentException())
            .`when`(paymentService).processPayment(order.amount)

        assertThrows<PaymentException> {
            underTest.placeOrder(order)
        }


        verify(inventoryService).isProductAvailable(order.product)
        verify(paymentService).processPayment(order.amount)
        verifyNoInteractions(notificationService)
    }
}
