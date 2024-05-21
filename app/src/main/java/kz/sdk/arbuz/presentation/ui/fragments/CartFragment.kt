package kz.sdk.arbuz.presentation.ui.fragments

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kz.sdk.arbuz.MyApp
import kz.sdk.arbuz.databinding.FragmentCartBinding
import kz.sdk.arbuz.domain.Product
import kz.sdk.arbuz.presentation.ui.adapters.CartAdapter
import kz.sdk.arbuz.presentation.ui.base.BaseFragment
import kz.sdk.arbuz.presentation.viewmodel.ProductViewModel
import kz.sdk.arbuz.presentation.viewmodel.ProductViewModelFactory


class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate) {

    private val productViewModel: ProductViewModel by viewModels {
        ProductViewModelFactory((requireActivity().application as MyApp).repository)
    }
    private lateinit var adapter: CartAdapter

    override fun onInit() {
        super.onInit()
        adapter = CartAdapter { product, action ->
            if (action == "add") {
                productViewModel.update(product.copy(quantityInCart = product.quantityInCart + 1))
            } else {
                productViewModel.update(product.copy(quantityInCart = product.quantityInCart - 1))
            }
        }
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.cartRecyclerView.adapter = adapter
    }

    override fun onBindView() {
        super.onBindView()
        productViewModel.cartProducts.observe(viewLifecycleOwner) { products ->
            products?.let { adapter.submitList(it) }
            updateTotalAmount(products)
        }
    }

    private fun updateTotalAmount(products: List<Product>) {
        val total = products.sumByDouble { it.price * it.quantityInCart }
        binding.totalAmount.text = "Общая сумма: $total ₸"
    }
}