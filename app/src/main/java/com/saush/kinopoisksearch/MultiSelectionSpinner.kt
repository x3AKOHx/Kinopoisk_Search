package com.saush.kinopoisksearch

import android.content.Context
import android.content.DialogInterface
import android.util.AttributeSet
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner

class MultiSelectionSpinner(context: Context): AppCompatSpinner(context),
    DialogInterface.OnMultiChoiceClickListener {

    val items = mutableListOf<Item>()
    val selection = mutableListOf<Boolean>()
    var adapter: ArrayAdapter<String>

    constructor(context: Context) : super(context) {
        adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item)
        super.setAdapter(adapter)
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item)
        super.setAdapter(adapter)
    }

    override fun onClick(p0: DialogInterface?, p1: Int, p2: Boolean) {
        TODO("Not yet implemented")
    }
}

