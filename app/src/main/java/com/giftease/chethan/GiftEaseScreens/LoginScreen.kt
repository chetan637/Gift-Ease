package com.giftease.chethan.GiftEaseScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.giftease.chethan.components.ButtonComponent
import com.giftease.chethan.components.ClickableLoginTextComponent
import com.giftease.chethan.components.DividerTextComponent
import com.giftease.chethan.components.HeadingTextComponent
import com.giftease.chethan.components.MyTextFieldComponent
import com.giftease.chethan.components.PasswordTextFieldComponent
import com.giftease.chethan.data.GifteaseViewModelL
import com.giftease.chethan.data.GifteaseLogin.LoginUIGifteaseEvent
import com.giftease.chethan.Gifteasenavigation.AppRouter
import com.giftease.chethan.Gifteasenavigation.Screen
import com.giftease.chethan.Gifteasenavigation.SystemBackButtonHandler


@Composable
fun LoginScreen(loginViewModel: GifteaseViewModelL = viewModel()) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)
        ) {

            Column( modifier = Modifier.fillMaxSize()) {

                Spacer(modifier = Modifier.height(20.dp))
                HeadingTextComponent(value = "Login")
                Spacer(modifier = Modifier.height(20.dp))

                MyTextFieldComponent(
                    labelValue = stringResource(id = com.giftease.chethan.R.string.email),
                    painterResource(id = com.giftease.chethan.R.drawable.message),
                    onTextChanged = { loginViewModel.onEvent(LoginUIGifteaseEvent.EmailChanged(it)) },
                    errorStatus = loginViewModel.loginUIState.value.emailError,
                    TextStyle(color = Color.Blue, fontSize = 18.sp),
                    Color.LightGray,
 PaddingValues(16.dp)
                )

                PasswordTextFieldComponent(
                    labelValue = stringResource(id =com.giftease.chethan.R.string.password),
                    painterResource(id = com.giftease.chethan.R.drawable.lock),
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIGifteaseEvent.PasswordChanged(it))
                    },
                    errorStatus = loginViewModel.loginUIState.value.passwordError
                )

                Spacer(modifier = Modifier.height(40.dp))

                Spacer(modifier = Modifier.height(40.dp))

                ButtonComponent(
                    value = stringResource(id =com.giftease.chethan. R.string.login),
                    onButtonClicked = {
                       loginViewModel.onEvent(LoginUIGifteaseEvent.LoginButtonClicked)
                    },
                    isEnabled = loginViewModel.allValidationsPassed.value
                )

                Spacer(modifier = Modifier.height(20.dp))

                DividerTextComponent()

                ClickableLoginTextComponent(tryingToLogin = false, onTextSelected = {
                    AppRouter.navigateTo(Screen.SignUpScreen)
                })
            }
        }

        if(loginViewModel.loginInProgress.value) {
            CircularProgressIndicator()
        }
    }

    SystemBackButtonHandler {
        AppRouter.navigateTo(Screen.SignUpScreen)
    }

}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}

