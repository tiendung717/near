package org.app.dzung.viewmodel

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun initAssetViewModel() : AssetViewModel {
    val context = LocalContext.current as ComponentActivity
    return hiltViewModel(context)
}

@Composable
fun initTransactionViewModel() : TransactionViewModel {
    val context = LocalContext.current as ComponentActivity
    return hiltViewModel(context)
}