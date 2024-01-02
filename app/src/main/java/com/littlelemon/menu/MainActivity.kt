package com.littlelemon.menu

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.MenuCompat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update


class MainActivity : ComponentActivity() {

    private val productsState: MutableStateFlow<Products> =
        MutableStateFlow(Products(ProductsWarehouse.getProductsList()))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { InitUI() }
    }

    @Composable
    fun InitUI() {
        val products by productsState.collectAsState()
        ProductsGrid(products = products, startProductActivity = this::startProductActivity)
    }

    private fun startProductActivity(productItem: ProductItem) {

        // Create an Intent to start the ProductActivity
        val intent = Intent(baseContext, ProductActivity::class.java)

        // Pass product details as extra properties to the intent
        intent.putExtra(ProductActivity.KEY_TITLE, productItem.title)
        intent.putExtra(ProductActivity.KEY_PRICE, productItem.price)
        intent.putExtra(ProductActivity.KEY_IMAGE, productItem.image)
        intent.putExtra(ProductActivity.KEY_CATEGORY, productItem.category)

        // Start the ProductActivity with the created intent
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.products_menu, menu)
        MenuCompat.setGroupDividerEnabled(menu, true)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.groupId == R.id.sorting) {
            val type = when (item.itemId) {
                R.id.sort_a_z -> SortType.Alphabetically
                R.id.sort_price_asc -> SortType.PriceAsc
                R.id.sort_price_desc -> SortType.PriceDesc
                else -> SortType.Alphabetically
            }
            productsState.update {
                Products(
                    SortHelper().sortProducts(
                        type,
                        ProductsWarehouse.getProductsList()
                    )
                )
            }
        } else if (item.groupId == R.id.filter) {
            val type = when (item.itemId) {
                R.id.filter_all -> FilterType.All
                R.id.filter_drinks -> FilterType.Drinks
                R.id.filter_food -> FilterType.Food
                R.id.filter_dessert -> FilterType.Dessert
                else -> FilterType.All
            }
            productsState.update {
                Products(
                    FilterHelper().filterProducts(
                        type,
                        ProductsWarehouse.getProductsList()
                    )
                )
            }
        }
        return true
    }
}