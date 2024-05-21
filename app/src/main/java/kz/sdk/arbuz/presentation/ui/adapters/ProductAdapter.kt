package kz.sdk.arbuz.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kz.sdk.arbuz.databinding.ItemProductBinding
import kz.sdk.arbuz.domain.Product
import kz.sdk.arbuz.presentation.ui.base.BaseProductViewHolder



class ProductAdapter(
    private val clickListener: (Product, String) -> Unit
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bindView(product)
    }

    inner class ProductViewHolder(override val binding: ItemProductBinding) :
        BaseProductViewHolder<ItemProductBinding>(binding) {

        override fun bindView(item: Product) {
            binding.apply {
                title.text = item.name
                price.text = "${item.price} ₸/шт"
                amount.text = "${item.quantityInCart} шт"
                img.setImageResource(item.img)

                pricaChange.text = "${item.price} ₸/шт"
                if (item.quantityInCart > 0) {
                    oneItemCv.visibility = View.GONE
                    mtplItemCv.visibility = View.VISIBLE
                    amountChange.text = item.quantityInCart.toString()
                } else {
                    oneItemCv.visibility = View.VISIBLE
                    mtplItemCv.visibility = View.GONE
                }


                addBtn.setOnClickListener {
                    clickListener(item, "add")
                }

                removeBtn.setOnClickListener {
                    clickListener(item, "remove")
                }
                addBtn2.setOnClickListener {
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