package org.app.dzung.ui.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.app.dzung.R
import org.app.dzung.data.DataSource
import org.app.dzung.data.model.Contact
import org.app.dzung.ui.event.BottomSheetEvent
import org.app.dzung.ui.event.initEventHandler
import org.app.dzung.ui.theme.Blue
import org.app.dzung.ui.theme.Gray2
import org.app.dzung.ui.theme.Gray3
import org.app.dzung.ui.theme.Gray4

@Composable
fun BottomSheetGift(sendGift: () -> Unit) {
    val searchText = remember { mutableStateOf(TextFieldValue()) }
    val eventHandler = initEventHandler()

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BottomSheetHeader(title = stringResource(id = R.string.gift_an_nft)) {
            eventHandler.postBottomSheetEvent(BottomSheetEvent.None)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                modifier = Modifier
                    .background(color = Color.Transparent)
                    .fillMaxWidth(),
                value = searchText.value,
                onValueChange = {
                    searchText.value = it
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "search",
                        tint = Gray4
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(6.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                trailingIcon = {
                    TextButton(
                        onClick = { }) {
                        Text(text = stringResource(id = R.string.import_contact), color = Blue)
                    }
                }
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(vertical = 12.dp)
        ) {
            items(DataSource.contacts()) {
                ContactItem(contact = it)
            }
        }

        Button(
            modifier = Modifier
                .wrapContentSize()
                .background(color = Blue, RoundedCornerShape(10.dp)),
            onClick = sendGift
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(id = R.string.send_gift), color = Color.White)
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowRight,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }

        TextButton(
            modifier = Modifier.padding(vertical = 12.dp),
            onClick = { }) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(id = R.string.share_app), color = Blue)
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_share),
                    contentDescription = "",
                    tint = Blue
                )
            }
        }
    }
}

@Composable
private fun ContactItem(contact: Contact) {
    var isSelected by remember {
        mutableStateOf(false)
    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 12.dp)
            .clickable { isSelected = !isSelected }
    ) {
        val (image, name, nick, check) = createRefs()

        Box(modifier = Modifier
            .constrainAs(image) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
            .size(48.dp),
            contentAlignment = Alignment.Center) {
            Text(
                text = contact.name.take(2).uppercase(),
                textAlign = TextAlign.Center,
                color = Gray2,
                fontWeight = FontWeight.SemiBold
            )
        }

        Text(
            modifier = Modifier.constrainAs(name) {
                start.linkTo(image.end, margin = 12.dp)
                top.linkTo(parent.top, margin = 12.dp)
                bottom.linkTo(nick.top)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
            text = contact.name
        )
        Text(
            modifier = Modifier.constrainAs(nick) {
                start.linkTo(name.start)
                top.linkTo(name.bottom)
                bottom.linkTo(parent.bottom, margin = 12.dp)
                end.linkTo(name.end)
                width = Dimension.fillToConstraints
            },
            text = contact.nick,
            color = Gray3
        )
        Icon(
            modifier = Modifier.constrainAs(check) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            imageVector = ImageVector.vectorResource(id = if (isSelected) R.drawable.ic_checked else R.drawable.ic_uncheck),
            contentDescription = "checked",
            tint = Color.Unspecified
        )
    }
}