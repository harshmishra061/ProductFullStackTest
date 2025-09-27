package com.example.productsfullstacktest.controller

import com.example.productsfullstacktest.repository.ProductRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ProductPageController(private val productRepository: ProductRepository) {
    @GetMapping("/products-page")
    fun showProductsPage(model: Model): String {
        val products = productRepository.findAll()
        model.addAttribute("products", products)
        // point to the fragment inside products.html
        model.addAttribute("content", "products :: productsContent")
        return "products" // layout.html is the main template
    }

}
