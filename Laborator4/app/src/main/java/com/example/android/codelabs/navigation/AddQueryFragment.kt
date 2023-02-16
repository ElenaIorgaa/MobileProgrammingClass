package com.example.android.codelabs.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import android.widget.Button


class AddQueryFragment : Fragment() {
    //private val bookViewModel: BookViewModel = BookViewModel(BookRepository(BookDao()))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.add_query, container, false)

        val button: Button = rootView.findViewById(R.id.add_button)
        button.setOnClickListener {

            val title = rootView.findViewById<EditText>(R.id.text_title)
            val author = rootView.findViewById<EditText>(R.id.text_author)
            //wordViewModel.insert(Book(Integer.parseInt(id.text.toString()),title.text.toString(),author.text.toString()))
            val dbHelper: DBHelper = DBHelper(context)
            dbHelper.addNewBook(title.text.toString(), author.text.toString())
            //findViewById(R.id.text_id)

            title.setText(" ")
            author.setText(" ")

        }
        return rootView


    }
}