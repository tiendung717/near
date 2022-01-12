package org.app.dzung.ui.component.bottomsheet

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.app.dzung.R
import org.app.dzung.ui.event.BottomSheetEvent
import org.app.dzung.ui.event.initEventHandler
import org.app.dzung.ui.theme.Blue
import org.app.dzung.ui.theme.Gray1

@Composable
fun BottomSheetCreateNearAccount() {
    val eventHandler = initEventHandler()
    BottomSheetCreateNearAccount(
        onCreateAccount = {
            eventHandler.postBottomSheetEvent(BottomSheetEvent.Gift)
        }, onReadyHaveAccount = {
            eventHandler.postBottomSheetEvent(BottomSheetEvent.None)
        }, onClose = {
            eventHandler.postBottomSheetEvent(BottomSheetEvent.None)
        }
    )
}

@Composable
fun BottomSheetCreateNearAccount(
    onCreateAccount: () -> Unit,
    onReadyHaveAccount: () -> Unit,
    onClose: () -> Unit
) {
    var name by remember { mutableStateOf(TextFieldValue()) }
    var account by remember { mutableStateOf(TextFieldValue()) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        BottomSheetHeader(title = stringResource(id = R.string.create_near_accoutn)) {
            onClose.invoke()
        }

        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            progress = 0.66f,
            backgroundColor = Color.White
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.create_account_instruction),
            color = Gray1
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.full_name).uppercase(),
            color = Gray1,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = name,
            onValueChange = { name = it })

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.account_id).uppercase(),
            color = Gray1,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = account,
            onValueChange = { account = it })

        Button(
            onClick = onCreateAccount, colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Black,
                contentColor = Color.White
            ), enabled = name.text.isNotEmpty() && account.text.isNotEmpty()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(R.string.create_an_account))
                Icon(Icons.Default.KeyboardArrowRight, "")
            }
        }

        val tcAndPrivacy = buildAnnotatedString {
            append("By creating a Near account, you agree to the NEAR Wallet\n")
            pushStringAnnotation(
                tag = "TcUrl",
                annotation = "https://en.wikipedia.org/wiki/Terms_of_service"
            )
            withStyle(style = SpanStyle(color = Blue)) {
                append(stringResource(R.string.term_and_conditions))
            }
            append(" and ")
            pushStringAnnotation(
                tag = "PrivacyUrl",
                annotation = "https://en.wikipedia.org/wiki/Privacy"
            )
            withStyle(style = SpanStyle(color = Blue)) {
                append(stringResource(R.string.privacy_policy))
            }
        }
        val context = LocalContext.current

        ClickableText(modifier = Modifier
            .padding(top = 16.dp),
            text = tcAndPrivacy,
            style = TextStyle.Default.copy(textAlign = TextAlign.Center),
            onClick = { offset ->
                var selectedLink = ""
                tcAndPrivacy.getStringAnnotations(
                    tag = "TcUrl", start = offset,
                    end = offset
                ).firstOrNull()?.let {
                    selectedLink = it.item
                }
                tcAndPrivacy.getStringAnnotations(
                    tag = "PrivacyUrl",
                    start = offset,
                    end = offset
                ).firstOrNull()?.let { selectedLink = it.item }

                if (selectedLink.isNotEmpty()) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(selectedLink))
                    context.startActivity(intent)
                }
            }
        )

        Text(
            modifier = Modifier.padding(top = 24.dp),
            text = stringResource(id = R.string.already_have_near_account_)
        )

        Button(onClick = onReadyHaveAccount) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(R.string.login_with_near))
                Icon(Icons.Default.KeyboardArrowRight, "")
            }
        }
    }
}

@Composable
@Preview
private fun BottomSheetCreateNearAccountPreview() {
    BottomSheetCreateNearAccount({}, {}, {})
}


