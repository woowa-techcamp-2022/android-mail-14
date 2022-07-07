package com.example.mailapp.model

import com.example.mailapp.R
import java.util.regex.Pattern
import kotlin.random.Random

data class MailModel(
    val senderId: String,
    val title: String,
    val content: String,
    val date: String,
    val type: MailType
) {
    companion object {
        private val colors= arrayOf(
            "#FF03DAC5",
            "#FF018786",
            "#03A9F4",
            "#FF6200EE",
            "#009688",
            "#FFEB3B",
            "#FFC107",
            "#CDDC39",
            "#FF5722",
            "#00BCD4",
        )
    }
    enum class MailType {
        Primary,
        Promotion,
        Social
    }
    //profile image 에 표시될 이름(첫글자가 영문이라면 첫글자, 아니라면 Null)
    val printId: String? = if(Pattern.compile("^[a-zA-Z0-9]+$").matcher(senderId.first().toString()).matches()) senderId.first().toString() else null
    // profile color : color list 에서 랜덤하게 하나 지정
    val profileColor: String = colors[Random.nextInt(colors.size)]
    // default profile image res id
    val defaultProfileImageResId: Int = R.drawable.ic_person_circle
}