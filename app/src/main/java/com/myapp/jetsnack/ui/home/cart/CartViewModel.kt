package com.myapp.jetsnack.ui.home.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myapp.jetsnack.model.OrderLine
import com.myapp.jetsnack.model.SnackRepo
import com.myapp.jetsnack.model.SnackbarManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Holds the contents of the cart and allows changes to it.
 */
class CartViewModel(private val snackbarManager: SnackbarManager, snackRepository: SnackRepo) : ViewModel() {

    private val _orderLines: MutableStateFlow<List<OrderLine>> = MutableStateFlow(snackRepository.getCart())
    val orderLines: StateFlow<List<OrderLine>> get() = _orderLines

    private var requestCount = 0
    private fun shouldRandomlyFail(): Boolean = ++requestCount % 5 == 0

    fun increaseSnackCount(snackId: Long) {
        if (!shouldRandomlyFail()) {
            val currentCount = _orderLines.value.first { it.snack.id == snackId }.count
            updateSnackCount(snackId, currentCount + 1)
        } else {
            snackbarManager.showMessage("Could not increase count. Please try again.")
        }
    }

    fun decreaseSnackCount(snackId: Long) {
        if (!shouldRandomlyFail()) {
            val currentCount = _orderLines.value.first { it.snack.id == snackId }.count
            if (currentCount == 1) {
                removeSnack(snackId)
            } else {
                updateSnackCount(snackId, currentCount - 1)
            }
        } else {
            snackbarManager.showMessage("Could not decrease count. Please try again.")
        }
    }

    fun removeSnack(snackId: Long) {
        _orderLines.value = _orderLines.value.filter { it.snack.id != snackId }
    }

    private fun updateSnackCount(snackId: Long, count: Int) {
        _orderLines.value = _orderLines.value.map {
            if (it.snack.id == snackId) {
                it.copy(count = count)
            } else {
                it
            }
        }
    }

    companion object {
        fun provideFactory(
            snackbarManager: SnackbarManager = SnackbarManager,
            snackRepository: SnackRepo = SnackRepo,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CartViewModel(snackbarManager, snackRepository) as T
            }
        }
    }
}
