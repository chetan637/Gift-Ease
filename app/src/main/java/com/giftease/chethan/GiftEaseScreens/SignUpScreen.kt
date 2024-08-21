package com.giftease.chethan.GiftEaseScreens

import androidx.compose.foundation.Image
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
import com.giftease.chethan.components.CheckboxComponent
import com.giftease.chethan.components.ClickableLoginTextComponent
import com.giftease.chethan.components.DividerTextComponent
import com.giftease.chethan.components.HeadingTextComponent
import com.giftease.chethan.components.MyTextFieldComponent
import com.giftease.chethan.components.PasswordTextFieldComponent
import com.giftease.chethan.data.GiftEasesignup.GiftEaseSignupUIEvent
import com.giftease.chethan.data.GiftEasesignup.SignupGifteaseViewModel
import com.giftease.chethan.Gifteasenavigation.AppRouter
import com.giftease.chethan.Gifteasenavigation.Screen


@Composable
fun SignUpScreen(signupViewModel: SignupGifteaseViewModel = viewModel()) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center,

    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(color =  Color.White)
                .align(Alignment.Center)
        ) {
            Column(
                modifier = Modifier

                    .background(color = Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Image(modifier = Modifier.size(150.dp),
                    painter = painterResource(id = com.giftease.chethan.R.drawable.glogo), contentDescription = null)
                Spacer(modifier = Modifier.height(10.dp))
                  HeadingTextComponent(value = "GiftEase")
                  Spacer(modifier = Modifier.height(8.dp))
                  MyTextFieldComponent(
                      labelValue = stringResource(id = com.giftease.chethan.R.string.first_name),
                      painterResource(id = com.giftease.chethan.R.drawable.profile),
                      onTextChanged = {
                          signupViewModel.onEvent(GiftEaseSignupUIEvent.FirstNameChanged(it))
                      },
                      errorStatus = signupViewModel.registrationUIState.value.firstNameError,
                      TextStyle(color = Color.Blue, fontSize = 18.sp),
                      Color.LightGray,
 PaddingValues(16.dp)
                  )

                MyTextFieldComponent(
                    labelValue = stringResource(id =com.giftease.chethan. R.string.last_name),
                    painterResource = painterResource(id =com.giftease.chethan. R.drawable.profile),
                    onTextChanged = {
                        signupViewModel.onEvent(GiftEaseSignupUIEvent.LastNameChanged(it))
                    },
                    errorStatus = signupViewModel.registrationUIState.value.lastNameError,
                    textStyle = TextStyle(color = Color.Blue, fontSize = 18.sp),
                    backgroundColor = Color.LightGray,
                    contentPadding = PaddingValues(16.dp)
                )

                MyTextFieldComponent(
                    labelValue = stringResource(id = com.giftease.chethan.R.string.email),
                    painterResource = painterResource(id = com.giftease.chethan.R.drawable.message),
                    onTextChanged = {
                        signupViewModel.onEvent(GiftEaseSignupUIEvent.EmailChanged(it))
                    },
                    errorStatus = signupViewModel.registrationUIState.value.emailError,
                    textStyle = TextStyle(color = Color.Blue, fontSize = 18.sp),
                    backgroundColor = Color.LightGray,
                    contentPadding = PaddingValues(16.dp)
                )

                PasswordTextFieldComponent(
                    labelValue = stringResource(id = com.giftease.chethan.R.string.password),
                    painterResource = painterResource(id =com.giftease.chethan. R.drawable.ic_lock),
                    onTextSelected = {
                        signupViewModel.onEvent(GiftEaseSignupUIEvent.PasswordChanged(it))
                    },
                    errorStatus = signupViewModel.registrationUIState.value.passwordError
                )

                CheckboxComponent(value = stringResource(id =com.giftease.chethan. R.string.terms_and_conditions),
                    onTextSelected = {
                        AppRouter.navigateTo(Screen.TermsAndConditionsScreen)
                    },
                    onCheckedChange = {
                        signupViewModel.onEvent(GiftEaseSignupUIEvent.PrivacyPolicyCheckBoxClicked(it))
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                ButtonComponent(
                    value = stringResource(id = com.giftease.chethan.R.string.register),
                    onButtonClicked = {
                        signupViewModel.onEvent(GiftEaseSignupUIEvent.RegisterButtonClicked)
                    },
                    isEnabled = signupViewModel.allValidationsPassed.value
                )

                Spacer(modifier = Modifier.height(10.dp))

                DividerTextComponent()

                ClickableLoginTextComponent(tryingToLogin = true, onTextSelected = {
                    AppRouter.navigateTo(Screen.LoginScreen)
                })
            }
        }


        if(signupViewModel.signUpInProgress.value) {
            CircularProgressIndicator()
        }
    }

}

@Preview
@Composable
fun DefaultPreviewOfSignUpScreen() {
    SignUpScreen()
}


