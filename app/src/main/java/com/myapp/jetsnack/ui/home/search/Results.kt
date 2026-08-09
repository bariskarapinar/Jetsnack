package com.myapp.jetsnack.ui.home.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.myapp.jetsnack.model.Snack
import com.myapp.jetsnack.ui.components.JetsnackDivider
import com.myapp.jetsnack.ui.components.SnackImage
import com.myapp.jetsnack.ui.theme.JetsnackTheme
import com.myapp.jetsnack.ui.utils.formatPrice

@Composable
fun SearchResults(
    results: List<Snack>,
    onSnackClick: (Long, String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "${results.size} results",
            style = MaterialTheme.typography.titleLarge,
            color = JetsnackTheme.colors.brand,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
        )
        LazyColumn(
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(results) { snack ->
                SearchResultItem(snack, onSnackClick)
            }
        }
    }
}

@Composable
private fun SearchResultItem(
    snack: Snack,
    onSnackClick: (Long, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSnackClick(snack.id, "search") }
            .padding(horizontal = 24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            SnackImage(
                imageUrl = snack.imageUrl,
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = snack.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = JetsnackTheme.colors.textSecondary
                )
                Text(
                    text = snack.tagline,
                    style = MaterialTheme.typography.bodyLarge,
                    color = JetsnackTheme.colors.textHelp
                )
            }
            Text(
                text = formatPrice(snack.price),
                style = MaterialTheme.typography.titleMedium,
                color = JetsnackTheme.colors.textPrimary,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        JetsnackDivider()
    }
}

@Composable
fun NoResults(query: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No results for \"$query\"",
            style = MaterialTheme.typography.titleLarge,
            color = JetsnackTheme.colors.brand,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Try a different search term or check for typos.",
            style = MaterialTheme.typography.bodyLarge,
            color = JetsnackTheme.colors.textHelp
        )
    }
}
