package kz.sdk.arbuz.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kz.sdk.arbuz.databinding.ItemCartBinding
import kz.sdk.arbuz.domain.Product
import kz.sdk.arbuz.presentation.ui.base.BaseProductViewHolder

class CartAdapter(
    private val clickListener: (Product, String) -> Unit
) : ListAdapter<Product, CartAdapter.CartViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = getItem(position)
        holder.bindView(product)
    }

    inner class CartViewHolder(override val binding: ItemCartBinding) :
        BaseProductViewHolder<ItemCartBinding>(binding) {

        override fun bindView(item: Product) {
            binding.apply {
                textView.text = item.name
                price.text = "${item.price} ₸/шт"
                amountChange.text = item.quantityInCart.toString()
                img.setImageResource(item.img)
                priceChange.text = "${item.price * item.quantityInCart} ₸"

                removeBtn.setOnClickListener {
                    clickListener(item, "remove")
                }

                addBtn.setOnClickListener {
                    clickListener(item, "add")
                }
            }
        }
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}