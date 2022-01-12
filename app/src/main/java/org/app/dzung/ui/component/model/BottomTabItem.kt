package org.app.dzung.ui.component.model

import androidx.annotation.DrawableRes
import org.app.dzung.R
import org.app.dzung.ui.navigation.NavTarget

enum class BottomTabItem(
    @DrawableRes val icon: Int,
    val route: String
) {
    HOME(R.drawable.ic_home, NavTarget.Home.route),
    NFT(R.drawable.ic_nft, NavTarget.CreateNFT.route),
    TRANSACTION(R.drawable.ic_transaction, NavTarget.Transaction.route)
}