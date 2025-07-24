package com.example.geomcalc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class CalculationActivity : AppCompatActivity() {
    private lateinit var textShapeName: TextView
    private lateinit var radioArea: RadioButton
    private lateinit var radioPerimeter: RadioButton
    private lateinit var buttonCalculate: Button
    private lateinit var textResult: TextView
    private lateinit var buttonShare: Button
    private lateinit var imageFormula: ImageView

    private lateinit var label1: TextView
    private lateinit var label2: TextView
    private lateinit var label3: TextView
    private lateinit var label4: TextView
    private lateinit var input1: EditText
    private lateinit var input2: EditText
    private lateinit var input3: EditText
    private lateinit var input4: EditText

    private var shape: String = ""
    private var calculationType: String = "area" // "area" или "perimeter"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculation)

        initViews()
        setupDefaults()
        setListeners()
    }

    private fun initViews() {
        textShapeName = findViewById(R.id.textShapeName)
        radioArea = findViewById(R.id.radioArea)
        radioPerimeter = findViewById(R.id.radioPerimeter)
        buttonCalculate = findViewById(R.id.buttonCalculate)
        textResult = findViewById(R.id.textResult)
        buttonShare = findViewById(R.id.buttonShare)
        imageFormula = findViewById(R.id.imageFormula)

        label1 = findViewById(R.id.label1)
        label2 = findViewById(R.id.label2)
        label3 = findViewById(R.id.label3)
        label4 = findViewById(R.id.label4)
        input1 = findViewById(R.id.input1)
        input2 = findViewById(R.id.input2)
        input3 = findViewById(R.id.input3)
        input4 = findViewById(R.id.input4)
    }

    private fun setupDefaults() {
        shape = intent.getStringExtra("SHAPE") ?: "Неизвестная фигура"
        textShapeName.text = "Фигура: $shape"

        // Устанавливаем площадь по умолчанию
        radioArea.isChecked = true
        calculationType = "area"

        updateUI()
    }

    private fun setListeners() {
        radioArea.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                calculationType = "area"
                updateUI()
            }
        }

        radioPerimeter.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                calculationType = "perimeter"
                updateUI()
            }
        }

        buttonCalculate.setOnClickListener { calculate() }
        buttonShare.setOnClickListener { shareResult() }
    }

    private fun updateUI() {
        setupFieldsVisibility()
        updateFormulaImage()
    }

    private fun setupFieldsVisibility() {
        when (shape) {
            "Квадрат" -> {
                label1.text = if (calculationType == "area") "Введите сторону" else "Введите сторону"
                showFields(1)
            }
            "Прямоугольник" -> {
                label1.text = "Введите длину"
                label2.text = "Введите ширину"
                showFields(2)
            }
            "Треугольник" -> {
                if (calculationType == "area") {
                    label1.text = "Введите основание"
                    label2.text = "Введите высоту"
                    showFields(2)
                } else {
                    label1.text = "Введите сторону a"
                    label2.text = "Введите сторону b"
                    label3.text = "Введите сторону c"
                    showFields(3)
                }
            }
            "Круг" -> {
                label1.text = "Введите радиус"
                showFields(1)
            }
            "Ромб" -> {
                if (calculationType == "area") {
                    label1.text = "Введите диагональ 1"
                    label2.text = "Введите диагональ 2"
                    showFields(2)
                } else {
                    label1.text = "Введите сторону"
                    showFields(1)
                }
            }
            "Трапеция" -> {
                label1.text = "Введите основание a"
                label2.text = "Введите основание b"
                if (calculationType == "area") {
                    label3.text = "Введите высоту"
                    showFields(3)
                } else {
                    label3.text = "Введите боковую сторону c"
                    label4.text = "Введите боковую сторону d"
                    showFields(4)
                }
            }
        }
    }

    private fun showFields(count: Int) {
        val fields = listOf(
            label1 to input1,
            label2 to input2,
            label3 to input3,
            label4 to input4
        )

        fields.forEachIndexed { index, (label, input) ->
            if (index < count) {
                label.visibility = View.VISIBLE
                input.visibility = View.VISIBLE
            } else {
                label.visibility = View.GONE
                input.visibility = View.GONE
                input.text.clear()
            }
        }
    }

    private fun updateFormulaImage() {
        val imageName = when (shape) {
            "Квадрат" -> "square_$calculationType"
            "Прямоугольник" -> "rectangle_$calculationType"
            "Треугольник" -> "triangle_$calculationType"
            "Круг" -> "circle_$calculationType"
            "Ромб" -> "rhombus_$calculationType"
            "Трапеция" -> "trapezoid_$calculationType"
            else -> null
        }

        imageName?.let { name ->
            val resId = resources.getIdentifier(name, "drawable", packageName)
            if (resId != 0) {
                imageFormula.setImageResource(resId)
                imageFormula.visibility = View.VISIBLE
            } else {
                imageFormula.visibility = View.GONE
                Log.e("FormulaImage", "Image not found: $name")
            }
        } ?: run {
            imageFormula.visibility = View.GONE
        }
    }

    private fun calculate() {
        try {
            val val1 = input1.text.toString().toDoubleOrNull() ?: 0.0
            val val2 = input2.text.toString().toDoubleOrNull() ?: 0.0
            val val3 = input3.text.toString().toDoubleOrNull() ?: 0.0
            val val4 = input4.text.toString().toDoubleOrNull() ?: 0.0

            val result = when (shape) {
                "Квадрат" -> if (calculationType == "area") val1 * val1 else 4 * val1
                "Прямоугольник" -> if (calculationType == "area") val1 * val2 else 2 * (val1 + val2)
                "Треугольник" -> if (calculationType == "area") 0.5 * val1 * val2 else val1 + val2 + val3
                "Круг" -> if (calculationType == "area") Math.PI * val1 * val1 else 2 * Math.PI * val1
                "Ромб" -> if (calculationType == "area") (val1 * val2) / 2 else 4 * val1
                "Трапеция" -> if (calculationType == "area") 0.5 * (val1 + val2) * val3 else val1 + val2 + val3 + val4
                else -> 0.0
            }

            textResult.text = "Результат: ${"%.2f".format(result)}"
        } catch (e: Exception) {
            Toast.makeText(this, "Ошибка в расчетах: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun shareResult() {
        val resultText = textResult.text.toString()
        if (resultText == "Результат: ") {
            Toast.makeText(this, "Сначала рассчитайте результат", Toast.LENGTH_SHORT).show()
            return
        }

        val shareText = """
            Результат расчета:
            Фигура: $shape
            Тип: ${if (calculationType == "area") "Площадь" else "Периметр"}
            $resultText
        """.trimIndent()

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }

        startActivity(Intent.createChooser(shareIntent, "Поделиться результатом"))
    }
}