package org.app.dzung.common

import androidx.navigation.NavHostController
import org.app.dzung.ui.event.NavEvent

fun NavHostController.handleNavEvent(navEvent: NavEvent) {
    when (navEvent) {
        is NavEvent.Action -> {
            navigate(navEvent.target.route)
        }
        is NavEvent.ActionInclusive -> {
            navigate(navEvent.target.route) {
                popUpTo(navEvent.inclusiveTarget.route) {
                    inclusive = true
                }
            }
        }
        is NavEvent.PopBackStack -> {
            popBackStack(navEvent.target.route, inclusive = navEvent.inclusive)
        }
        is NavEvent.NavigateUp -> {
            navigateUp()
        }
        else -> {

        }
    }
}