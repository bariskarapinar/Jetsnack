package com.myapp.jetsnack.model

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * A fake repo for searching.
 */
object SearchRepo {
    fun getCategories(): List<SearchCategoryCollection> = searchCategoryCollections
    fun getSuggestions(): List<SearchSuggestionGroup> = searchSuggestions
    suspend fun search(query: String): List<Snack> = withContext(Dispatchers.Default) {
        delay(200L) // simulate an I/O delay
        snacks.filter { it.name.contains(query, ignoreCase = true) }
    }
}

@Immutable
data class SearchCategoryCollection(val id: Long, val name: String, val categories: List<SearchCategory>)

@Immutable
data class SearchCategory(val name: String, val imageUrl: String)

@Immutable
data class SearchSuggestionGroup(val id: Long, val name: String, val suggestions: List<String>)

/**
 * Static data
 */

private val searchCategoryCollections = listOf(
    SearchCategoryCollection(
        id = 0L,
        name = "Categories",
        categories = listOf(
            SearchCategory(
                name = "Chips & crackers",
                imageUrl = "https://source.unsplash.com/300x300/?chips",
            ),
            SearchCategory(
                name = "Fruit snacks",
                imageUrl = "https://source.unsplash.com/300x300/?fruit",
            ),
            SearchCategory(
                name = "Desserts",
                imageUrl = "https://source.unsplash.com/300x300/?dessert",
            ),
            SearchCategory(
                name = "Nuts",
                imageUrl = "https://source.unsplash.com/300x300/?nuts",
            ),
        ),
    ),
    SearchCategoryCollection(
        id = 1L,
        name = "Lifestyles",
        categories = listOf(
            SearchCategory(
                name = "Organic",
                imageUrl = "https://source.unsplash.com/300x300/?organic",
            ),
            SearchCategory(
                name = "Gluten Free",
                imageUrl = "https://source.unsplash.com/300x300/?glutenfree",
            ),
            SearchCategory(
                name = "Paleo",
                imageUrl = "https://source.unsplash.com/300x300/?paleo",
            ),
            SearchCategory(
                name = "Vegan",
                imageUrl = "https://source.unsplash.com/300x300/?vegan",
            ),
            SearchCategory(
                name = "Vegetarian",
                imageUrl = "https://source.unsplash.com/300x300/?vegetarian",
            ),
            SearchCategory(
                name = "Whole30",
                imageUrl = "https://source.unsplash.com/300x300/?whole30",
            ),
        ),
    ),
)

private val searchSuggestions = listOf(
    SearchSuggestionGroup(
        id = 0L,
        name = "Recent searches",
        suggestions = listOf(
            "Cheese",
            "Apple Sauce",
        ),
    ),
    SearchSuggestionGroup(
        id = 1L,
        name = "Popular searches",
        suggestions = listOf(
            "Organic",
            "Gluten Free",
            "Paleo",
            "Vegan",
            "Vegitarian",
            "Whole30",
        ),
    ),
)
