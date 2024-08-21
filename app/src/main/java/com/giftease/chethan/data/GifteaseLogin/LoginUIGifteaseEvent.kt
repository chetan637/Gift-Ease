package com.giftease.chethan.data.GifteaseLogin

sealed class LoginUIGifteaseEvent{

    data class EmailChanged(val email:String): LoginUIGifteaseEvent()
    data class PasswordChanged(val password: String) : LoginUIGifteaseEvent()

    object LoginButtonClicked : LoginUIGifteaseEvent()
}
