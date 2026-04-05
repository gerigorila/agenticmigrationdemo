package com.gerigorila.agenticmigrationdemo.data.repository

import com.gerigorila.agenticmigrationdemo.domain.model.Product

class ProductRepository {

    private val products = listOf(
        Product(1, "Wireless Headphones", 79.99,
            "Bluetooth 5.0 over-ear headphones with noise cancellation and 30-hour battery life.",
            "https://example.com/headphones.jpg"),
        Product(2, "Mechanical Keyboard", 129.99,
            "RGB backlit mechanical keyboard with Cherry MX switches and aluminum frame.",
            "https://example.com/keyboard.jpg"),
        Product(3, "USB-C Hub", 49.99,
            "7-in-1 USB-C hub with HDMI, USB 3.0, SD card reader, and 100W power delivery.",
            "https://example.com/hub.jpg"),
        Product(4, "Laptop Stand", 39.99,
            "Adjustable aluminum laptop stand with ventilation and ergonomic design.",
            "https://example.com/stand.jpg"),
        Product(5, "Wireless Mouse", 29.99,
            "Ergonomic wireless mouse with 4000 DPI sensor and silent click buttons.",
            "https://example.com/mouse.jpg"),
        Product(6, "Webcam HD", 59.99,
            "1080p webcam with autofocus, built-in microphone, and privacy shutter.",
            "https://example.com/webcam.jpg")
    )

    fun getProducts(): List<Product> = products

    fun getProductById(id: Int): Product? = products.find { it.id == id }
}
