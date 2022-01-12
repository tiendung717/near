package org.app.dzung.data.model.nft

import org.app.dzung.common.shortString

data class NftDetails(
    val tokenId: String,
    val contract: String
) {
    fun getShortenContract() = contract.shortString()
}