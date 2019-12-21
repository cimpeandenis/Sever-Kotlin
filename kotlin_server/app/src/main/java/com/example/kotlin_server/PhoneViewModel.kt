package com.example.kotlin_server

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PhoneViewModel(application: Application): AndroidViewModel(application) {
    private val repository: PhoneRepository
    val phones: LiveData<List<Phone>>
    var isConnected: Boolean = false
    init {
        val phonesDAO = PhoneRoomDatabase.getDatabase(application, viewModelScope).phoneDao()
        repository = PhoneRepository(phonesDAO)
        phones = repository.allPhones
        Service.repository = repository
    }
    fun insert(phone: Phone) = viewModelScope.launch {
        repository.insert(phone)
    }



    fun delete(phone: Phone) = viewModelScope.launch {
        repository.delete(phone)
    }

    fun update(phone: Phone) = viewModelScope.launch {
        repository.update(phone)
    }

    fun setConnection(connected: Boolean) {
        isConnected = connected
        if (isConnected) {
            viewModelScope.launch { sync() }
        }
    }

    private fun sync() {
        GlobalScope.launch {
            val tsDB: List<Phone> = Service.repository.getAllStatic()

            Log.d("SYNC", tsDB.toString())

            for(t in tsDB){
                if(t.id < 0){
                    Service.service.insert(t)
                }
            }

            Service.repository.deleteLocals()
            val tsServer: List<Phone> = Service.service.getItems()
            for (tServer in tsServer) {
                Service.repository.insert(tServer)
            }
        }
    }
}