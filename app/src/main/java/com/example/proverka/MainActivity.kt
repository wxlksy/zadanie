package com.example.proverka

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.TextView
import kotlin.random.Random
import kotlin.Boolean


class MainActivity : AppCompatActivity()
{
    private lateinit var start_button: Button
    private lateinit var true_button: Button
    private lateinit var false_button: Button
    private lateinit var textOperation: TextView
    private lateinit var textFirstOperand: TextView
    private lateinit var textSecondOperand: TextView
    private lateinit var textEqually: TextView
    private lateinit var textResult: TextView
    private lateinit var textViewTrue: TextView
    private lateinit var textViewTime: TextView
    private lateinit var textViewPercentage: TextView
    private lateinit var textViewTimeAvg: TextView
    private lateinit var textNumberFalse: TextView
    private lateinit var textViewTimeMax: TextView
    private lateinit var textViewTimeMin: TextView

    private var startTime: Long = 0
    private var correctAnswers = 0
    private var totalAnswer = 0
    private var maxTime = Long.MAX_VALUE
    private var minTime = Long.MIN_VALUE
    private var totalTime = 0L


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_button = findViewById(R.id.start_button)
        true_button = findViewById(R.id.true_button)
        false_button = findViewById(R.id.false_button)
        textOperation = findViewById(R.id.textOperation)
        textFirstOperand = findViewById(R.id.textFirstOperand)
        textSecondOperand = findViewById(R.id.textSecondOperand)
        textEqually = findViewById(R.id.textEqually)
        textResult = findViewById(R.id.textResult)
        textViewTrue = findViewById(R.id.textViewTrue)
        textViewTime = findViewById(R.id.textViewTime)
        textViewPercentage = findViewById(R.id.textViewPercentage)
        textViewTimeAvg = findViewById(R.id.textViewTimeAvg)
        textNumberFalse = findViewById(R.id.textNumberFalse)
        textViewTimeMax = findViewById(R.id.textViewTimeMax)
        textViewTimeMin = findViewById(R.id.textViewTimeMin)

        start_button.setOnClickListener {
            generateExample()
            true_button.isEnabled = true
            false_button.isEnabled = true
            startTime = SystemClock.uptimeMillis()
        }

        true_button.setOnClickListener {
            checkAnswer(true)
        }

        false_button.setOnClickListener {
            checkAnswer(false)
        }
    }

    private fun generateExample()
    {
        val operand1 = Random.nextInt(10, 100)
        val operand2 = Random.nextInt(10, 100)
        val operator = listOf('+', '-', '*', '/').random()

        textFirstOperand.text = operand1.toString()
        textSecondOperand.text = operand2.toString()
        textOperation.text = operator.toString()

        val correctAnswer = when (operator)
        {
            '+' -> operand1 + operand2
            '-' -> operand1 - operand2
            '*' -> operand1 * operand2
            '/' -> operand1.toDouble() / operand2
            else -> 0
        }

        val displayedAnswer = if (Random.nextBoolean())
        { correctAnswers }

        else
        {
            when (operator)
            {
                '+' -> correctAnswers + Random.nextInt(1, 10)
                '-' -> correctAnswers - Random.nextInt(1, 10)
                '*' -> correctAnswers + Random.nextInt(1, 10)
                '/' -> correctAnswers + Random.nextDouble()
                else -> 0
            }
        }

        textResult.text = if (operator == '/')
        {
            String.format("%.2f", displayedAnswer)
        }

        else
        {
            displayedAnswer.toString()
        }

    }

    private fun checkAnswer(b: Boolean)
    {
        val elapsedTime = SystemClock.uptimeMillis() - startTime
        totalTime += elapsedTime
        totalAnswer++

        if (isCorrect)
        {
            correctAnswers++
            textResult.text = "ПРАВИЛЬНО"
        }

        else
        {
            textResult.text = "НЕ ПРАВИЛЬНО"
        }

    }

}

