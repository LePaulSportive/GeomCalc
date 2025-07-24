package com.example.geomcalc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttons: Map<Int, String> = mapOf(
            R.id.button_square to "Квадрат",
            R.id.button_rectangle to "Прямоугольник",
            R.id.button_triangle to "Треугольник",
            R.id.button_circle to "Круг",
            R.id.button_trapezoid to "Трапеция",
            R.id.button_rhombus to "Ромб"
        )

        for ((id, shape) in buttons) {
            findViewById<Button>(id).setOnClickListener {
                val intent = Intent(this, CalculationActivity::class.java)
                intent.putExtra("SHAPE", shape) // Теперь передача корректная
                startActivity(intent)
            }
        }
    }
}
