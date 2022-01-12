package org.app.dzung.data.model.nft

sealed class NFTType(val name: String) {
    object DigitalArt : NFTType("Digital Art")
}
