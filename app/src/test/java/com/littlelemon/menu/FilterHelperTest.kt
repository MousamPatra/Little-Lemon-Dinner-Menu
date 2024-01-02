package com.littlelemon.menu

import org.junit.Assert.*
import org.junit.Test

class FilterHelperTest{

    // Create a sample list of products for test
    private val sampleProductsList = mutableListOf(
        ProductItem(title = "Black tea", price = 3.00, category = "Drinks", R.drawable.black_tea),
        ProductItem(title = "Croissant", price = 7.00, category = "Dessert", R.drawable.croissant),
        ProductItem(title = "Bouillabaisse", price = 20.00, category = "Food", R.drawable.bouillabaisse)
    )
    @Test
    fun filterProducts_filterTypeDessert_croissantReturned(){

        val filteredList = FilterHelper().filterProducts(FilterType.Dessert, sampleProductsList)


        assertEquals(1, filteredList.size) // Expecting only one item in the filtered list
        assertEquals("Croissant", filteredList[0].title) // Expecting the filtered item to be Croissant
        assertEquals(7.00, filteredList[0].price, 0.001) // Expecting the price of the filtered item to be 7.00
        assertEquals("Dessert", filteredList[0].category) // Expecting the category of the filtered item to be Dessert

    }
}