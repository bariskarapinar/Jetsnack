@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.myapp.jetsnack.ui.home

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.myapp.jetsnack.ui.LocalNavAnimatedVisibilityScope
import com.myapp.jetsnack.ui.LocalSharedTransitionScope
import com.myapp.jetsnack.ui.components.JetsnackDivider
import com.myapp.jetsnack.ui.snackdetail.spatialExpressiveSpring
import com.myapp.jetsnack.ui.theme.AlphaNearOpaque
import com.myapp.jetsnack.ui.theme.JetsnackTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DestinationBar(modifier: Modifier = Modifier) {
    val sharedElementScope = LocalSharedTransitionScope.current ?: throw IllegalStateException("No shared element scope")
    val navAnimatedScope = LocalNavAnimatedVisibilityScope.current ?: throw IllegalStateException("No nav scope")
    with(sharedElementScope) {
        with(navAnimatedScope) {
            Column(
                modifier = modifier
                    .renderInSharedTransitionScopeOverlay()
                    .animateEnterExit(
                        enter = slideInVertically(spatialExpressiveSpring()) { -it * 2 },
                        exit = slideOutVertically(spatialExpressiveSpring()) { -it * 2 },
                    ),
            ) {
                TopAppBar(
                    windowInsets = WindowInsets(0, 0, 0, 0),
                    title = {
                        Row {
                            Text(
                                text = "Delivery to 1600 Amphitheater Way",
                                style = MaterialTheme.typography.titleMedium,
                                color = JetsnackTheme.colors.textSecondary,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .weight(1f)
                                    .align(Alignment.CenterVertically),
                            )
                            IconButton(
                                onClick = { /* todo */ },
                                modifier = Modifier.align(Alignment.CenterVertically),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ExpandMore,
                                    tint = JetsnackTheme.colors.brand,
                                    contentDescription = "Select Delivery",
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors().copy(
                        containerColor = JetsnackTheme.colors.uiBackground
                            .copy(alpha = AlphaNearOpaque),
                        titleContentColor = JetsnackTheme.colors.textSecondary,
                    ),
                )
                JetsnackDivider()
            }
        }
    }
}
