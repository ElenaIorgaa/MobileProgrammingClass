/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.codelabs.navigation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions

/**
 * Fragment used to show how to navigate to another destination
 */
class HomeFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO STEP 5 - Set an OnClickListener, using Navigation.createNavigateOnClickListener()
//        val button = view.findViewById<Button>(R.id.navigate_destination_button)
//        button?.setOnClickListener {
//            findNavController().navigate(R.id.flow_step_one_dest, null)
//        }
        //TODO END STEP 5

        //TODO STEP 6 - Set NavOptions
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }
        view.findViewById<Button>(R.id.navigate_destination_button)?.setOnClickListener {
            findNavController().navigate(R.id.flow_step_one_dest, null, options)
        }
        // Note the usage of curly braces since we are defining the click listener lambda
        view.findViewById<Button>(R.id.navigate_action_button)?.setOnClickListener {
            val flowStepNumberArg = 1
            val action = HomeFragmentDirections.nextAction(flowStepNumberArg)
            findNavController().navigate(action)
        }
        view.findViewById<Button>(R.id.database)?.setOnClickListener {
            findNavController().navigate(R.id.database,null,options)
        }
        //TODO END STEP 6

        //TODO STEP 7.2 - Update the OnClickListener to navigate using an action
        view.findViewById<Button>(R.id.navigate_action_button)?.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.next_action, null)
        )
        //TODO END STEP 7.2
        view.findViewById<Button>(R.id.button)?.setOnClickListener {
            val text: TextView = view.findViewById(R.id.editLink)
            val url: String = text.text.toString()
            val dbHelper: DBHelper = DBHelper(context)
            val books = dbHelper.getAllBooks()

            val navOptions = NavOptions.Builder().setPopUpTo(R.id.home_dest, true).build()
            findNavController().navigate(Uri.parse(url),navOptions)
                    }
                }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }
}
