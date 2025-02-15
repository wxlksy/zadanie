package com.example.proverka

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import com.example.proverka.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var startTime: Long = 0
    private var correctAnswers = 0
    private var totalAnswers = 0
    private var maxTime: Long = 0
    private var minTime: Long = Long.MAX_VALUE
    private var totalTime: Long = 0

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
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

    private fun generateExample() {
        val operand1 = Random.nextInt(10, 100)
        val operand2 = Random.nextInt(10, 100)
        val operator = listOf('+', '-', '*', '/').random()

        binding.textFirstOperand.text = operand1.toString()
        binding.textSecondOperand.text = operand2.toString()
        binding.textOperation.text = operator.toString()

        val correctAnswer = when (operator) {
            '+' -> operand1 + operand2
            '-' -> operand1 - operand2
            '*' -> operand1 * operand2
            '/' -> operand1.toDouble() / operand2
            else -> 0
        }

        val displayedAnswer = if (Random.nextBoolean()) {
            correctAnswer
        } else {
            when (operator)
            {
                '+' -> correctAnswers + Random.nextInt(1, 10)
                '-' -> correctAnswers - Random.nextInt(1, 10)
                '*' -> correctAnswers + Random.nextInt(1, 10)
                '/' -> correctAnswers + Random.nextDouble()
                else -> 0
            }
        }

        binding.textResult.text = if (operator == '/') {
            String.format("%.2f", displayedAnswer)
        } else {
            displayedAnswer.toString()
        }
    }

    private fun checkAnswer(isCorrect: Boolean) {
        val elapsedTime = (SystemClock.uptimeMillis() - startTime) / 1000.0
        totalTime += elapsedTime.toLong()
        totalAnswers++

        if (isCorrect) { correctAnswers++ } else { }

        if (totalAnswers == 1 || elapsedTime.toLong() > maxTime) {
            maxTime = elapsedTime.toLong()
        }
        if (totalAnswers == 1 || elapsedTime.toLong() < minTime) {
            minTime = elapsedTime.toLong()
        }

        binding.textViewTimeMax.text = "Максимальное время: %.2f сек".format(maxTime.toDouble())
        binding.textViewTimeMin.text = "Минимальное время: %.2f сек".format(minTime.toDouble())
        binding.textViewTimeAvg.text = "Среднее время: %.2f сек".format(totalTime.toDouble() / totalAnswers)

        binding.textViewTrue.text = "Правильных ответов = $correctAnswers"
        binding.textViewFalse.text = "Неправильных ответов = ${totalAnswers - correctAnswers}"
        binding.textViewPercentage.text = "Процент правильных = %.2f%%".format(correctAnswers.toDouble() / totalAnswers * 100)

        binding.trueButton.isEnabled = false
        binding.falseButton.isEnabled = false
    }
}
