package kz.sdk.arbuz.domain

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    @DrawableRes val img: Int,
    val price: Double,
    val description: String,
    val quantityInCart: Int = 0
)