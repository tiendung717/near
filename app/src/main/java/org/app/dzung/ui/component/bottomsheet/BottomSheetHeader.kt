package org.app.dzung.ui.component.bottomsheet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import org.app.dzung.ui.theme.Gray1

@Composable
fun BottomSheetHeader(title: String, onCloseClicked: () -> Unit) {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (text, button) = createRefs()
        Text(
            modifier = Modifier
                .constrainAs(text) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxWidth(),
            text = title,
            textAlign = TextAlign.Center,
            fontSize = 22.sp
        )
        IconButton(
            modifier = Modifier.constrainAs(button) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            onClick = onCloseClicked
        ) {
            Icon(Icons.Rounded.Close, contentDescription = null, tint = Gray1)
        }
    }
}