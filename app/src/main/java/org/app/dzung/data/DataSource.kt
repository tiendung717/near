package org.app.dzung.data

import org.app.dzung.data.model.Contact
import org.app.dzung.data.model.nft.*
import org.app.dzung.data.model.transaction.Transaction
import org.app.dzung.data.model.transaction.TransactionWallet
import org.app.dzung.data.model.transaction.TransactionType
import org.threeten.bp.LocalDateTime
import kotlin.random.Random


object DataSource {

    private fun assetNames() = listOf(
        "Test 00",
        "Test 01",
        "Test 02",
        "Test 03"
    )

    private fun contactNames() = listOf(
        "Darlene Robertson",
        "Jacob Jones",
    )

    fun contacts(): List<Contact> {
        val names = contactNames()
        return (0..100).map {
            Contact(
                id = it,
                name = names[it % names.size],
                nick = "@johndoe",
                image = ""
            )
        }
    }

    fun assets(): List<Nft> {
        val names = assetNames()
        return (10720L..10820L).map {
            val assetName = names[it.toInt() % names.size]
            Nft(
                id = it,
                name = assetName,
                type = NFTType.DigitalArt,
                image = if (it % 2 == 0L) DummyImageUrl.IMAGE_1 else DummyImageUrl.IMAGE_2,
                nftOwner = NftOwner(
                    name = "john_doe",
                    image = ""
                ),
                info = NftDetails(
                    tokenId = "38943",
                    contract = "0x6a62089778465f8BD5f98A388C17bb023f7C683C".lowercase()
                ),
                description = "Having returned home to Rathleigh House near Macroom, Cork, Ireland, the hot-tempered Art became involved in a feud with a protestant landowner and magistrate, "
            )
        }
    }

    fun transactions(): List<Transaction> {
        return (10720L..10820L).map {
            Transaction(
                id = it,
                sender = TransactionWallet(
                    name = "john.near",
                    address = "0x6a62089778465f8BD5f98A388C17bb023f7C683C".lowercase()
                ),
                receiver = TransactionWallet(
                    name = "john.near",
                    address = "0x6a62089778465f8BD5f98A388C17bb023f7C683C".lowercase()
                ),
                type = if (Random.nextBoolean()) TransactionType.In else TransactionType.Out,
                timestamp = when (it % 2) {
                    0L -> LocalDateTime.now().minusDays(7)
                    else -> LocalDateTime.now().minusMinutes(5)
                }
            )
        }.sortedByDescending {
            it.timestamp
        }
    }
}