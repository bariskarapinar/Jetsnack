package com.myapp.jetsnack.model

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf

@Stable
class Filter(
    val name: String,
    enabled: Boolean = false,
    val icon: Int? = null
) {
    val enabled = mutableStateOf(enabled)
}

val filters = listOf(
    Filter(name = "Organic"),
    Filter(name = "Gluten-free"),
    Filter(name = "Dairy-free"),
    Filter(name = "Sweet"),
    Filter(name = "Savory"),
)

val priceFilters = listOf(
    Filter(name = "$"),
    Filter(name = "$$"),
    Filter(name = "$$$"),
    Filter(name = "$$$$"),
)

val sortFilters = listOf(
    Filter(name = "Android's favorite (default)"),
    Filter(name = "Rating"),
    Filter(name = "Alphabetical"),
)

val categoryFilters = listOf(
    Filter(name = "Chips & crackers"),
    Filter(name = "Fruit snacks"),
    Filter(name = "Desserts"),
    Filter(name = "Nuts"),
)

val lifeStyleFilters = listOf(
    Filter(name = "Organic"),
    Filter(name = "Gluten-free"),
    Filter(name = "Dairy-free"),
    Filter(name = "Sweet"),
    Filter(name = "Savory"),
)

var sortDefault = sortFilters.get(0).name
