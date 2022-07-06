package com.example.mailapp

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.core.widget.addTextChangedListener
import com.example.mailapp.databinding.CompEditTextWithRuleBinding

/**
 * rule 정보: [rule 제목, rule 조건, rule 일치하지 않을시 메시지]
 * rule 정보를 입력받아, 입력값이 해당 rule 에 일치하는지 체크 후 뷰 변동
 * rule 과 일치한다면 입력값을, 일치하지 않는다면 null 리턴
 */
class EditTextWithRule @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
): BaseConstraintComp<CompEditTextWithRuleBinding>(context, attrs, defStyleAttr, defStyleRes) {

    override val layoutResId: Int
        get() = R.layout.comp_edit_text_with_rule

    var tagEditText: String? = null
        get() = vd.textInputEditText.tag as? String
        set(value) {
            vd.textInputEditText.tag = value
            field = value
        }

    class RuleInfo(
        val title: String,
        val ruleErrorMessage: String,
        val ruleCompare: (String)->(Boolean)
    )
    private var ruleInfo: RuleInfo? = null
    private fun setRules(ruleInfo: RuleInfo){
        this.ruleInfo = ruleInfo
        vd.textInputLayout.hint = ruleInfo.title
    }
    fun setRules(title: String, ruleErrorMessage: String, rule: (String)->(Boolean)){
        Log.d("TAG", "set rules => $title, $ruleErrorMessage")
        this.setRules(RuleInfo(title, ruleErrorMessage, rule))
    }

    private var textChangedListenerWithCorrectRule: ((String) -> (Unit))? = null
    fun setTextChangedWithCorrectRule(textChangedListenerWithCorrectRule: (String) -> (Unit)){
        this.textChangedListenerWithCorrectRule = textChangedListenerWithCorrectRule
    }

    init {
        attrs.let {
            val typedArr = context.obtainStyledAttributes(it, R.styleable.EditTextWithRule, 0, 0)
            typedArr.recycle()
        }
        setListener()
    }

    fun setText(text: String){
        vd.textInputEditText.setText(text)
    }

    private fun setListener(){
        vd.textInputEditText.addTextChangedListener {
            Log.d("TAG", "input [$tagEditText]=> $it")
            if(it.isNullOrBlank()) {
                setCheckRuleResult(true)
                return@addTextChangedListener
            }
            when(ruleInfo?.ruleCompare?.invoke(it.toString())){
                true -> {
                    setCheckRuleResult(true)
                    textChangedListenerWithCorrectRule?.invoke(it.toString())
                }
                else -> {
                    setCheckRuleResult(false)
                }
            }
        }
    }

    private fun setCheckRuleResult(correct: Boolean){
        vd.textInputLayout.error = if(correct)null else ruleInfo?.ruleErrorMessage
    }

}