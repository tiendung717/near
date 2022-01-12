package org.app.dzung.ui.component.model

import androidx.compose.ui.text.input.KeyboardType
import org.app.dzung.R
import org.app.dzung.ui.theme.Gray2
import org.app.dzung.ui.theme.Gray3

enum class AuthMode {
    Email,
    Phone;

    fun getSelectionColor(currentMode: AuthMode) = if (this == currentMode) Gray2 else Gray3
    fun getLabel() = when (this) {
        Phone -> R.string.phone_label
        Email -> R.string.email_label
    }

    fun getKeyboardType() = when (this) {
        Phone -> KeyboardType.Phone
        Email -> KeyboardType.Email
    }

    fun isEmail() = this == Email
}