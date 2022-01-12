package org.app.dzung.data.model.transaction

sealed class TransactionType {
    object In: TransactionType()
    object Out: TransactionType()
}