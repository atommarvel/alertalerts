package com.radiantmood.alertalerts.util

import android.text.Editable
import android.text.TextWatcher

open class SimpleTextWatcher : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun afterTextChanged(s: Editable?) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onChange(s.toString())
    }

    open fun onChange(text: String) {}
}

fun onTextChange(block: (text: String) -> Unit) = object : SimpleTextWatcher() {
    override fun onChange(text: String) = block(text)
}