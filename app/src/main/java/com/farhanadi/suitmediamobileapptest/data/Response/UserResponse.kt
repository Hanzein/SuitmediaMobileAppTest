package com.farhanadi.suitmediamobileapptest.data.Response

import com.farhanadi.suitmediamobileapptest.data.DataClass.User

data class UserResponse(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: List<User>
)