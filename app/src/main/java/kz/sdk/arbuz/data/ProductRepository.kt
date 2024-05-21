package kz.sdk.arbuz.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import kz.sdk.arbuz.domain.Product


class ProductRepository(private val productDao: ProductDao) {
    val allProducts: LiveData<List<Product>> = productDao.getAllProducts()

    val cartProducts: LiveData<List<Product>> = productDao.getCartProducts()

    suspend fun insert(product: Product) {
        productDao.insert(product)
    }

    suspend fun insertAll(products: List<Product>) {
        productDao.insertAll(products)
    }

    suspend fun update(product: Product) {
        productDao.update(product)
    }
}