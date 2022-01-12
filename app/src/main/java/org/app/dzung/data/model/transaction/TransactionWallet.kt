package org.app.dzung.data.model.transaction

import org.app.dzung.common.shortString

data class TransactionWallet(
    val address: String,
    val name: String
) {
    fun getShortenAddress() = address.shortString()
}