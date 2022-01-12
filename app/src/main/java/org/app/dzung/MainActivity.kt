package org.app.dzung

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.navigationBarsPadding
import dagger.hilt.android.AndroidEntryPoint
import org.app.dzung.ui.component.bottomsheet.BottomSheetCreateNearAccount
import org.app.dzung.ui.component.bottomsheet.BottomSheetEmpty
import org.app.dzung.ui.component.bottomsheet.BottomSheetGift
import org.app.dzung.ui.component.bottomsheet.BottomSheetVerifyUser
import org.app.dzung.ui.component.model.BottomTabItem
import org.app.dzung.ui.event.BottomSheetEvent
import org.app.dzung.ui.event.NavEvent
import org.app.dzung.ui.event.SnackBarEvent
import org.app.dzung.ui.event.initEventHandler
import org.app.dzung.ui.navigation.AppGraph
import org.app.dzung.ui.navigation.NavTarget
import org.app.dzung.ui.theme.AppTheme
import org.app.dzung.ui.theme.Blue

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AppUI()
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AppUI() {
    ProvideWindowInsets {
        AppTheme {
            val tabs = remember { BottomTabItem.values() }
            val navController = rememberNavController()

            val eventHandler = initEventHandler()
            val bottomState =
                rememberModalBottomSheetState(ModalBottomSheetValue.Hidden, confirmStateChange = {
                    if (it == ModalBottomSheetValue.Hidden) {
                        eventHandler.postBottomSheetEvent(BottomSheetEvent.None)
                    }
                    true
                })
            when (eventHandler.bottomSheetEvent) {
                is BottomSheetEvent.None -> LaunchedEffect(
                    key1 = "hide",
                    block = {
                        bottomState.hide()
                    })
                is BottomSheetEvent.VerifyUser,
                BottomSheetEvent.CreateNearAccount,
                BottomSheetEvent.Gift -> LaunchedEffect(
                    key1 = "show",
                    block = {
                        bottomState.animateTo(ModalBottomSheetValue.Expanded)
                    })
            }

            val scaffoldState = rememberScaffoldState()
            val message = when (val event = eventHandler.snackBarEvent) {
                is SnackBarEvent.None -> ""
                is SnackBarEvent.Error -> event.error
                is SnackBarEvent.Info -> event.message
            }

            if (message.isNotEmpty()) {
                Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
            }

            ModalBottomSheetLayout(
                sheetState = bottomState,
                sheetContent = {
                    when (val event = eventHandler.bottomSheetEvent) {
                        is BottomSheetEvent.None -> BottomSheetEmpty()
                        is BottomSheetEvent.VerifyUser -> BottomSheetVerifyUser(event)
                        is BottomSheetEvent.CreateNearAccount -> BottomSheetCreateNearAccount()
                        is BottomSheetEvent.Gift -> BottomSheetGift {
                            eventHandler.run {
                                postBottomSheetEvent(BottomSheetEvent.None)
                                postNavEvent(NavEvent.Action(NavTarget.Home))
                            }
                        }
                    }
                }
            ) {
                Scaffold(
                    scaffoldState = scaffoldState,
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        AppBottomBar(navController, tabs)
                    }
                ) {
                    AppGraph(navController)

                }
            }
        }
    }


}

@Composable
private fun AppBottomBar(navController: NavController, tabs: Array<BottomTabItem>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val routes = remember { tabs.map { it.route } }
    if (currentRoute in routes) {
        BottomNavigation(
            Modifier.navigationBarsHeight(additional = 56.dp),
            backgroundColor = Color.White
        ) {
            tabs.forEach { tab ->
                BottomNavigationItem(
                    icon = { Icon(painterResource(tab.icon), contentDescription = null) },
                    selected = currentRoute == tab.route,
                    onClick = {
                        if (tab.route != currentRoute) {
                            navController.navigate(tab.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    alwaysShowLabel = false,
                    selectedContentColor = Blue,
                    unselectedContentColor = Color.Black,
                    modifier = Modifier.navigationBarsPadding()
                )
            }
        }
    }
}

