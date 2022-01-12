package org.app.dzung.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.app.dzung.common.StateResult
import org.app.dzung.common.getState
import org.app.dzung.data.Repository
import org.app.dzung.data.model.transaction.Transaction
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun getRecentTransactions(state: MutableState<StateResult<List<Transaction>>>) = getState(state) {
        repository.getRecentTransactions()
    }

    fun getHistoryTransactions(state: MutableState<StateResult<List<Transaction>>>) = getState(state) {
        repository.getHistoryTransactions()
    }

}