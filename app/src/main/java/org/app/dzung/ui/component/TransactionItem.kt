package org.app.dzung.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.app.dzung.R
import org.app.dzung.data.model.transaction.Transaction
import org.app.dzung.ui.theme.Blue
import org.app.dzung.ui.theme.Gray1
import org.app.dzung.ui.theme.Gray4


@Composable
fun TransactionItem(transaction: Transaction) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (icon, order, desc, timestamp) = createRefs()
        Icon(
            modifier = Modifier
                .constrainAs(icon) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .size(50.dp)
                .border(1.dp, Gray4, CircleShape)
                .padding(16.dp),
            imageVector = ImageVector.vectorResource(id = if (transaction.isIncoming()) R.drawable.ic_receive else R.drawable.ic_sent),
            contentDescription = "icon"
        )

        Text(
            modifier = Modifier.constrainAs(order) {
                start.linkTo(icon.end, margin = 12.dp)
                top.linkTo(parent.top, margin = 8.dp)
                end.linkTo(timestamp.start)
                bottom.linkTo(desc.top)
                width = Dimension.fillToConstraints
            },
            text = transaction.identifier(),
            color = Blue,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier.constrainAs(desc) {
                start.linkTo(order.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom, margin = 8.dp)
                top.linkTo(order.bottom, margin = 2.dp)
                width = Dimension.fillToConstraints
            },
            text = when {
                transaction.isIncoming() -> buildAnnotatedString {
                    append(stringResource(id = R.string.receive_from))
                    append(" ")
                    withStyle(SpanStyle(Blue)) {
                        append(transaction.sender.getShortenAddress())
                    }
                }
                else -> buildAnnotatedString {
                    append(stringResource(id = R.string.sent_to))
                    append(" ")
                    withStyle(SpanStyle(Blue)) {
                        append(transaction.receiver.name)
                    }
                }
            },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 13.sp
        )

        Text(
            modifier = Modifier.constrainAs(timestamp) {
                end.linkTo(parent.end)
                top.linkTo(order.top)
                bottom.linkTo(order.bottom)
            },
            text = transaction.getPrettyTime(),
            color = Gray1
        )
    }
}