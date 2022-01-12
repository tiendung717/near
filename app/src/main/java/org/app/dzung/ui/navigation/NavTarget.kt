package org.app.dzung.ui.navigation

sealed class NavTarget(val route: String) {
    object Authentication : NavTarget("authentication")
    object Home : NavTarget("home")
    object NFT: NavTarget("nft")
    object NFTDetails: NavTarget("ntf/details")
    object CreateNFT : NavTarget("nft/create")
    object SendNFT : NavTarget("nft/send")
    object Transaction : NavTarget("transaction")
}