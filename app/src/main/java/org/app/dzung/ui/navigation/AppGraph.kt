package org.app.dzung.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.app.dzung.common.handleNavEvent
import org.app.dzung.ui.event.initEventHandler
import org.app.dzung.ui.screen.ScreenAssets
import org.app.dzung.ui.screen.ScreenAuthentication
import org.app.dzung.ui.screen.ScreenHome
import org.app.dzung.ui.screen.ScreenTransactions
import org.app.dzung.ui.screen.*

@Composable
fun AppGraph(navController: NavHostController) {
    val eventHandler = initEventHandler()
    navController.handleNavEvent(eventHandler.navEvent)

    NavHost(navController, NavTarget.Authentication.route) {
        composable(NavTarget.Authentication.route) {
            ScreenAuthentication()
        }
        composable(NavTarget.Home.route) {
            ScreenHome()
        }
        composable(NavTarget.NFT.route) {
            ScreenAssets()
        }
        composable(NavTarget.Transaction.route) {
            ScreenTransactions()
        }
        composable(NavTarget.NFTDetails.route) {
            ScreenAssetDetails()
        }
        composable(NavTarget.CreateNFT.route) {
            ScreenCreateNft()
        }
        composable(NavTarget.SendNFT.route) {
            ScreenSendNft()
        }
    }
}