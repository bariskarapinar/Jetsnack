package com.myapp.jetsnack.model

import androidx.compose.runtime.Immutable

@Immutable
data class Snack(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val price: Long,
    val tagline: String = "",
    val tags: Set<String> = emptySet(),
)

/**
 * Static data
 */

val snacks = listOf(
    Snack(
        id = 1L,
        name = "Cupcake",
        tagline = "A tag line",
        imageUrl = "https://picsum.photos/seed/cupcake/300/300",
        price = 299,
    ),
    Snack(
        id = 2L,
        name = "Donut",
        tagline = "A tag line",
        imageUrl = "https://picsum.photos/seed/donut/300/300",
        price = 299,
    ),
    Snack(
        id = 3L,
        name = "Eclair",
        tagline = "A tag line",
        imageUrl = "https://picsum.photos/seed/eclair/300/300",
        price = 299,
    ),
    Snack(
        id = 4L,
        name = "Froyo",
        tagline = "A tag line",
        imageUrl = "https://picsum.photos/seed/froyo/300/300",
        price = 299,
    ),
    Snack(
        id = 5L,
        name = "Gingerbread",
        tagline = "A tag line",
        imageUrl = "https://picsum.photos/seed/gingerbread/300/300",
        price = 499,
    ),
    Snack(
        id = 6L,
        name = "Honeycomb",
        tagline = "A tag line",
        imageUrl = "https://picsum.photos/seed/honeycomb/300/300",
        price = 299,
    ),
    Snack(
        id = 7L,
        name = "Ice Cream Sandwich",
        tagline = "A tag line",
        imageUrl = "https://picsum.photos/seed/icecream/300/300",
        price = 1299,
    ),
    Snack(
        id = 8L,
        name = "Jellybean",
        tagline = "A tag line",
        imageUrl = "https://picsum.photos/seed/jellybean/300/300",
        price = 299,
    ),
    Snack(
        id = 9L,
        name = "KitKat",
        tagline = "A tag line",
        imageUrl = "https://picsum.photos/seed/kitkat/300/300",
        price = 549,
    ),
    Snack(
        id = 10L,
        name = "Lollipop",
        tagline = "A tag line",
        imageUrl = "https://picsum.photos/seed/lollipop/300/300",
        price = 299,
    ),
    Snack(
        id = 11L,
        name = "Marshmallow",
        tagline = "A tag line",
        imageUrl = "https://picsum.photos/seed/marshmallow/300/300",
        price = 299,
    ),
    Snack(
        id = 12L,
        name = "Nougat",
        tagline = "A tag line",
        imageUrl = "https://picsum.photos/seed/nougat/300/300",
        price = 299,
    ),
    Snack(
        id = 13L,
        name = "Oreo",
        tagline = "A tag line",
        imageUrl = "https://picsum.photos/seed/oreo/300/300",
        price = 299,
    ),
    Snack(
        id = 14L,
        name = "Pie",
        tagline = "A tag line",
        imageUrl = "https://picsum.photos/seed/pie/300/300",
        price = 299,
    ),
    Snack(
        id = 15L,
        name = "Chips",
        imageUrl = "https://picsum.photos/seed/chips/300/300",
        price = 299,
    ),
    Snack(
        id = 16L,
        name = "Pretzels",
        imageUrl = "https://picsum.photos/seed/pretzels/300/300",
        price = 299,
    ),
    Snack(
        id = 17L,
        name = "Smoothies",
        imageUrl = "https://picsum.photos/seed/smoothies/300/300",
        price = 299,
    ),
    Snack(
        id = 18L,
        name = "Popcorn",
        imageUrl = "https://picsum.photos/seed/popcorn/300/300",
        price = 299,
    ),
    Snack(
        id = 19L,
        name = "Almonds",
        imageUrl = "https://picsum.photos/seed/almonds/300/300",
        price = 299,
    ),
    Snack(
        id = 20L,
        name = "Cheese",
        imageUrl = "https://picsum.photos/seed/cheese/300/300",
        price = 299,
    ),
    Snack(
        id = 21L,
        name = "Apples",
        tagline = "A tag line",
        imageUrl = "https://picsum.photos/seed/apples/300/300",
        price = 299,
    ),
    Snack(
        id = 22L,
        name = "Apple sauce",
        tagline = "A tag line",
        imageUrl = "https://picsum.photos/seed/applesauce/300/300",
        price = 299,
    ),
    Snack(
        id = 23L,
        name = "Apple chips",
        tagline = "A tag line",
        imageUrl = "https://picsum.photos/seed/applechips/300/300",
        price = 299,
    ),
    Snack(
        id = 24L,
        name = "Apple juice",
        tagline = "A tag line",
        imageUrl = "https://picsum.photos/seed/applejuice/300/300",
        price = 299,
    ),
    Snack(
        id = 25L,
        name = "Apple pie",
        tagline = "A tag line",
        imageUrl = "https://picsum.photos/seed/applepie/300/300",
        price = 299,
    ),
    Snack(
        id = 26L,
        name = "Grapes",
        tagline = "A tag line",
        imageUrl = "https://picsum.photos/seed/grapes/300/300",
        price = 299,
    ),
    Snack(
        id = 27L,
        name = "Kiwi",
        tagline = "A tag line",
        imageUrl = "https://picsum.photos/seed/kiwi/300/300",
        price = 299,
    ),
    Snack(
        id = 28L,
        name = "Mango",
        tagline = "A tag line",
        imageUrl = "https://picsum.photos/seed/mango/300/300",
        price = 299,
    ),
)
