package com.farhanadi.suitmediamobileapptest.third.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farhanadi.suitmediamobileapptest.R
import com.farhanadi.suitmediamobileapptest.data.DataClass.User

class UserAdapter(private val users: List<User>, private val onItemClick: (User) -> Unit) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tableuser, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position], onItemClick)
    }

    override fun getItemCount(): Int = users.size

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userName: TextView = itemView.findViewById(R.id.tv_username_third)
        private val userEmail: TextView = itemView.findViewById(R.id.tvuser_email)
        private val userAvatar: ImageView = itemView.findViewById(R.id.usericon_profile)

        fun bind(user: User, onItemClick: (User) -> Unit) {
            userName.text = "${user.first_name} ${user.last_name}"
            userEmail.text = user.email

            Glide.with(itemView.context)
                .load(user.avatar)
                .into(userAvatar)

            itemView.setOnClickListener {
                onItemClick(user)
            }
        }
    }
}