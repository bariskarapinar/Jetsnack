@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.myapp.jetsnack.ui.components

import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.myapp.jetsnack.model.CollectionType
import com.myapp.jetsnack.model.Snack
import com.myapp.jetsnack.model.SnackCollection
import com.myapp.jetsnack.ui.LocalNavAnimatedVisibilityScope
import com.myapp.jetsnack.ui.LocalSharedTransitionScope
import com.myapp.jetsnack.ui.SnackSharedElementKey
import com.myapp.jetsnack.ui.SnackSharedElementType
import com.myapp.jetsnack.ui.snackdetail.nonSpatialExpressiveSpring
import com.myapp.jetsnack.ui.snackdetail.snackDetailBoundsTransform
import com.myapp.jetsnack.ui.theme.JetsnackTheme

private val HighlightCardWidth = 170.dp
private val HighlightCardPadding = 16.dp

private val Density.cardWidthWithPaddingPx
    get() = (HighlightCardWidth + HighlightCardPadding).toPx()

@Composable
fun SnackCollection(
    snackCollection: SnackCollection,
    onSnackClick: (Long, String) -> Unit,
    modifier: Modifier = Modifier,
    index: Int = 0,
    highlight: Boolean = true,
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .heightIn(min = 56.dp)
                .padding(start = 24.dp),
        ) {
            Text(
                text = snackCollection.name,
                style = MaterialTheme.typography.titleLarge,
                color = JetsnackTheme.colors.brand,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start),
            )
            IconButton(
                onClick = { /* todo */ },
                modifier = Modifier.align(Alignment.CenterVertically),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = JetsnackTheme.colors.brand,
                    contentDescription = null,
                )
            }
        }
        if (highlight && snackCollection.type == CollectionType.Highlight) {
            HighlightedSnacks(snackCollection.id, index, snackCollection.snacks, onSnackClick)
        } else {
            Snacks(snackCollection.id, snackCollection.snacks, onSnackClick)
        }
    }
}

@Composable
private fun HighlightedSnacks(
    snackCollectionId: Long,
    index: Int,
    snacks: List<Snack>,
    onSnackClick: (Long, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val rowState = rememberLazyListState()
    val cardWidthWithPaddingPx = with(LocalDensity.current) { cardWidthWithPaddingPx }

    val scrollProvider = {
        val offsetFromStart = cardWidthWithPaddingPx * rowState.firstVisibleItemIndex
        offsetFromStart + rowState.firstVisibleItemScrollOffset
    }

    val gradient = when ((index / 2) % 2) {
        0 -> JetsnackTheme.colors.gradient6_1
        else -> JetsnackTheme.colors.gradient6_2
    }

    LazyRow(
        state = rowState,
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(start = 24.dp, end = 24.dp),
    ) {
        itemsIndexed(snacks) { itemIndex, snack ->
            HighlightSnackItem(
                snackCollectionId = snackCollectionId,
                snack = snack,
                onSnackClick = onSnackClick,
                index = itemIndex,
                gradient = gradient,
                scrollProvider = scrollProvider,
            )
        }
    }
}

@Composable
private fun Snacks(
    snackCollectionId: Long,
    snacks: List<Snack>,
    onSnackClick: (Long, String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(start = 12.dp, end = 12.dp),
    ) {
        items(snacks) { snack ->
            SnackItem(snack, snackCollectionId, onSnackClick)
        }
    }
}

@Composable
fun SnackItem(
    snack: Snack,
    snackCollectionId: Long,
    onSnackClick: (Long, String) -> Unit,
    modifier: Modifier = Modifier
) {
    JetsnackSurface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.padding(
            start = 4.dp,
            end = 4.dp,
            bottom = 8.dp,
        ),
    ) {
        val sharedTransitionScope = LocalSharedTransitionScope.current
            ?: throw IllegalStateException("No sharedTransitionScope found")
        val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current
            ?: throw IllegalStateException("No animatedVisibilityScope found")

        with(sharedTransitionScope) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable(onClick = { onSnackClick(snack.id, snackCollectionId.toString()) })
                    .padding(8.dp),
            ) {
                SnackImage(
                    imageUrl = snack.imageUrl,
                    elevation = 1.dp,
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .sharedBounds(
                            rememberSharedContentState(
                                key = SnackSharedElementKey(
                                    snackId = snack.id,
                                    origin = snackCollectionId.toString(),
                                    type = SnackSharedElementType.Image,
                                ),
                            ),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = snackDetailBoundsTransform,
                        ),
                )
                Text(
                    text = snack.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = JetsnackTheme.colors.textSecondary,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .wrapContentWidth()
                        .sharedBounds(
                            rememberSharedContentState(
                                key = SnackSharedElementKey(
                                    snackId = snack.id,
                                    origin = snackCollectionId.toString(),
                                    type = SnackSharedElementType.Title,
                                ),
                            ),
                            animatedVisibilityScope = animatedVisibilityScope,
                            enter = fadeIn(nonSpatialExpressiveSpring()),
                            exit = fadeOut(nonSpatialExpressiveSpring()),
                            resizeMode = SharedTransitionScope.ResizeMode.scaleToBounds(),
                            boundsTransform = snackDetailBoundsTransform,
                        ),
                )
            }
        }
    }
}

@Composable
private fun HighlightSnackItem(
    snackCollectionId: Long,
    snack: Snack,
    onSnackClick: (Long, String) -> Unit,
    index: Int,
    gradient: List<Color>,
    scrollProvider: () -> Float,
    modifier: Modifier = Modifier,
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
        ?: throw IllegalStateException("No Scope found")
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current
        ?: throw IllegalStateException("No Scope found")

    with(sharedTransitionScope) {
        val roundedCornerAnimation by animatedVisibilityScope.transition
            .animateDp(label = "rounded corner") { enterExit: EnterExitState ->
                when (enterExit) {
                    EnterExitState.PreEnter -> 0.dp
                    EnterExitState.Visible -> 20.dp
                    EnterExitState.PostExit -> 20.dp
                }
            }

        JetsnackCard(
            elevation = 0.dp,
            shape = RoundedCornerShape(roundedCornerAnimation),
            modifier = modifier
                .padding(bottom = 16.dp)
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(
                        key = SnackSharedElementKey(
                            snackId = snack.id,
                            origin = snackCollectionId.toString(),
                            type = SnackSharedElementType.Bounds,
                        ),
                    ),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = snackDetailBoundsTransform,
                    clipInOverlayDuringTransition = OverlayClip(
                        RoundedCornerShape(
                            roundedCornerAnimation,
                        ),
                    ),
                    enter = fadeIn(),
                    exit = fadeOut(),
                )
                .size(
                    width = HighlightCardWidth,
                    height = 250.dp,
                )
                .border(
                    1.dp,
                    JetsnackTheme.colors.uiBorder.copy(alpha = 0.12f),
                    RoundedCornerShape(roundedCornerAnimation),
                ),
        ) {
            Column(
                modifier = Modifier
                    .clickable(onClick = {
                        onSnackClick(
                            snack.id,
                            snackCollectionId.toString(),
                        )
                    })
                    .fillMaxSize(),
            ) {
                Box(
                    modifier = Modifier
                        .height(160.dp)
                        .fillMaxWidth(),
                ) {
                    Box(
                        modifier = Modifier
                            .sharedBounds(
                                rememberSharedContentState(
                                    key = SnackSharedElementKey(
                                        snackId = snack.id,
                                        origin = snackCollectionId.toString(),
                                        type = SnackSharedElementType.Background,
                                    ),
                                ),
                                animatedVisibilityScope = animatedVisibilityScope,
                                boundsTransform = snackDetailBoundsTransform,
                                enter = fadeIn(nonSpatialExpressiveSpring()),
                                exit = fadeOut(nonSpatialExpressiveSpring()),
                                resizeMode = SharedTransitionScope.ResizeMode.scaleToBounds(),
                            )
                            .height(100.dp)
                            .fillMaxWidth()
                            .offsetGradientBackground(
                                colors = gradient,
                                width = {
                                    6 * cardWidthWithPaddingPx
                                },
                                offset = {
                                    val left = index * cardWidthWithPaddingPx
                                    val gradientOffset = left - (scrollProvider() / 3f)
                                    gradientOffset
                                },
                            ),
                    )
                    SnackImage(
                        imageUrl = snack.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .sharedBounds(
                                rememberSharedContentState(
                                    key = SnackSharedElementKey(
                                        snackId = snack.id,
                                        origin = snackCollectionId.toString(),
                                        type = SnackSharedElementType.Image,
                                    ),
                                ),
                                animatedVisibilityScope = animatedVisibilityScope,
                                exit = fadeOut(nonSpatialExpressiveSpring()),
                                enter = fadeIn(nonSpatialExpressiveSpring()),
                                boundsTransform = snackDetailBoundsTransform,
                            )
                            .align(Alignment.BottomCenter)
                            .size(120.dp),
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = snack.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleLarge,
                    color = JetsnackTheme.colors.textSecondary,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .sharedBounds(
                            rememberSharedContentState(
                                key = SnackSharedElementKey(
                                    snackId = snack.id,
                                    origin = snackCollectionId.toString(),
                                    type = SnackSharedElementType.Title,
                                ),
                            ),
                            animatedVisibilityScope = animatedVisibilityScope,
                            enter = fadeIn(nonSpatialExpressiveSpring()),
                            exit = fadeOut(nonSpatialExpressiveSpring()),
                            boundsTransform = snackDetailBoundsTransform,
                            resizeMode = SharedTransitionScope.ResizeMode.scaleToBounds(),
                        )
                        .wrapContentWidth(),
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = snack.tagline,
                    style = MaterialTheme.typography.bodyLarge,
                    color = JetsnackTheme.colors.textHelp,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .sharedBounds(
                            rememberSharedContentState(
                                key = SnackSharedElementKey(
                                    snackId = snack.id,
                                    origin = snackCollectionId.toString(),
                                    type = SnackSharedElementType.Tagline,
                                ),
                            ),
                            animatedVisibilityScope = animatedVisibilityScope,
                            enter = fadeIn(nonSpatialExpressiveSpring()),
                            exit = fadeOut(nonSpatialExpressiveSpring()),
                            boundsTransform = snackDetailBoundsTransform,
                            resizeMode = SharedTransitionScope.ResizeMode.scaleToBounds(),
                        )
                        .wrapContentWidth(),
                )
            }
        }
    }
}

@Composable
fun SnackImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp,
) {
    JetsnackSurface(
        elevation = elevation,
        shape = CircleShape,
        modifier = modifier,
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = contentDescription,
            loading = {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                }
            },
            error = {
                Icon(
                    imageVector = Icons.Default.Fastfood,
                    contentDescription = null,
                    tint = JetsnackTheme.colors.brand.copy(alpha = 0.5f),
                    modifier = Modifier.padding(16.dp)
                )
            },
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
    }
}
