package com.example.googlebookstest.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.googlebookstest.R
import com.example.googlebookstest.model.VolumeInfoElement

class BookAdapter(private val dataSet: List<VolumeInfoElement>): RecyclerView.Adapter<BookAdapter.BookViewHolder>() {


    class BookViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        private val tvTitle: TextView = view.findViewById(R.id.book_title)
        private val tvAuthors: TextView = view.findViewById(R.id.book_authors)


        fun onBind(bookElement:VolumeInfoElement){
            tvTitle.text = bookElement.title
            tvAuthors.text = bookElement.authors.toString()
        }
    }

    //Create the viewHolder used in this adapter
    //@param viewType idf you wan t to render multiple viewHolder
    //@param parent represent the RecyclerView layout xml file
    // Always use "attachToRoot = false" to avoid rendering now and avoid crashes
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BookViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.book_item_layout, parent, false)
        )

    //Connect the data withe view reference.
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.onBind(dataSet[position])
    }

    //Display as N element from the list.
    override fun getItemCount(): Int {
        return dataSet.size
    }


}