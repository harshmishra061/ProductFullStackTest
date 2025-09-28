package com.example.productsfullstacktest.repository
import com.example.productsfullstacktest.model.Product
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : CrudRepository<Product, Long>