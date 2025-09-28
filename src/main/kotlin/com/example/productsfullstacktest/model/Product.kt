package com.example.productsfullstacktest.model
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("products")
data class Product(
    @Id  val id: Long? = null,
    var title: String,
    val publishedAt: String,
    var vendor: String,
)