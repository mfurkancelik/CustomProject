package com.mffid.customproject.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mffid.customproject.databinding.RowLayoutBinding
import com.mffid.customproject.model.PostModel

class RecyclerViewAdapter(private val postList : ArrayList<PostModel>, private val listener : Listener): RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {

    interface Listener {
        fun onItemClick(postModel: PostModel)
    }

    private val colors: Array<String> = arrayOf("#7e5e62","#236dc9","#446862","#e2ceb8","#894160","#286a6c","#d1b9ed","#d3dfe1")

    class RowHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RowHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.count()
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listener.onItemClick(postList.get(position))
        }
        holder.itemView.setBackgroundColor(Color.parseColor(colors[position % 8]))

        holder.binding.textName.text = postList.get(position).title
        holder.binding.textPrice.text = postList.get(position).body




    }
}