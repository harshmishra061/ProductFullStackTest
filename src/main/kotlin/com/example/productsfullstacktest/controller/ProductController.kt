package com.example.productsfullstacktest.controller

import com.example.productsfullstacktest.model.Product
import com.example.productsfullstacktest.repository.ProductRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController(private val productRepository: ProductRepository) {

    // GET all products
    @GetMapping
    fun getAllProducts(): ResponseEntity<Iterable<Product?>>? {
        val products = productRepository.findAll()
        return ResponseEntity.ok(products)
    }

    // GET product by ID
    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long): ResponseEntity<Product> {
        val product = productRepository.findById(id)
        return if (product.isPresent) {
            ResponseEntity.ok(product.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    // Optional: search products by title
    @GetMapping("/search")
    fun searchProducts(@RequestParam title: String): ResponseEntity<List<Product>> {
        val products = productRepository.findAll().filter { it.title.contains(title, ignoreCase = true) }
        return ResponseEntity.ok(products)
    }
}