package com.seanutf.android.mvi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seanutf.android.databinding.ItemLayoutMviBinding
import com.seanutf.android.mvi.data.model.User

class MainAdapter(
        private val users: ArrayList<User>
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(private val itemVb: ItemLayoutMviBinding) : RecyclerView.ViewHolder(itemVb.root) {
        fun bind(user: User) {
            itemVb.textViewUserName.text = user.name
            itemVb.textViewUserEmail.text = user.email
            Glide.with(itemVb.imageViewAvatar.context)
                    .load(user.avatar)
                    .into(itemVb.imageViewAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            DataViewHolder(ItemLayoutMviBinding.inflate(LayoutInflater.from(parent.context),
                    parent, false))

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
            holder.bind(users[position])

    fun addData(list: List<User>) {
        users.addAll(list)
    }
}