package kz.sdk.arbuz.presentation.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kz.sdk.arbuz.data.ProductRepository
import kz.sdk.arbuz.domain.Product



class ProductViewModel(private val repository: ProductRepository) : ViewModel() {
    val allProducts: LiveData<List<Product>> = repository.allProducts
    val cartProducts: LiveData<List<Product>> = repository.cartProducts

    fun insert(product: Product) = viewModelScope.launch {
        repository.insert(product)
    }

    fun insertAll(products: List<Product>) = viewModelScope.launch {
        repository.insertAll(products)
    }

    fun update(product: Product) = viewModelScope.launch {
        repository.update(product)
    }
}
