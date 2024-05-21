package kz.sdk.arbuz.presentation.ui.fragments

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import kz.sdk.arbuz.MyApp
import kz.sdk.arbuz.presentation.ui.base.BaseFragment
import kz.sdk.arbuz.databinding.FragmentHomeBinding
import kz.sdk.arbuz.presentation.ui.adapters.ProductAdapter
import kz.sdk.arbuz.presentation.viewmodel.ProductViewModel


import kz.sdk.arbuz.presentation.viewmodel.ProductViewModelFactory

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val productViewModel: ProductViewModel by viewModels {
        ProductViewModelFactory((requireActivity().application as MyApp).repository)
    }
    private lateinit var adapter: ProductAdapter

    override fun onInit() {
        super.onInit()
        adapter = ProductAdapter { product, action ->
            val newQuantity = if (action == "add") product.quantityInCart + 1 else product.quantityInCart - 1
            productViewModel.update(product.copy(quantityInCart = newQuantity))
        }
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerView.adapter = adapter
    }

    override fun onBindView() {
        super.onBindView()
        productViewModel.allProducts.observe(viewLifecycleOwner) { products ->
            products?.let { adapter.submitList(it) }
        }
    }
}