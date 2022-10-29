package com.example.mynote.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynote.R
import com.example.mynote.databinding.TaskLayoutBinding
import com.example.mynote.register.request.Completed
import com.example.mynote.task.data.Data

class TaskAdapter(private val listener: onItemClickListener) : RecyclerView.Adapter<TaskAdapter.VH>() {
    var model: MutableList<Data> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class VH(val binding: TaskLayoutBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun getItemCount(): Int = model.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_layout, parent, false)
        val binding = TaskLayoutBinding.bind(view)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val task = model[position]

        holder.binding.apply {
            completed.isChecked = task.completed
            completed.isEnabled != task.completed
            description.text = task.description
            description.paint.isStrikeThruText = task.completed
            Log.d("TTT","----> $task     " +
                    " --> ${task.completed}")
            createdAt.text = task.createdAt

            delete.setOnClickListener {
                onClick.invoke(task,model.indexOf(task))
            }

            completed.setOnClickListener {
                onCheckboxClick.invoke(task)
            }

            root.setOnClickListener {
                listener.onItemClick(task)
            }
        }
    }

    interface onItemClickListener {
        fun onItemClick(task: Data)
        fun onCheckBoxClick(task: Data,isChecked: Completed)
    }

    private var onClick: (data: Data, position: Int) -> Unit = { soz, position ->}
    fun removeItemClick(itemClick: (data: Data,position: Int) -> Unit) {
        this.onClick = itemClick
    }

    fun removeItem(position: Int) {
        if (model.size > 0) {
            model.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    private var onCheckboxClick: (data: Data) -> Unit = {}
    fun setOnCheckboxClickListener(onCheckboxClick: (data: Data) -> Unit) {
        this.onCheckboxClick = onCheckboxClick
    }
}
