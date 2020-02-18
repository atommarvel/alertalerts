package com.radiantmood.alertalerts.ui.common

import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.radiantmood.alertalerts.FormEditTextBindingModelBuilder
import com.radiantmood.alertalerts.R

fun FormEditTextBindingModelBuilder.watch(textWatcher: TextWatcher) {
    fun View.editText() = findViewById<EditText>(R.id.input_et)
    onBindView { it.editText().addTextChangedListener(textWatcher) }
    onUnBindView { it.editText().removeTextChangedListener(textWatcher) }
}

fun FormEditTextBindingModelBuilder.onBindView(block: (view: View) -> Unit) {
    this.onBind { _, view, _ -> block(view.dataBinding.root) }
}

fun FormEditTextBindingModelBuilder.onUnBindView(block: (view: View) -> Unit) {
    this.onUnbind { _, view -> block(view.dataBinding.root) }
}