package org.app.dzung.ui.screen

import android.content.Intent
import android.net.Uri
import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.app.dzung.R
import org.app.dzung.ui.component.model.AuthMode
import org.app.dzung.ui.event.BottomSheetEvent
import org.app.dzung.ui.event.NavEvent
import org.app.dzung.ui.event.initEventHandler
import org.app.dzung.ui.navigation.NavTarget
import org.app.dzung.ui.theme.Blue
import org.app.dzung.ui.theme.Yellow

@Composable
fun ScreenAuthentication() {
    val eventHandler = initEventHandler()
    val localFocus = LocalFocusManager.current
    ScreenAuthentication(
        onGetStarted = { mode, text ->
            localFocus.clearFocus(true)
            eventHandler.postBottomSheetEvent(BottomSheetEvent.VerifyUser(mode, text))
        }, onLogin = {
            localFocus.clearFocus(true)
            eventHandler.postNavEvent(NavEvent.Action(NavTarget.Home))
        }
    )
}

@Composable
private fun ScreenAuthentication(
    onGetStarted: (AuthMode, String) -> Unit,
    onLogin: () -> Unit
) {
    ConstraintLayout(Modifier.fillMaxSize()) {
        val (backgroundId, logoId, content) = createRefs()

        Image(
            modifier = Modifier
                .constrainAs(backgroundId) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .background(Yellow),
            alignment = Alignment.TopStart,
            painter = painterResource(id = R.drawable.ic_onboard),
            contentDescription = stringResource(R.string.onboard)
        )
        Image(
            modifier = Modifier
                .constrainAs(logoId) {
                    bottom.linkTo(backgroundId.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(vertical = 16.dp),
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = stringResource(
                id = R.string.logo
            )
        )
        Column(
            modifier = Modifier
                .constrainAs(content) {
                    top.linkTo(backgroundId.bottom)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }
                .fillMaxWidth()
                .background(Color.White)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailAndPhone(onGetStarted)
            NearAccount(onLogin)
        }
    }
}


@Composable
private fun EmailAndPhone(onGetStarted: (AuthMode, String) -> Unit) {
    var mode by remember { mutableStateOf(AuthMode.Email) }
    var input by remember { mutableStateOf(TextFieldValue()) }

    Row {
        TextButton(
            onClick = {
                mode = AuthMode.Email
                input = TextFieldValue()
            },
            colors = ButtonDefaults.textButtonColors(
                contentColor = AuthMode.Email.getSelectionColor(mode)
            )
        ) {
            Text(text = stringResource(R.string.email))
        }
        Spacer(
            modifier = Modifier
                .width(24.dp)
                .wrapContentHeight()
        )
        TextButton(
            onClick = {
                mode = AuthMode.Phone
                input = TextFieldValue()
            },
            colors = ButtonDefaults.textButtonColors(
                contentColor = AuthMode.Phone.getSelectionColor(mode)
            )
        ) {
            Text(text = stringResource(R.string.phone))
        }
    }


    OutlinedTextField(modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = mode.getKeyboardType()),
        value = input,
        label = {
            Text(text = stringResource(mode.getLabel()))
        },
        onValueChange = { input = it })

    Button(
        modifier = Modifier.padding(top = 16.dp),
        onClick = {
            onGetStarted.invoke(mode, input.text)
        },
        enabled = if (mode.isEmail()) Patterns.EMAIL_ADDRESS.matcher(input.text)
            .matches() else input.text.isNotEmpty()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(R.string.get_started))
            Icon(Icons.Default.KeyboardArrowRight, "")
        }
    }
}

@Composable
private fun NearAccount(onLogin: () -> Unit) {
    var account by remember { mutableStateOf(TextFieldValue()) }
    Divider(
        Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
            .height(1.dp)
    )

    Text(
        modifier = Modifier.padding(top = 24.dp),
        text = stringResource(R.string.already_have_near_account)
    )
    OutlinedTextField(modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        value = account,
        label = {
            Text(text = stringResource(R.string.wallet_name_near))
        },
        onValueChange = { account = it })
    Button(
        modifier = Modifier.padding(top = 16.dp),
        onClick = onLogin,
        enabled = account.text.isNotEmpty()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(R.string.login))
            Icon(Icons.Default.KeyboardArrowRight, "")
        }
    }
    val tcAndPrivacy = buildAnnotatedString {
        append("by clicking continue you must agree to near labs\n")
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
}


@Composable
@Preview
private fun ScreenAuthenticationPreview() {
    ScreenAuthentication({ _, _ -> }, {})
}