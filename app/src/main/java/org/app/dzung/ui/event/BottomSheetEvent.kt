package org.app.dzung.ui.event

import org.app.dzung.ui.component.model.AuthMode

sealed class BottomSheetEvent {
    object None : BottomSheetEvent()
    class VerifyUser(val mode: AuthMode, val input: String) : BottomSheetEvent()
    object CreateNearAccount : BottomSheetEvent()
    object Gift : BottomSheetEvent()
}