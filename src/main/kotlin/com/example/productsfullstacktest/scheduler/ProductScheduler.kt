package com.example.productsfullstacktest.scheduler
import com.example.productsfullstacktest.model.Product
import com.example.productsfullstacktest.repository.ProductRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

@Component
class ProductScheduler(private val productRepository: ProductRepository) {

    private val restTemplate = RestTemplate()
    private val mapper = jacksonObjectMapper()

    @Scheduled(initialDelay = 0, fixedRate = 60 * 60 * 1000)  // runs every hour
    fun fetchProducts() {
        val url = "https://famme.no/products.json"
        val response: String = restTemplate.getForObject(url, String::class.java) ?: return

        val json = mapper.readTree(response)
        val productsArray = json["products"]?.take(50) ?: return
        productRepository.deleteAll()
        productsArray.forEach { p ->
            val product = Product(
                title = p["title"].asText(),
                publishedAt = p["published_at"].asText(),
                vendor = p["vendor"].asText()
            )
            productRepository.save(product)
        }
    }
}
