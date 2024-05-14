package com.coderscastle.simplecalculator
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.coderscastle.simplecalculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var currentInput = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {

            clearBtn.setOnClickListener { onBackspaceClick(it) }
            startBracketBtn.setOnClickListener { onBracketClick(it) }
            endBracketBtn.setOnClickListener { onBracketClick(it) }
            divisionBtn.setOnClickListener { onOperatorClick(it) }
            intoBtn.setOnClickListener { onOperatorClick(it) }
            minusBtn.setOnClickListener { onOperatorClick(it) }
            plusBtn.setOnClickListener { onOperatorClick(it) }
            acBtn.setOnClickListener { onAlClearClick() }
            btn0.setOnClickListener { onDigitClick(it) }
            btn1.setOnClickListener { onDigitClick(it) }
            btn2.setOnClickListener { onDigitClick(it) }
            btn3.setOnClickListener { onDigitClick(it) }
            btn4.setOnClickListener { onDigitClick(it) }
            btn5.setOnClickListener { onDigitClick(it) }
            btn6.setOnClickListener { onDigitClick(it) }
            btn7.setOnClickListener { onDigitClick(it) }
            btn8.setOnClickListener { onDigitClick(it) }
            btn9.setOnClickListener { onDigitClick(it) }
            pointBtn.setOnClickListener { onDigitClick(it) }
            equalBtn.setOnClickListener { onEqualClick() }
        }
    }

    fun onDigitClick(view: View) {
        val digit = (view as TextView).text.toString()

        if (binding.resultTv.text.isNotEmpty()) {
            currentInput = ""
            binding.resultTv.text = ""
        }

        currentInput += digit
        binding.displayTv.text = currentInput
    }

    fun onOperatorClick(view: View) {
        val operator = (view as TextView).text.toString()

        if (binding.resultTv.text.toString() == "0") {
            return
        }

        if (binding.resultTv.text.isNotEmpty()) {
            currentInput = binding.resultTv.text.toString()
            binding.resultTv.text = ""
        }
        if (currentInput.isNotEmpty() && (currentInput.last() == '+' || currentInput.last() == '-' || currentInput.last() == '×' || currentInput.last() == '÷')) {

            currentInput = currentInput.substring(0, currentInput.length - 1)
        }

        if (currentInput.isNotEmpty() && currentInput.last() == '.') {
            return
        }

        currentInput += operator
        binding.displayTv.text = currentInput
    }

    fun onBackspaceClick(view: View) {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.substring(0, currentInput.length - 1)
            binding.displayTv.text = currentInput
        }
        if (binding.resultTv.text.isNotEmpty()) {
            binding.resultTv.text = ""
        }
    }

    fun onAlClearClick() {
        currentInput = ""
        binding.displayTv.text = ""
        binding.resultTv.text = ""
    }

    fun onEqualClick() {
        try {
            val result = evaluateExpression(currentInput)
            val formattedResult = if (result % 1 == 0.0) {
                result.toInt().toString()
            } else {
                result.toString()
            }
            binding.resultTv.text = formattedResult
        } catch (e: Exception) {
            binding.resultTv.text = getString(R.string.error)
        }
    }


    fun onBracketClick(view: View) {
        val bracket = (view as TextView).text

        if (binding.resultTv.text.isNotEmpty()) {
            currentInput = ""
            binding.resultTv.text = ""
        }
        currentInput += bracket
        binding.displayTv.text = currentInput
    }

    private fun evaluateExpression(expression: String): Double {
        val expressionWithSymbols = expression.replace("×", "*").replace("÷", "/")
        val result = ExpressionBuilder(expressionWithSymbols).build().evaluate()
        return result
    }
}