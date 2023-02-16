package com.example.android.codelabs.navigation

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class ItemDetailFragment : Fragment() {
    var item: Book? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragment = ItemDetailFragment()
        container?.removeAllViews()

        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        item = requireArguments().getParcelable<Book>("book")
        var id = 0;
        if(item==null)
            id = requireArguments().getInt("id");

        val textView = rootView.findViewById<TextView>(R.id.textView12)
        //text.setText("id : " + item.id.toString() + "\n" + "title: "+item.title + "\n" + "author: " + item.author)
        val text = "id: %d\nTitle: %s\nAuthor: %s"
        if(item!=null)
            textView.text = String.format(text, item!!.id, item!!.title, item!!.author)
        else {
            textView.text = String.format("%d", id)
            val dbHelper: DBHelper = DBHelper(context)
            val bookList = dbHelper.getAllBooks()
            var bookF: Book? = null
            for (i in 0 until bookList.size) {
                val book = bookList[i]
                if(book.id == id) {
                    bookF = book;
                }
            }
            if(bookF == null)
            {
                val navOptions = NavOptions.Builder().setPopUpTo(R.id.home_dest, true).build()
                findNavController().navigate(Uri.parse("http://pdm.lab4.ac"),navOptions)
            }
            else
            {
                textView.text = String.format(text,bookF.id,bookF.title, bookF.author)
            }
        }

        return rootView
    }


    companion object {
        fun newInstance(book: Book): ItemDetailFragment {
            val fragment = ItemDetailFragment()
            val args = Bundle()
            args.putParcelable("book", book)
            fragment.arguments = args
            return fragment
        }
    }
}