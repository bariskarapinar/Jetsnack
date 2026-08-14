package com.myapp.jetsnack.ui.home.cart

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myapp.jetsnack.model.OrderLine
import com.myapp.jetsnack.model.SnackCollection
import com.myapp.jetsnack.model.SnackRepo
import com.myapp.jetsnack.ui.components.JetsnackButton
import com.myapp.jetsnack.ui.components.JetsnackDivider
import com.myapp.jetsnack.ui.components.JetsnackSurface
import com.myapp.jetsnack.ui.components.QuantitySelector
import com.myapp.jetsnack.ui.components.SnackCollection
import com.myapp.jetsnack.ui.components.SnackImage
import com.myapp.jetsnack.ui.home.DestinationBar
import com.myapp.jetsnack.ui.snackdetail.nonSpatialExpressiveSpring
import com.myapp.jetsnack.ui.snackdetail.spatialExpressiveSpring
import com.myapp.jetsnack.ui.theme.AlphaNearOpaque
import com.myapp.jetsnack.ui.theme.JetsnackTheme
import com.myapp.jetsnack.ui.utils.formatPrice
import kotlin.math.roundToInt

@Composable
fun Cart(
    onSnackClick: (Long, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = viewModel(factory = CartViewModel.provideFactory()),
) {
    val orderLines by viewModel.orderLines.collectAsStateWithLifecycle()
    val inspiredByCart = remember { SnackRepo.getInspiredByCart() }
    Cart(
        orderLines = orderLines,
        removeSnack = viewModel::removeSnack,
        increaseItemCount = viewModel::increaseSnackCount,
        decreaseItemCount = viewModel::decreaseSnackCount,
        checkout = viewModel::checkout,
        inspiredByCart = inspiredByCart,
        onSnackClick = onSnackClick,
        modifier = modifier,
    )
}

@Composable
fun Cart(
    orderLines: List<OrderLine>,
    removeSnack: (Long) -> Unit,
    increaseItemCount: (Long) -> Unit,
    decreaseItemCount: (Long) -> Unit,
    checkout: () -> Unit,
    inspiredByCart: SnackCollection,
    onSnackClick: (Long, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    JetsnackSurface(modifier = modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            CartContent(
                orderLines = orderLines,
                removeSnack = removeSnack,
                increaseItemCount = increaseItemCount,
                decreaseItemCount = decreaseItemCount,
                inspiredByCart = inspiredByCart,
                onSnackClick = onSnackClick,
                modifier = Modifier.align(Alignment.TopCenter),
            )
            DestinationBar(modifier = Modifier.align(Alignment.TopCenter))
            CheckoutBar(
                onCheckoutClick = checkout,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
private fun CartContent(
    orderLines: List<OrderLine>,
    removeSnack: (Long) -> Unit,
    increaseItemCount: (Long) -> Unit,
    decreaseItemCount: (Long) -> Unit,
    inspiredByCart: SnackCollection,
    onSnackClick: (Long, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val itemCount = orderLines.size
    val itemAnimationSpecFade = nonSpatialExpressiveSpring<Float>()
    val itemPlacementSpec = spatialExpressiveSpring<IntOffset>()

    LazyColumn(modifier) {
        item(key = "title") {
            Spacer(
                Modifier.windowInsetsTopHeight(
                    WindowInsets.statusBars.add(WindowInsets(top = 56.dp)),
                ),
            )
            Text(
                text = "My Cart ($itemCount items)",
                style = MaterialTheme.typography.titleLarge,
                color = JetsnackTheme.colors.brand,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .heightIn(min = 56.dp)
                    .padding(horizontal = 24.dp, vertical = 4.dp)
                    .wrapContentHeight(),
            )
        }
        items(orderLines, key = { it.snack.id }) { orderLine ->
            SwipeDismissItem(
                modifier = Modifier.animateItem(
                    fadeInSpec = itemAnimationSpecFade,
                    fadeOutSpec = itemAnimationSpecFade,
                    placementSpec = itemPlacementSpec,
                ),
                background = { progress ->
                    SwipeDismissItemBackground(progress)
                },
            ) {
                CartItem(
                    orderLine = orderLine,
                    removeSnack = removeSnack,
                    increaseItemCount = increaseItemCount,
                    decreaseItemCount = decreaseItemCount,
                    onSnackClick = onSnackClick,
                )
            }
        }
        item("summary") {
            SummaryItem(
                modifier = Modifier.animateItem(
                    fadeInSpec = itemAnimationSpecFade,
                    fadeOutSpec = itemAnimationSpecFade,
                    placementSpec = itemPlacementSpec,
                ),
                subtotal = orderLines.sumOf { it.snack.price * it.count },
                shippingCosts = 369,
            )
        }
        item(key = "inspiredByCart") {
            SnackCollection(
                modifier = Modifier.animateItem(
                    fadeInSpec = itemAnimationSpecFade,
                    fadeOutSpec = itemAnimationSpecFade,
                    placementSpec = itemPlacementSpec,
                ),
                snackCollection = inspiredByCart,
                onSnackClick = onSnackClick,
                highlight = false,
            )
            Spacer(Modifier.height(56.dp))
        }
    }
}

@Composable
private fun SwipeDismissItemBackground(progress: Float) {
    Column(
        modifier = Modifier
            .background(JetsnackTheme.colors.uiBackground)
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Center,
    ) {
        val padding: Dp by animateDpAsState(
            if (progress < 0.5f) 4.dp else 0.dp,
            label = "padding",
        )
        BoxWithConstraints(
            Modifier
                .fillMaxWidth(progress),
        ) {
            Surface(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxWidth()
                    .height(maxWidth)
                    .align(Alignment.Center),
                shape = RoundedCornerShape(percent = ((1 - progress) * 100).roundToInt()),
                color = JetsnackTheme.colors.error,
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    if (progress in 0.125f..0.475f) {
                        val iconAlpha: Float by animateFloatAsState(
                            if (progress > 0.4f) 0.5f else 1f,
                            label = "icon alpha",
                        )
                        Icon(
                            imageVector = Icons.Default.DeleteForever,
                            modifier = Modifier
                                .size(32.dp)
                                .graphicsLayer(alpha = iconAlpha),
                            tint = JetsnackTheme.colors.uiBackground,
                            contentDescription = null,
                        )
                    }
                    val textAlpha by animateFloatAsState(
                        if (progress > 0.5f) 1f else 0.5f,
                        label = "text alpha",
                    )
                    if (progress > 0.5f) {
                        Text(
                            text = "Remove",
                            style = MaterialTheme.typography.titleMedium,
                            color = JetsnackTheme.colors.uiBackground,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .graphicsLayer(
                                    alpha = textAlpha,
                                ),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CartItem(
    orderLine: OrderLine,
    removeSnack: (Long) -> Unit,
    increaseItemCount: (Long) -> Unit,
    decreaseItemCount: (Long) -> Unit,
    onSnackClick: (Long, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val snack = orderLine.snack
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onSnackClick(snack.id, "cart") }
            .background(JetsnackTheme.colors.uiBackground)
            .padding(horizontal = 24.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            SnackImage(
                imageUrl = snack.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .size(100.dp),
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = snack.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = JetsnackTheme.colors.textSecondary,
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 16.dp, end = 16.dp),
                    )
                    IconButton(
                        onClick = { removeSnack(snack.id) },
                        modifier = Modifier.padding(top = 12.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            tint = JetsnackTheme.colors.iconSecondary,
                            contentDescription = "Remove",
                        )
                    }
                }
                Text(
                    text = snack.tagline,
                    style = MaterialTheme.typography.bodyLarge,
                    color = JetsnackTheme.colors.textHelp,
                    modifier = Modifier.padding(end = 16.dp),
                )
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = formatPrice(snack.price),
                        style = MaterialTheme.typography.titleMedium,
                        color = JetsnackTheme.colors.textPrimary,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 16.dp)
                            .alignBy(LastBaseline),
                    )
                    QuantitySelector(
                        count = orderLine.count,
                        decreaseItemCount = { decreaseItemCount(snack.id) },
                        increaseItemCount = { increaseItemCount(snack.id) },
                        modifier = Modifier.alignBy(LastBaseline),
                    )
                }
            }
        }
        JetsnackDivider()
    }
}

@Composable
fun SummaryItem(subtotal: Long, shippingCosts: Long, modifier: Modifier = Modifier) {
    Column(modifier) {
        Text(
            text = "Summary",
            style = MaterialTheme.typography.titleLarge,
            color = JetsnackTheme.colors.brand,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .heightIn(min = 56.dp)
                .wrapContentHeight(),
        )
        Row(modifier = Modifier.padding(horizontal = 24.dp)) {
            Text(
                text = "Subtotal",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
                    .alignBy(LastBaseline),
            )
            Text(
                text = formatPrice(subtotal),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.alignBy(LastBaseline),
            )
        }
        Row(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
            Text(
                text = "Shipping & Handling",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
                    .alignBy(LastBaseline),
            )
            Text(
                text = formatPrice(shippingCosts),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.alignBy(LastBaseline),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        JetsnackDivider()
        Row(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
            Text(
                text = "Total",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
                    .wrapContentWidth(Alignment.End)
                    .alignBy(LastBaseline),
            )
            Text(
                text = formatPrice(subtotal + shippingCosts),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.alignBy(LastBaseline),
            )
        }
        JetsnackDivider()
    }
}

@Composable
private fun CheckoutBar(
    onCheckoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier.background(
            JetsnackTheme.colors.uiBackground.copy(alpha = AlphaNearOpaque),
        ),
    ) {
        JetsnackDivider()
        Row {
            Spacer(Modifier.weight(1f))
            JetsnackButton(
                onClick = onCheckoutClick,
                shape = RectangleShape,
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .weight(1f),
            ) {
                Text(
                    text = "Checkout",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                )
            }
        }
    }
}
