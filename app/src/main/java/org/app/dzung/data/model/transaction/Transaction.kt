package org.app.dzung.data.model.transaction

import org.ocpsoft.prettytime.PrettyTime
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import java.util.*


data class Transaction(
    val id: Long,
    val sender: TransactionWallet,
    val receiver: TransactionWallet,
    val type: TransactionType,
    val timestamp: LocalDateTime
) {
    fun identifier() = "#$id"
    fun isIncoming() = type == TransactionType.In
    fun getPrettyTime() : String {
        return PrettyTime().format(
            Date(timestamp.atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli())
        )
    }
}