package com.myapp.jetsnack.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * A simple repository that manages the cart state.
 */
object CartRepo {
    private val _orderLines = MutableStateFlow<List<OrderLine>>(emptyList())
    val orderLines: StateFlow<List<OrderLine>> = _orderLines.asStateFlow()

    fun addSnack(snack: Snack, count: Int = 1) {
        _orderLines.update { currentOrderLines ->
            val existingOrderLine = currentOrderLines.find { it.snack.id == snack.id }
            if (existingOrderLine != null) {
                currentOrderLines.map {
                    if (it.snack.id == snack.id) it.copy(count = it.count + count) else it
                }
            } else {
                currentOrderLines + OrderLine(snack, count)
            }
        }
    }

    fun removeSnack(snackId: Long) {
        _orderLines.update { currentOrderLines ->
            currentOrderLines.filter { it.snack.id != snackId }
        }
    }

    fun updateSnackCount(snackId: Long, count: Int) {
        _orderLines.update { currentOrderLines ->
            currentOrderLines.map {
                if (it.snack.id == snackId) it.copy(count = count) else it
            }
        }
    }

    fun clearCart() {
        _orderLines.value = emptyList()
    }
}
