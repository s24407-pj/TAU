package pl.example.service

interface InventoryService {
    fun isProductAvailable(product: String): Boolean
}