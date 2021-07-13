package com.example.concurrentnetworkcall.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.concurrentnetworkcall.R
import com.example.concurrentnetworkcall.databinding.ItemListBinding
import com.example.concurrentnetworkcall.models.User

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private val items: MutableList<User> = mutableListOf()

    fun populateData(data: List<User>) {
        items.addAll(data)
        notifyDataSetChanged()
    }

    fun clearList() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
    )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = items[position]

        with(holder) {
            with(binding) {
                tvName.text = item.name

                Glide.with(itemView.context)
                    .load(item.imageUrl)
                    .circleCrop()
                    .into(ivProfile)
            }
        }
    }

    override fun getItemCount() = items.size

    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemListBinding.bind(view)
    }
}