package org.app.dzung.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.app.dzung.ui.theme.Blue

@Composable
fun Header(header: String, action: String, onActionClick: () -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (title, seeAll) = createRefs()
        Text(
            modifier = Modifier.constrainAs(title) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(seeAll.start)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
            },
            text = header,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        TextButton(modifier = Modifier.constrainAs(seeAll) {
            top.linkTo(title.top)
            end.linkTo(parent.end)
            bottom.linkTo(title.bottom)
        }, onClick = onActionClick) {
            Text(

                text = action,
                fontWeight = FontWeight.Normal,
                color = Blue
            )
        }
    }
}

@Composable
@Preview
private fun HeaderPreview() {
    Header(header = "header", action = "action") {}
}

