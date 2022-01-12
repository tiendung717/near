package org.app.dzung.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.app.dzung.R
import org.app.dzung.common.StateResult
import org.app.dzung.data.model.transaction.Transaction
import org.app.dzung.ui.component.Header
import org.app.dzung.ui.component.TransactionItem
import org.app.dzung.ui.event.NavEvent
import org.app.dzung.ui.event.initEventHandler
import org.app.dzung.ui.navigation.NavTarget
import org.app.dzung.ui.component.UserInfo
import org.app.dzung.ui.theme.Blue
import org.app.dzung.viewmodel.initTransactionViewModel

@Composable
fun ScreenTransactions() {
    val eventHandler = initEventHandler()
    val transactionViewModel = initTransactionViewModel()

    val transactions: MutableState<StateResult<List<Transaction>>> = remember {
        mutableStateOf(StateResult.None)
    }

    var tabIndex by remember { mutableStateOf(0) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
            .padding(top = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            UserInfo()
        }

        if (transactions.value is StateResult.Success) {
            item {
                Header(
                    header = stringResource(id = R.string.history_transaction),
                    action = stringResource(id = R.string.send_nft)
                ) {
                    eventHandler.postNavEvent(NavEvent.Action(NavTarget.SendNFT))
                }
            }

            item {
                TransactionTab(tabIndex = tabIndex) {
                    tabIndex = it
                }
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    val allTransactions = transactions.value.takeValueOrThrow()
                    val sentTransactions = allTransactions.filterNot { it.isIncoming() }
                    val recvTransactions = allTransactions.filter { it.isIncoming() }
                    when (tabIndex) {
                        0 -> allTransactions.forEach { TransactionItem(it) }
                        1 -> sentTransactions.forEach { TransactionItem(it) }
                        2 -> recvTransactions.forEach { TransactionItem(it) }
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = "fetchData") {
        transactionViewModel.getHistoryTransactions(transactions)
    }
}

@Composable
private fun TransactionTab(tabIndex: Int, onTabSelected: (Int) -> Unit) {
    TabRow(
        modifier = Modifier
            .wrapContentWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .border(1.dp, Blue, RoundedCornerShape(10.dp)),
        selectedTabIndex = tabIndex,
        backgroundColor = Color.Transparent,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                height = 0.dp,
                color = Color.Gray
            )
        },
        divider = {
            TabRowDefaults.Divider(thickness = 0.dp)
        }
    ) {
        listOf(
            stringResource(id = R.string.transaction_all),
            stringResource(id = R.string.transaction_sent),
            stringResource(id = R.string.transaction_received)
        ).forEachIndexed { index, title ->
            val tabSelected = index == tabIndex
            Tab(
                modifier = Modifier
                    .wrapContentWidth()
                    .background(
                        if (tabSelected) Blue else Color.Transparent
                    ),
                selected = tabSelected,
                onClick = { onTabSelected(index) },
                text = {
                    Text(
                        modifier = Modifier.wrapContentWidth(),
                        text = title,
                        fontWeight = FontWeight.Bold
                    )
                },
                selectedContentColor = Color.White,
                unselectedContentColor = Blue
            )
        }
    }
}