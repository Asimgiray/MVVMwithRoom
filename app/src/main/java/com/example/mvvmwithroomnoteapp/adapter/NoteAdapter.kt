package com.example.mvvmwithroomnoteapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mvvmwithroomnoteapp.R
import com.example.mvvmwithroomnoteapp.databinding.RowNoteBinding
import com.example.mvvmwithroomnoteapp.db.Note
import com.example.mvvmwithroomnoteapp.utils.RowClickListener
import com.example.mvvmwithroomnoteapp.utils.Utility
import com.example.mvvmwithroomnoteapp.utils.Utility.Companion.isNullOrEmpty
import com.example.mvvmwithroomnoteapp.utils.dataBindingAdapter

import java.io.Serializable

class NoteAdapter (
    private val noteList: List<Note>,
    private val deleteClickListener : RowClickListener<Serializable>,
    private val noteClickListener: RowClickListener<Serializable>
    )  : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: RowNoteBinding by dataBindingAdapter(R.layout.row_note, inflater, parent)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentNote = noteList[position]

        holder.binding.tvTitle.text = currentNote.noteTitle
        holder.binding.tvDesc.text = currentNote.noteDesc
        holder.binding.tvDate.text = currentNote.noteDate
        Utility.boolToVisibility(holder.binding.tvEdited, currentNote.isNoteEdited)
        if (!isNullOrEmpty(currentNote.noteImage)) {
            holder.binding.imgNote.visibility = View.VISIBLE
            Glide
                .with(holder.binding.root)
                .applyDefaultRequestOptions(object : RequestOptions() {
                    override fun error(resourceId: Int): RequestOptions {
                        return super.error(R.drawable.icon_error_black)
                    }
                })
                .load(currentNote.noteImage)
                .fitCenter()
                .into(holder.binding.imgNote)
        }

        holder.binding.imgDelete.setOnClickListener { deleteClickListener.onRowClick(position, currentNote) }
        holder.binding.root.setOnClickListener { noteClickListener.onRowClick(position, currentNote) }

    }

    override fun getItemCount() = noteList.count()

    class ViewHolder(val binding: RowNoteBinding) : RecyclerView.ViewHolder(binding.root)

}