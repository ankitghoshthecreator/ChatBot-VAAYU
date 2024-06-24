package com.example.chatbot

import android.annotation.SuppressLint
import android.telephony.SmsMessage
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch





@SuppressLint("SecretInSource")
class ChatBotVM : ViewModel() {
    val list = mutableStateListOf<ChatData>()

    private val genAi by lazy {
        GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = ApiKey
        )
    }

    fun sendMessage(message: String) = viewModelScope.launch {
        val chat = genAi.startChat()

        list.add(ChatData(message, ChatRoleEnum.USER.role))

        val response = chat.sendMessage(
            content(ChatRoleEnum.USER.role) { text(message) }
        ).text

        response?.let {
            list.add(ChatData(it, ChatRoleEnum.MODEL.role))
        }
    }
}