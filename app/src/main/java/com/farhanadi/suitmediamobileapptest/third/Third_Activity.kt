package com.farhanadi.suitmediamobileapptest.third

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farhanadi.suitmediamobileapptest.R
import com.farhanadi.suitmediamobileapptest.third.Adapter.UserAdapter
import com.farhanadi.suitmediamobileapptest.data.DataClass.User
import com.farhanadi.suitmediamobileapptest.data.Response.UserResponse
import com.farhanadi.suitmediamobileapptest.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Third_Activity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private val users = mutableListOf<User>()
    private var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        recyclerView = findViewById(R.id.rv_tableuser)
        recyclerView.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter(users) { user ->
            onUserSelected(user)
        }
        recyclerView.adapter = userAdapter

        // Ambil data awal
        fetchUsers()

        // Set up pull-to-refresh and pagination
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1)) {
                    // Load halaman selanjutnya
                    fetchUsers()
                }
            }
        })

        // Set up back button
        findViewById<ImageView>(R.id.btn_back_third).setOnClickListener {
            onBackPressed()
        }
    }

    private fun fetchUsers() {
        val apiService = ApiConfig.provideApiService()
        val call = apiService.getUsers(currentPage, 10) // Fetch 10 users per page

        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                response.body()?.let {
                    if (it.data.isNotEmpty()) {
                        users.addAll(it.data)
                        userAdapter.notifyDataSetChanged()
                        currentPage++
                    } else {
                        showEmptyState()
                    }
                } ?: run {
                    showEmptyState()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(this@Third_Activity, "Failed to load data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun onUserSelected(user: User) {
        val resultIntent = Intent().apply {
            putExtra("SELECTED_USER_NAME", "${user.first_name} ${user.last_name}")
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    private fun showEmptyState() {
        // Fungsi untuk menangani situasi ketika tidak ada lagi pengguna yang dapat dimuat dari API.
        Toast.makeText(this, "No more users available", Toast.LENGTH_SHORT).show()
    }
}
