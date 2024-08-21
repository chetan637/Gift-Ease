package  com.giftease.chethan.data.GiftEasesignup

sealed class GiftEaseSignupUIEvent{

    data class FirstNameChanged(val firstName:String) : GiftEaseSignupUIEvent()
    data class LastNameChanged(val lastName:String) : GiftEaseSignupUIEvent()
    data class EmailChanged(val email:String): GiftEaseSignupUIEvent()
    data class PasswordChanged(val password: String) : GiftEaseSignupUIEvent()

    data class PrivacyPolicyCheckBoxClicked(val status:Boolean) : GiftEaseSignupUIEvent()

    object RegisterButtonClicked : GiftEaseSignupUIEvent()
}
