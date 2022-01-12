package org.app.dzung.data.model.nft

import org.app.dzung.R

object DummyImageUrl {
     const val IMAGE_1 = "image_1"
     const val IMAGE_2 = "image_2"
}

data class Nft(
    val id: Long,
    val name: String,
    val type: NFTType,
    val image: String,
    val nftOwner: NftOwner,
    val description: String,
    val info: NftDetails
) {
     fun identifier() = "#$id"

     fun getImageResource() = when (image) {
          DummyImageUrl.IMAGE_1 -> R.drawable.asset1
          else -> R.drawable.asset2
     }
}

