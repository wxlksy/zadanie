package com.example.proverka

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import com.example.proverka.databinding.ActivityMainBinding
import kotlin.random.Random
import kotlin.Boolean

class MainActivity : AppCompatActivity()
{
    private var startTime: Long = 0
    private var correctAnswers = 0
    private var totalAnswer = 0
    private var maxTime = Long.MAX_VALUE
    private var minTime = Long.MIN_VALUE
    private var totalTime = 0L

    private lateinit var  binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startButton.setOnClickListener {
            generateExample()
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
            startTime = SystemClock.uptimeMillis()
        }

        binding.trueButton.setOnClickListener {
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener {
            checkAnswer(false)
        }
    }

    private fun generateExample()
    {
        val operand1 = Random.nextInt(10, 100)
        val operand2 = Random.nextInt(10, 100)
        val operator = listOf('+', '-', '*', '/').random()

        binding.textFirstOperand.text = operand1.toString()
        binding.textSecondOperand.text = operand2.toString()
        binding.textOperation.text = operator.toString()

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

        binding.textResult.text = if (operator == '/')
        {
            String.format("%.2f", displayedAnswer)
        }

        else
        {
            displayedAnswer.toString()
        }

    }

    private fun checkAnswer(isCorrect: Boolean)
    {
        val elapsedTime = SystemClock.uptimeMillis() - startTime
        totalTime += elapsedTime
        totalAnswer++

        if (isCorrect)
        {
            correctAnswers++
            binding.textResult.text = "ПРАВИЛЬНО"
        }

        else
        {
            binding.textResult.text = "НЕ ПРАВИЛЬНО"
        }

        minTime = minOf(minTime, elapsedTime)
        maxTime = maxOf(maxTime, elapsedTime)

        binding.textViewTime.text = "Время = $elapsedTime ms"
        binding.textViewTrue.text = "Правильных ответов = $correctAnswers"
        binding.textViewFalse.text = "Неправильных ответов ${totalAnswer - correctAnswers}"
        binding.textViewPercentage.text = "Процент правильных = ${String.format("%.2f", (correctAnswers.toDouble() / totalAnswer * 100))}%"
        binding.textViewTimeMax.text = "Максимальное время: $maxTime ms"
        binding.textViewTimeMin.text = "Минимальное время: $minTime ms"
        binding.textViewTimeAvg.text = "Среднее время: ${String.format("%.2f", totalTime.toDouble() / totalAnswer)} ms"

        binding.trueButton.isEnabled = false
        binding.falseButton.isEnabled = false

    }
}


