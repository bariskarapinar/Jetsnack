package com.myapp.jetsnack.ui.home.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myapp.jetsnack.model.CartRepo
import com.myapp.jetsnack.model.OrderLine
import com.myapp.jetsnack.model.SnackRepo
import com.myapp.jetsnack.model.SnackbarManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Holds the contents of the cart and allows changes to it.
 */
class CartViewModel(
    private val snackbarManager: SnackbarManager,
    private val cartRepo: CartRepo = CartRepo
) : ViewModel() {

    val orderLines: StateFlow<List<OrderLine>> = cartRepo.orderLines

    private var requestCount = 0
    private fun shouldRandomlyFail(): Boolean = ++requestCount % 5 == 0

    fun increaseSnackCount(snackId: Long) {
        if (!shouldRandomlyFail()) {
            val currentCount = orderLines.value.first { it.snack.id == snackId }.count
            cartRepo.updateSnackCount(snackId, currentCount + 1)
        } else {
            snackbarManager.showMessage("Could not increase count. Please try again.")
        }
    }

    fun decreaseSnackCount(snackId: Long) {
        if (!shouldRandomlyFail()) {
            val currentCount = orderLines.value.first { it.snack.id == snackId }.count
            if (currentCount == 1) {
                removeSnack(snackId)
            } else {
                cartRepo.updateSnackCount(snackId, currentCount - 1)
            }
        } else {
            snackbarManager.showMessage("Could not decrease count. Please try again.")
        }
    }

    fun removeSnack(snackId: Long) {
        cartRepo.removeSnack(snackId)
    }

    fun checkout() {
        cartRepo.clearCart()
        snackbarManager.showMessage("Ordered! Thank you for your purchase.")
    }

    companion object {
        fun provideFactory(
            snackbarManager: SnackbarManager = SnackbarManager,
            cartRepo: CartRepo = CartRepo,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CartViewModel(snackbarManager, cartRepo) as T
            }
        }
    }
}
