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
        tagline = "A delicious frosted treat",
        imageUrl = "https://images.unsplash.com/photo-1576618148400-f54bed99fcfd?auto=format&fit=crop&w=400&q=80",
        price = 299,
    ),
    Snack(
        id = 2L,
        name = "Donut",
        tagline = "Sweet glazed goodness",
        imageUrl = "https://images.unsplash.com/photo-1551024601-bec78aea704b?auto=format&fit=crop&w=400&q=80",
        price = 299,
    ),
    Snack(
        id = 3L,
        name = "Eclair",
        tagline = "Cream-filled pastry delight",
        imageUrl = "https://images.unsplash.com/photo-1612203985729-7072695438d3?auto=format&fit=crop&w=400&q=80",
        price = 299,
    ),
    Snack(
        id = 4L,
        name = "Froyo",
        tagline = "Cool and refreshing yogurt",
        imageUrl = "https://images.unsplash.com/photo-1563805042-7684c019e1cb?auto=format&fit=crop&w=400&q=80",
        price = 299,
    ),
    Snack(
        id = 5L,
        name = "Gingerbread",
        tagline = "Classic spicy ginger cookie",
        imageUrl = "https://images.unsplash.com/photo-1511081692775-0574936a5971?auto=format&fit=crop&w=400&q=80",
        price = 499,
    ),
    Snack(
        id = 6L,
        name = "Honeycomb",
        tagline = "Sweet and crunchy honey",
        imageUrl = "https://images.unsplash.com/photo-1587049352846-4a222e784d38?auto=format&fit=crop&w=400&q=80",
        price = 299,
    ),
    Snack(
        id = 7L,
        name = "Ice Cream Sandwich",
        tagline = "Cookie meets cold cream",
        imageUrl = "https://images.unsplash.com/photo-1528577930419-35713233cea4?auto=format&fit=crop&w=400&q=80",
        price = 1299,
    ),
    Snack(
        id = 8L,
        name = "Jellybean",
        tagline = "Colorful chewy bursts",
        imageUrl = "https://images.unsplash.com/photo-1534073828943-f801091bb18c?auto=format&fit=crop&w=400&q=80",
        price = 299,
    ),
    Snack(
        id = 9L,
        name = "KitKat",
        tagline = "Have a break with chocolate",
        imageUrl = "https://images.unsplash.com/photo-1621939514649-280e2ee25f60?auto=format&fit=crop&w=400&q=80",
        price = 549,
    ),
    Snack(
        id = 10L,
        name = "Lollipop",
        tagline = "Sweet candy on a stick",
        imageUrl = "https://images.unsplash.com/photo-1534073737927-85f1ebff1f5d?auto=format&fit=crop&w=400&q=80",
        price = 299,
    ),
    Snack(
        id = 11L,
        name = "Marshmallow",
        tagline = "Soft and fluffy clouds",
        imageUrl = "https://images.unsplash.com/photo-1527324688151-0e627063f2b1?auto=format&fit=crop&w=400&q=80",
        price = 299,
    ),
    Snack(
        id = 12L,
        name = "Nougat",
        tagline = "Nutty and chewy treat",
        imageUrl = "https://images.unsplash.com/photo-1601000201016-57766060c498?auto=format&fit=crop&w=400&q=80",
        price = 299,
    ),
    Snack(
        id = 13L,
        name = "Oreo",
        tagline = "Double stuffed chocolate",
        imageUrl = "https://images.unsplash.com/photo-1558961363-fa8fdf82db35?auto=format&fit=crop&w=400&q=80",
        price = 299,
    ),
    Snack(
        id = 14L,
        name = "Pie",
        tagline = "Warm fruit-filled crust",
        imageUrl = "https://images.unsplash.com/photo-1601000919720-990793138b00?auto=format&fit=crop&w=400&q=80",
        price = 299,
    ),
    Snack(
        id = 15L,
        name = "Chips",
        tagline = "Crispy salty snacks",
        imageUrl = "https://images.unsplash.com/photo-1566478989037-eec170784d0b?auto=format&fit=crop&w=400&q=80",
        price = 299,
    ),
    Snack(
        id = 16L,
        name = "Pretzels",
        tagline = "Twisted salty crunch",
        imageUrl = "https://images.unsplash.com/photo-1541544537156-7627a7a4aa1c?auto=format&fit=crop&w=400&q=80",
        price = 299,
    ),
    Snack(
        id = 17L,
        name = "Smoothies",
        tagline = "Fruit blend in a glass",
        imageUrl = "https://images.unsplash.com/photo-1505252585461-04db1eb84625?auto=format&fit=crop&w=400&q=80",
        price = 299,
    ),
    Snack(
        id = 18L,
        name = "Popcorn",
        tagline = "Buttery cinema snack",
        imageUrl = "https://images.unsplash.com/photo-1512149177596-f817c7ef5d4c?auto=format&fit=crop&w=400&q=80",
        price = 299,
    ),
    Snack(
        id = 19L,
        name = "Almonds",
        tagline = "Roasted crunchy nuts",
        imageUrl = "https://images.unsplash.com/photo-1508061253366-f7da158b6d46?auto=format&fit=crop&w=400&q=80",
        price = 299,
    ),
    Snack(
        id = 20L,
        name = "Cheese",
        tagline = "Savory artisan block",
        imageUrl = "https://images.unsplash.com/photo-1486297678162-ad2490054d58?auto=format&fit=crop&w=400&q=80",
        price = 299,
    ),
    Snack(
        id = 21L,
        name = "Apples",
        tagline = "Fresh crisp orchard fruit",
        imageUrl = "https://images.unsplash.com/photo-1560806887-1e4cd0b6bcd6?auto=format&fit=crop&w=400&q=80",
        price = 299,
    ),
)
