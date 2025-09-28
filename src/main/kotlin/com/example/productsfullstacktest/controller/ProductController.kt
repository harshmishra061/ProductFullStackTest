package com.example.productsfullstacktest.controller
import com.example.productsfullstacktest.model.Product
import com.example.productsfullstacktest.repository.ProductRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Controller
@RequestMapping("/products")
class ProductController(private val productRepository: ProductRepository) {

    @GetMapping("/home")
    fun showProductsPage(): String {
        return "products"
    }

    @GetMapping
    fun getProductsFragment(model: Model): String {
        val products = productRepository.findAll()
        model.addAttribute("products", products)
        return "fragments/products-fragment"
    }

    @GetMapping("/search")
    fun searchProducts(@RequestParam(required = false) title: String?, model: Model): String {
        val products = if (title.isNullOrBlank()) {
            productRepository.findAll().toList()
        } else {
            productRepository.findAll().filter { it.title.contains(title, ignoreCase = true) }
        }
        model.addAttribute("products", products)
        return "fragments/products-fragment"
    }

    data class ProductRequest(val title: String, val vendor: String)

    @PostMapping("/add")
    @ResponseBody
    fun createProduct(@RequestBody request: ProductRequest): ResponseEntity<Product> {
        val newProduct = Product(
            title = request.title,
            vendor = request.vendor,
            publishedAt = OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        )
        val savedProduct = productRepository.save(newProduct)
        return ResponseEntity.ok(savedProduct)
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<String> {
        val productOptional = productRepository.findById(id)
        return if (productOptional.isPresent) {
            productRepository.deleteById(id)
            ResponseEntity.ok("Product with ID $id deleted successfully")
        } else {
            ResponseEntity.status(404).body("Product with ID $id not found")
        }
    }

    @GetMapping("/update/{id}")
    fun showUpdateProductPage(@PathVariable id: Long, model: Model): String {
        val product = productRepository.findById(id).orElseThrow {
            RuntimeException("Product with ID $id not found")
        }
        model.addAttribute("product", product)
        return "update-product" // Thymeleaf HTML page to render the update form
    }

    @PostMapping("/update/{id}")
    @ResponseBody
    fun updateProduct(
        @PathVariable id: Long,
        @RequestBody updatedRequest: ProductRequest
    ): ResponseEntity<Product> {
        val productOptional = productRepository.findById(id)
        return if (productOptional.isPresent) {
            var existingProduct = productOptional.get()
            existingProduct.title = updatedRequest.title
            existingProduct.vendor = updatedRequest.vendor
            val updatedProduct = productRepository.save(existingProduct)
            ResponseEntity.ok(updatedProduct)
        } else {
            ResponseEntity.status(404).build()
        }
    }


}
