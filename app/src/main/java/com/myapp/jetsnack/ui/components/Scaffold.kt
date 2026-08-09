package com.myapp.jetsnack.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.myapp.jetsnack.model.SnackbarManager
import com.myapp.jetsnack.ui.theme.JetsnackTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Wrap Material [androidx.compose.material3.Scaffold] and set [JetsnackTheme] colors.
 */
@Composable
fun JetsnackScaffold(
    modifier: Modifier = Modifier,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    topBar: @Composable (() -> Unit) = {},
    bottomBar: @Composable (() -> Unit) = {},
    snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
    floatingActionButton: @Composable (() -> Unit) = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    backgroundColor: Color = JetsnackTheme.colors.uiBackground,
    contentColor: Color = JetsnackTheme.colors.textSecondary,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = { snackbarHost(snackBarHostState) },
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = backgroundColor,
        contentColor = contentColor,
        content = content,
    )
}

/**
 * Remember and creates an instance of [JetsnackScaffoldState]
 */
@Composable
fun rememberJetsnackScaffoldState(
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    snackbarManager: SnackbarManager = SnackbarManager,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): JetsnackScaffoldState = remember(snackBarHostState, snackbarManager, coroutineScope) {
    JetsnackScaffoldState(snackBarHostState, snackbarManager, coroutineScope)
}

/**
 * Responsible for holding [ScaffoldState], handles the logic of showing snackbar messages
 */
@Stable
class JetsnackScaffoldState(
    val snackBarHostState: SnackbarHostState,
    private val snackbarManager: SnackbarManager,
    coroutineScope: CoroutineScope,
) {
    // Process snackbars coming from SnackbarManager
    init {
        coroutineScope.launch {
            snackbarManager.messages.collect { currentMessages ->
                if (currentMessages.isNotEmpty()) {
                    val message = currentMessages[0]
                    val text = message.message

                    // Notify the SnackbarManager so it can remove the current message from the list
                    snackbarManager.setMessageShown(message.id)

                    // Display the snackbar on the screen. `showSnackbar` is a function
                    // that suspends until the snackbar disappears from the screen
                    snackBarHostState.showSnackbar(text)
                }
            }
        }
    }
}
