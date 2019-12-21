package com.example.kotlin_server

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener  {

    private lateinit var phoneViewModel: PhoneViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        registerReceiver(
            ConnectivityReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        var adapter = PhoneListAdapter(this)

        item_list.adapter = adapter
        item_list.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener {
            showNewDialog()
        }

        phoneViewModel = ViewModelProvider(this)
            .get(PhoneViewModel::class.java)
        phoneViewModel.phones.observe(this, Observer { ts ->
            ts?.let {adapter.setphones(ArrayList(it))}
        })
        adapter.setViewModel(phoneViewModel)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showNewDialog() {
        val dialogBuilder = AlertDialog.Builder(this)

        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.VERTICAL

        val linearLayoutInner1 = LinearLayout(linearLayout.context)
        linearLayout.orientation = LinearLayout.HORIZONTAL

        val linearLayoutInner2 = LinearLayout(linearLayout.context)
        linearLayout.orientation = LinearLayout.HORIZONTAL

        val nameInput = EditText(this@MainActivity)
        val durationInput = EditText(this@MainActivity)
        val nameText = TextView(this@MainActivity)
        val durationText = TextView(this@MainActivity)
        val t = "phone"
        val t2 = "accessory"
        nameText.text = t
        durationText.text = t2

        linearLayoutInner1.addView(nameText)
        linearLayoutInner1.addView(nameInput)
        linearLayoutInner2.addView(durationText)
        linearLayoutInner2.addView(durationInput)
        linearLayout.addView(linearLayoutInner1)
        linearLayout.addView(linearLayoutInner2)

        dialogBuilder.setView(linearLayout)

        dialogBuilder.setTitle("New Activity")
        dialogBuilder.setPositiveButton("Save") { dialog, whichButton ->
            val name = nameInput.text.toString().trim()
            val duration = durationInput.text.toString().trim()
            if (phoneViewModel.isConnected) {
                GlobalScope.launch {
                    val t = Service.service.insert(
                        Phone(
                        name, duration
                    ))
//                    t.set(-1)
                    phoneViewModel.insert(t)
                }
            } else {
                GlobalScope.launch {
                    val id = Service.repository.getCount().toLong() * -1
                    val a = Phone(name, duration)
                    a.set(id)
                    phoneViewModel.insert(a)
                }
            }
//            activityViewModel.insert(Activity(name, duration))
        }
        dialogBuilder.setNegativeButton("Cancel") { dialog, whichButton ->
            //pass
        }
        val b = dialogBuilder.create()
        b.show()
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        this.phoneViewModel.setConnection(isConnected)
    }


}
