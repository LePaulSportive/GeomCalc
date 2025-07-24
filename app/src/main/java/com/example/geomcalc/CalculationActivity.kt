package com.example.geomcalc

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class CalculationActivity : AppCompatActivity() {
    private lateinit var textShapeName: TextView
    private lateinit var radioArea: RadioButton
    private lateinit var radioPerimeter: RadioButton
    private lateinit var buttonCalculate: Button
    private lateinit var textResult: TextView

    private lateinit var label1: TextView
    private lateinit var label2: TextView
    private lateinit var label3: TextView
    private lateinit var label4: TextView
    private lateinit var input1: EditText
    private lateinit var input2: EditText
    private lateinit var input3: EditText
    private lateinit var input4: EditText

    private var shape: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculation)

        textShapeName = findViewById(R.id.textShapeName)
        radioArea = findViewById(R.id.radioArea)
        radioPerimeter = findViewById(R.id.radioPerimeter)
        buttonCalculate = findViewById(R.id.buttonCalculate)
        textResult = findViewById(R.id.textResult)

        label1 = findViewById(R.id.label1)
        label2 = findViewById(R.id.label2)
        label3 = findViewById(R.id.label3)
        label4 = findViewById(R.id.label4)
        input1 = findViewById(R.id.input1)
        input2 = findViewById(R.id.input2)
        input3 = findViewById(R.id.input3)
        input4 = findViewById(R.id.input4)

        shape = intent.getStringExtra("SHAPE") ?: "Неизвестная фигура"
        textShapeName.text = "Фигура: $shape"

        setupFieldsVisibility()

        radioArea.setOnCheckedChangeListener { _, _ -> setupFieldsVisibility() }
        radioPerimeter.setOnCheckedChangeListener { _, _ -> setupFieldsVisibility() }

        buttonCalculate.setOnClickListener { calculate() }
    }

    private fun setupFieldsVisibility() {
        when (shape) {
            "Квадрат" -> {
                label1.text = "Введите сторону"
                showFields(1)
            }
            "Прямоугольник" -> {
                label1.text = "Введите длину"
                label2.text = "Введите ширину"
                showFields(2)
            }
            "Треугольник" -> {
                if (radioArea.isChecked) {
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
                if (radioArea.isChecked) {
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
                if (radioArea.isChecked) {
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
        val fields = listOf(label1 to input1, label2 to input2, label3 to input3, label4 to input4)
        for (i in fields.indices) {
            if (i < count) {
                fields[i].first.visibility = View.VISIBLE
                fields[i].second.visibility = View.VISIBLE
            } else {
                fields[i].first.visibility = View.GONE
                fields[i].second.visibility = View.GONE
            }
        }
    }

    private fun calculate() {
        val val1 = input1.text.toString().toDoubleOrNull() ?: 0.0
        val val2 = input2.text.toString().toDoubleOrNull() ?: 0.0
        val val3 = input3.text.toString().toDoubleOrNull() ?: 0.0
        val val4 = input4.text.toString().toDoubleOrNull() ?: 0.0

        val result = when (shape) {
            "Квадрат" -> if (radioArea.isChecked) val1 * val1 else 4 * val1
            "Прямоугольник" -> if (radioArea.isChecked) val1 * val2 else 2 * (val1 + val2)
            "Треугольник" -> if (radioArea.isChecked) 0.5 * val1 * val2 else val1 + val2 + val3
            "Круг" -> if (radioArea.isChecked) Math.PI * val1 * val1 else 2 * Math.PI * val1
            "Ромб" -> if (radioArea.isChecked) (val1 * val2) / 2 else 4 * val1
            "Трапеция" -> if (radioArea.isChecked) 0.5 * (val1 + val2) * val3 else val1 + val2 + val3 + val4
            else -> 0.0
        }

        textResult.text = "Результат: $result"
    }
}
