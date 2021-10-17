package br.com.fomezero.centralfood.layouts

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import br.com.fomezero.centralfood.R
import br.com.fomezero.centralfood.databinding.InputTextLayoutBinding

class InputTextLayout(
    context: Context,
    attributes: AttributeSet
) : LinearLayout(context, attributes) {

    private val binding: InputTextLayoutBinding =
        InputTextLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.obtainStyledAttributes(attributes, R.styleable.InputTextLayout).let {
            binding.apply {
                title.text = it.getString(R.styleable.InputTextLayout_inputTitle).orEmpty()
                field.setHint(it.getString(R.styleable.InputTextLayout_inputHint).orEmpty())
                field.setText(it.getString(R.styleable.InputTextLayout_inputText).orEmpty())
            }

            it.recycle()
        }
    }
}