package com.example.googlebookstest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.googlebookstest.R
import com.example.googlebookstest.model.BookResponse
import com.example.googlebookstest.model.VolumeInfoElement

class DisplayFragment: Fragment() {

    private lateinit var bookList: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(
            R.layout.display_book_fragment,
            container,
            false
        )
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        bookList = view.findViewById(R.id.book_list)
        val linearLayoutManager = LinearLayoutManager(context)
        val gridLayoutManager = GridLayoutManager(context, 3)
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL)
        bookList.layoutManager = staggeredGridLayoutManager

        arguments?.getParcelable<BookResponse>("BookResponse")?.let {
            it.items?.let { bookElement ->
                val result = bookElement.map { element -> element.volumeInfo!! }

                bookList.adapter = BookAdapter(result)
            }
        }
    }

    companion object{
        fun newInstance(bookResponse: BookResponse): DisplayFragment{
            val fragment = DisplayFragment()
            fragment.arguments = Bundle().apply {
                putParcelable("BookResponse", bookResponse)
            }
            return fragment
        }
    }
}