package com.farhanadi.suitmediamobileapptest.first

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.farhanadi.suitmediamobileapptest.R
import com.farhanadi.suitmediamobileapptest.second.MainActivity

class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        val etName = findViewById<EditText>(R.id.et_name_first)
        val etPalindrome = findViewById<EditText>(R.id.et_palindrom_first)
        val checkButton = findViewById<Button>(R.id.checkButton)
        val nextButton = findViewById<Button>(R.id.nextButton)

        checkButton.setOnClickListener {
            val inputText = etPalindrome.text.toString()

            if (inputText.isEmpty()) {
                // Memunculkan Toast Message ketika Field EditView Palindrome kosong
                Toast.makeText(this, "Please fill the palindrome field", Toast.LENGTH_SHORT).show()
            } else {
                val result = if (isPalindrome(inputText)) "isPalindrome" else "not palindrome"

                val dialog = AlertDialog.Builder(this)
                    .setMessage(result)
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()

                dialog.show()
            }
        }

        nextButton.setOnClickListener {
            val userName = etName.text.toString()
            if (userName.isEmpty()) {
                // Memunculkan Toast Message ketika Field EditView Name kosong
                Toast.makeText(this, "Please fill the name field", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("USER_NAME", userName) // Mengirimkan input-an nama dari First Screen untuk mengisi textview username di Second Screen
                }
                startActivity(intent)
            }
        }
    }

    private fun isPalindrome(text: String): Boolean {
        val cleanedText = text.replace("\\s".toRegex(), "")
        return cleanedText.equals(cleanedText.reversed(), ignoreCase = true)
    }
}
