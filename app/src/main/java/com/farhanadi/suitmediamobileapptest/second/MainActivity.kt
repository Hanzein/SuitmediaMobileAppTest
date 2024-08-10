package com.farhanadi.suitmediamobileapptest.second

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.farhanadi.suitmediamobileapptest.R
import com.farhanadi.suitmediamobileapptest.third.Third_Activity

class MainActivity : AppCompatActivity() {

    private lateinit var tvSelectedUser: TextView
    private lateinit var chooseUserLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val backBtn = findViewById<ImageView>(R.id.btn_back_second)
        val tvUsername = findViewById<TextView>(R.id.tv_username_second)
        tvSelectedUser = findViewById(R.id.tv_selectuser)
        val chooseButton = findViewById<Button>(R.id.Choose_btn)

        // Mengambil nama dari EditView Name yang diteruskan dari First_Activity
        val userName = intent.getStringExtra("USER_NAME")
        tvUsername.text = userName

        // Set up click listener for the back button
        backBtn.setOnClickListener {
            finish()
        }

        // Register for activity result
        chooseUserLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val selectedUserName = result.data?.getStringExtra("SELECTED_USER_NAME")
                tvSelectedUser.text = selectedUserName
            }
        }

        // Set up click listener for Choose a User button
        chooseButton.setOnClickListener {
            val intent = Intent(this, Third_Activity::class.java)
            chooseUserLauncher.launch(intent)
        }
    }
}
