package com.example.productsfullstacktest.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import com.fasterxml.jackson.databind.JsonNode

@Table("products")
data class Product(
    @Id val id: Long? = null,
    val title: String,
    val description: String?,
    val price: Double?,
    val imageUrl: String?
)