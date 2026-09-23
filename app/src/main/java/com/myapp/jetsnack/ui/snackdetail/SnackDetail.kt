@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.myapp.jetsnack.ui.snackdetail

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.myapp.jetsnack.model.CartRepo
import com.myapp.jetsnack.model.Snack
import com.myapp.jetsnack.model.SnackRepo
import com.myapp.jetsnack.ui.LocalNavAnimatedVisibilityScope
import com.myapp.jetsnack.ui.LocalSharedTransitionScope
import com.myapp.jetsnack.ui.SnackSharedElementKey
import com.myapp.jetsnack.ui.SnackSharedElementType
import com.myapp.jetsnack.ui.components.JetsnackButton
import com.myapp.jetsnack.ui.components.JetsnackSurface
import com.myapp.jetsnack.ui.components.QuantitySelector
import com.myapp.jetsnack.ui.components.SnackCollection
import com.myapp.jetsnack.ui.components.SnackImage
import com.myapp.jetsnack.ui.theme.JetsnackTheme
import com.myapp.jetsnack.ui.utils.formatPrice
import kotlin.math.roundToInt

private val BottomBarHeight = 72.dp
private val TitleHeight = 128.dp
private val GradientScroll = 180.dp
private val ImageOverlap = 115.dp
private val MinTitleOffset = 56.dp
private val MinImageOffset = 12.dp
private val MaxImageSize = 240.dp
private val MinImageSize = 40.dp
private val HzPadding = 24.dp

@Composable
fun SnackDetail(
    snackId: Long,
    origin: String,
    upPress: () -> Unit
) {
    val snack = remember(snackId) { SnackRepo.getSnack(snackId) }
    val related = remember(snackId) { SnackRepo.getRelated(snackId) }

    Box(Modifier.fillMaxSize()) {
        val scroll = rememberScrollState(0)
        Header()
        Body(related, scroll, onSnackClick = { id, origin -> /* This would recursively navigate, but for now we can just stay or navigate to new id if the NavHost allows */ })
        Title(snack, origin, scroll.value)
        Image(snack.imageUrl, origin, snack.id, scroll.value)
        Up(upPress)
        CartBottomBar(
            snack = snack,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun Header() {
    Spacer(
        modifier = Modifier
            .height(280.dp)
            .fillMaxWidth()
            .background(Brush.horizontalGradient(JetsnackTheme.colors.tornado1)),
    )
}

@Composable
private fun Up(upPress: () -> Unit) {
    IconButton(
        onClick = upPress,
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .size(36.dp)
            .background(
                color = Color.White.copy(alpha = 0.32f),
                shape = CircleShape,
            ),
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            tint = JetsnackTheme.colors.iconInteractive,
            contentDescription = "Back",
        )
    }
}

@Composable
private fun Body(
    related: List<com.myapp.jetsnack.model.SnackCollection>,
    scroll: ScrollState,
    onSnackClick: (Long, String) -> Unit
) {
    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(MinTitleOffset),
        )
        Column(
            modifier = Modifier.verticalScroll(scroll),
        ) {
            Spacer(Modifier.height(GradientScroll))
            JetsnackSurface(Modifier.fillMaxWidth()) {
                Column {
                    Spacer(Modifier.height(ImageOverlap))
                    Spacer(Modifier.height(TitleHeight))

                    Text(
                        text = "Details",
                        style = MaterialTheme.typography.titleLarge,
                        color = JetsnackTheme.colors.textHelp,
                        modifier = Modifier.padding(horizontal = HzPadding),
                    )
                    Text(
                        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = JetsnackTheme.colors.textHelp,
                        modifier = Modifier.padding(
                            horizontal = HzPadding,
                            vertical = 16.dp,
                        ),
                    )

                    related.forEach { collection ->
                        SnackCollection(
                            snackCollection = collection,
                            onSnackClick = onSnackClick,
                            highlight = false,
                        )
                    }
                    Spacer(Modifier.height(BottomBarHeight))
                }
            }
        }
    }
}

@Composable
private fun Title(snack: Snack, origin: String, scroll: Int) {
    val maxOffset = with(LocalDensity.current) { GradientScroll.toPx() }
    val minOffset = with(LocalDensity.current) { MinTitleOffset.toPx() }
    val collapseFraction = (scroll / maxOffset).coerceIn(0f, 1f)
    val titleOffset = androidx.compose.ui.util.lerp(maxOffset, minOffset, collapseFraction)

    val sharedTransitionScope = LocalSharedTransitionScope.current
        ?: throw IllegalStateException("No sharedElementScope found")
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current
        ?: throw IllegalStateException("No animatedVisibilityScope found")

    with(sharedTransitionScope) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = HzPadding)
                .graphicsLayer { translationY = titleOffset }
                .sharedBounds(
                    rememberSharedContentState(
                        key = SnackSharedElementKey(
                            snackId = snack.id,
                            origin = origin,
                            type = SnackSharedElementType.Bounds,
                        ),
                    ),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = snackDetailBoundsTransform,
                ),
        ) {
            Spacer(Modifier.height(16.dp))
            Text(
                text = snack.name,
                style = MaterialTheme.typography.displaySmall,
                color = JetsnackTheme.colors.textSecondary,
                modifier = Modifier.sharedBounds(
                    rememberSharedContentState(
                        key = SnackSharedElementKey(
                            snackId = snack.id,
                            origin = origin,
                            type = SnackSharedElementType.Title,
                        ),
                    ),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = snackDetailBoundsTransform,
                ),
            )
            Text(
                text = snack.tagline,
                style = MaterialTheme.typography.titleLarge,
                color = JetsnackTheme.colors.textHelp,
                modifier = Modifier.sharedBounds(
                    rememberSharedContentState(
                        key = SnackSharedElementKey(
                            snackId = snack.id,
                            origin = origin,
                            type = SnackSharedElementType.Tagline,
                        ),
                    ),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = snackDetailBoundsTransform,
                ),
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = formatPrice(snack.price),
                style = MaterialTheme.typography.headlineSmall,
                color = JetsnackTheme.colors.brand,
            )
        }
    }
}

@Composable
private fun Image(
    imageUrl: String,
    origin: String,
    snackId: Long,
    scroll: Int
) {
    val collapseRange = with(LocalDensity.current) { (GradientScroll - MinTitleOffset).toPx() }
    val collapseFraction = (scroll / collapseRange).coerceIn(0f, 1f)

    val sharedTransitionScope = LocalSharedTransitionScope.current
        ?: throw IllegalStateException("No sharedElementScope found")
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current
        ?: throw IllegalStateException("No animatedVisibilityScope found")

    with(sharedTransitionScope) {
        CollapsingImageLayout(
            collapseFraction = collapseFraction,
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = HzPadding),
        ) {
            SnackImage(
                imageUrl = imageUrl,
                contentDescription = null,
                modifier = Modifier.sharedBounds(
                    rememberSharedContentState(
                        key = SnackSharedElementKey(
                            snackId = snackId,
                            origin = origin,
                            type = SnackSharedElementType.Image,
                        ),
                    ),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = snackDetailBoundsTransform,
                ),
            )
        }
    }
}

@Composable
private fun CollapsingImageLayout(
    collapseFraction: Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content,
    ) { measurables, constraints ->
        check(measurables.size == 1)

        val imageMaxSize = MaxImageSize.roundToPx()
        val imageMinSize = MinImageSize.roundToPx()
        val imageWidth = androidx.compose.ui.util.lerp(imageMaxSize, imageMinSize, collapseFraction)
        val imagePlaceable = measurables[0].measure(Constraints.fixed(imageWidth, imageWidth))

        val imageY = androidx.compose.ui.util.lerp(MinTitleOffset.toPx(), MinImageOffset.toPx(), collapseFraction).roundToInt()
        val imageX = androidx.compose.ui.util.lerp(
            ((constraints.maxWidth - imageWidth) / 2).toFloat(), // Centered when expanded
            (constraints.maxWidth - imageWidth).toFloat(), // Right aligned when collapsed
            collapseFraction,
        ).roundToInt()
        layout(width = constraints.maxWidth, height = imageY + imageWidth) {
            imagePlaceable.placeRelative(imageX, imageY)
        }
    }
}

@Composable
private fun CartBottomBar(snack: Snack, modifier: Modifier = Modifier) {
    var count by remember { mutableIntStateOf(1) }
    JetsnackSurface(modifier) {
        Column {
            HorizontalDivider(color = JetsnackTheme.colors.uiBorder.copy(alpha = 0.12f))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(horizontal = HzPadding, vertical = 12.dp)
                    .height(BottomBarHeight - 24.dp),
            ) {
                QuantitySelector(
                    count = count,
                    decreaseItemCount = { if (count > 0) count-- },
                    increaseItemCount = { count++ },
                )
                Spacer(Modifier.width(16.dp))
                JetsnackButton(
                    onClick = { CartRepo.addSnack(snack, count) },
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = "ADD TO CART",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                    )
                }
            }
        }
    }
}

private fun Modifier.graphicsLayer(translationY: Float): Modifier = graphicsLayer {
    this.translationY = translationY
}
