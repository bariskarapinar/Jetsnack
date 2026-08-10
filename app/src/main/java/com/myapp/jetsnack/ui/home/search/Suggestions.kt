package com.myapp.jetsnack.ui.home.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.myapp.jetsnack.model.SearchSuggestionGroup
import com.myapp.jetsnack.ui.theme.JetsnackTheme

@Composable
fun SearchSuggestions(
    suggestions: List<SearchSuggestionGroup>,
    onSuggestionSelect: (String) -> Unit
) {
    LazyColumn {
        items(suggestions) { group ->
            SuggestionGroupItem(group, onSuggestionSelect)
        }
    }
}

@Composable
private fun SuggestionGroupItem(
    group: SearchSuggestionGroup,
    onSuggestionSelect: (String) -> Unit
) {
    Column {
        Text(
            text = group.name,
            style = MaterialTheme.typography.titleLarge,
            color = JetsnackTheme.colors.brand,
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 14.dp)
        )
        group.suggestions.forEach { suggestion ->
            SuggestionItemContent(suggestion, onSuggestionSelect)
        }
    }
}

@Composable
private fun SuggestionItemContent(
    suggestion: String,
    onSuggestionSelect: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .clickable { onSuggestionSelect(suggestion) }
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = suggestion,
            style = MaterialTheme.typography.bodyLarge,
            color = JetsnackTheme.colors.textSecondary
        )
    }
}
