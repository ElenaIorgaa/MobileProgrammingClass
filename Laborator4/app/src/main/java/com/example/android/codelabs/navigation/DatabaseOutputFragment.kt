package com.example.android.codelabs.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import java.lang.reflect.Array.newInstance
import java.net.URLClassLoader.newInstance
import javax.xml.parsers.DocumentBuilderFactory.newInstance

class DatabaseOutputFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.removeAllViews()
        val root = inflater.inflate(R.layout.database, container, false)

        val dbHelper: DBHelper = DBHelper(context)
        val listView = root.findViewById<ListView>(R.id.recipe_list_view)

        val bookList = dbHelper.getAllBooks()
        val listItems = arrayOfNulls<String>(bookList.size)
        for (i in 0 until bookList.size) {
            val book = bookList[i]
            listItems[i] = "Title: " + book.title + "\n" + "Author: " + book.author
        }
        val adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, listItems) }
        listView.adapter = adapter

        listView.setOnItemClickListener{parent,view,position,id ->
            //Navigation.findNavController(view).navigate(R.id.action_database_to_item_detail2);
           // Navigation.createNavigateOnClickListener(R.id.item_detail)
            //findNavController().navigate(R.id.action_database_to_item_detail2,null)
            //view.isVisible = false
//            findNavController().navigate(R.id.item_detail,null)
//            root =  inflater.inflate(R.layout.item_detail, container, false)
            //view.isVisible = false
            val book : Book = bookList[position]



            val item = parent.getItemAtPosition(position) as String
            // Create an instance of the fragment you want to navigate to
            val fragment = ItemDetailFragment.newInstance(book)

            // Get the FragmentManager and begin the FragmentTransaction
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            val args = Bundle()
            args.putParcelable("book", book)
            val deeplink = findNavController().createDeepLink()
                .setDestination(R.id.item_detail)
                .setArguments(args)
                .createPendingIntent()

            // Replace the contents of the container with the new fragment
            findNavController().navigate(R.id.item_detail,args)
            //fragmentTransaction.replace(R.id.db, fragment)

            // Add the fragment to the 'back stack'
            fragmentTransaction.addToBackStack(null)

            // Commit the FragmentTransaction
            fragmentTransaction.commit()

        }
        return root
    }
}