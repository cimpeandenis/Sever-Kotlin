package com.example.kotlin_server

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_list_content.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PhoneListAdapter(val context: Context):
    RecyclerView.Adapter<PhoneListAdapter.PhoneViewHolder>(){

    private var phones: ArrayList<Phone> = ArrayList()

    private var phoneViewModel: PhoneViewModel? = null

    inner class PhoneViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val idView: TextView = view.id_text
        val nameView: TextView = view.name
        val durationView: TextView = view.duration
        val editButton: TextView = view.updateBtn
        val deleteButton: TextView = view.deleteBtn

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewHolder{
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_content, parent, false)
        return PhoneViewHolder((view))
    }

    override fun getItemCount(): Int {
        return phones.size
    }

    override fun onBindViewHolder(holder: PhoneViewHolder, position: Int) {
        val item = phones[position]
        holder.idView.text = item.id.toString()
        holder.nameView.text = item.phone
        holder.durationView.text = item.accessory

//        holder.editButton.setOnClickListener { showUpdateDialog(holder, phones[position]) }
        holder.editButton.setOnClickListener {
            if (phoneViewModel!!.isConnected) {
                showUpdateDialog(holder, item)
            } else {
                Toast.makeText(
                    context,
                    "Update operation is not available offline!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        holder.deleteButton.setOnClickListener {
//            PhoneViewModel.delete(item)
            if (phoneViewModel!!.isConnected) {
                deletePhone(phones[position])
            } else {
                Toast.makeText(context,"Delete operation is not available offline!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showUpdateDialog(holder: PhoneListAdapter.PhoneViewHolder, Phone: Phone) {
        val dialogBuilder = AlertDialog.Builder(holder.itemView.context)
        val linearLayout = LinearLayout(holder.itemView.context)
        linearLayout.orientation = LinearLayout.VERTICAL
        val input = EditText(holder.itemView.context)
        val input2 = EditText(holder.itemView.context)

        input.setText(Phone.phone)
        input2.setText(Phone.accessory)
        linearLayout.addView(input)
        linearLayout.addView(input2)



        dialogBuilder.setView(linearLayout)

        dialogBuilder.setTitle("Update Phone")
        dialogBuilder.setPositiveButton("Update") { _, _ ->
            val newPhone = Phone(input.text.toString(), input2.text.toString())
            newPhone.set(Phone.id)
//            PhoneViewModel!!.update(newPhone)
            updatePhone(newPhone)
        }
        dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        val b = dialogBuilder.create()
        b.show()
    }

    private fun deletePhone(Phone: Phone) {
        GlobalScope.launch {
            Service.service.delete(Phone.id.toString())
            phoneViewModel?.delete(Phone)
        }
    }

    private fun updatePhone(Phone: Phone) {
        GlobalScope.launch {
            val aServer = Service.service.update(Phone.id.toString(), Phone)
            phoneViewModel?.update(Phone)
        }
        notifyDataSetChanged()
    }

    fun setphones(PhoneList: ArrayList<Phone>) {
        this.phones= PhoneList
        notifyDataSetChanged()
    }

    fun setViewModel(tViewModel: PhoneViewModel) {
        this.phoneViewModel= tViewModel
    }

}