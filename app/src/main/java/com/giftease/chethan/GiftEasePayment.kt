
package com.giftease.chethan

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.giftease.chethan.components.HeadingTextComponent
import com.giftease.chethan.ui.theme.TextColor

class PaymentActivity : ComponentActivity() {

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val context = LocalContext.current
            val gradient45 = Brush.linearGradient(
                colors = listOf(Color.White, Color.White),
                start = Offset(0f, Float.POSITIVE_INFINITY),
                end = Offset(Float.POSITIVE_INFINITY, 0f)
            )

            Surface(
                modifier = Modifier
                    .background(gradient45)
                    .fillMaxSize()
                    .padding(28.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(gradient45),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(50.dp))
                    Spacer(modifier = Modifier.height(15.dp))
                    HeadingTextComponent(value = "Payment Details")
                    Spacer(modifier = Modifier.height(15.dp))

                    var bankName by remember { mutableStateOf("") }
                    var cardNumber by remember { mutableStateOf("") }
                    var sortCode by remember { mutableStateOf("") }
                    var cvvNumber by remember { mutableStateOf("") }

                    OutlinedTextField(
                        value = bankName,
                        onValueChange = { bankName = it },
                        label = { Text("Bank Name", style = TextStyle(color = Color.Blue, fontSize = 18.sp)) },
                        leadingIcon = { Icon(painterResource(id = R.drawable.card_png1), contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Blue,
                            unfocusedBorderColor = Color.Gray,
                            backgroundColor = Color.LightGray
                        )
                    )

                    OutlinedTextField(
                        value = cardNumber,
                        onValueChange = { cardNumber = it },
                        label = { Text("Card Number", style = TextStyle(color = Color.Red, fontSize = 16.sp)) },
                        leadingIcon = { Icon(painterResource(id = R.drawable.card_png1), contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Red,
                            unfocusedBorderColor = Color.Gray,
                            backgroundColor = Color.Yellow
                        )
                    )

                    OutlinedTextField(
                        value = sortCode,
                        onValueChange = { sortCode = it },
                        label = { Text("Sort Code", style = TextStyle(color = Color.Green, fontSize = 14.sp)) },
                        leadingIcon = { Icon(painterResource(id = R.drawable.card_png1), contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Green,
                            unfocusedBorderColor = Color.Gray,
                            backgroundColor = Color.Cyan
                        )
                    )

                    OutlinedTextField(
                        value = cvvNumber,
                        onValueChange = { cvvNumber = it },
                        label = { Text("3 digits Cvv Number", style = TextStyle(color = Color.Magenta, fontSize = 20.sp)) },
                        leadingIcon = { Icon(painterResource(id = R.drawable.card_png1), contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Magenta,
                            unfocusedBorderColor = Color.Gray,
                            backgroundColor = Color.Blue
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        modifier = Modifier
                            .wrapContentWidth()
                            .heightIn(48.dp),
                        onClick = {
                            context.startActivity(Intent(context, OrderGiftEase::class.java))
                        },
                        contentPadding = PaddingValues(),
                        colors = ButtonDefaults.buttonColors(Color.Transparent),
                        shape = RoundedCornerShape(50.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(48.dp)
                                .background(
                                    brush = Brush.horizontalGradient(listOf(TextColor, TextColor)),
                                    shape = RoundedCornerShape(20.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Proceed",
                                fontSize = 18.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }











}

@Preview
@Composable
fun DefaultPreviewOfSignUpScreen() {

}



