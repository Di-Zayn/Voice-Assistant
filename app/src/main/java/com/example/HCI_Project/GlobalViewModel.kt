package com.example.HCI_Project

import androidx.lifecycle.MutableLiveData

object GlobalViewModel {
    private val text = MutableLiveData<String>()

    fun getText(): String? {
        return text.value
    }

    fun setText(_text: String) {
        text.value = _text
    }
}