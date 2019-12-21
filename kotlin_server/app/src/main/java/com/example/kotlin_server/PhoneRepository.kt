package com.example.kotlin_server

import androidx.lifecycle.LiveData

class PhoneRepository(private val phoneDao: PhoneDao) {

    val allPhones: LiveData<List<Phone>> = phoneDao.getPhones()

    suspend fun insert(phone: Phone) {
        phoneDao.insert(phone)
    }

    suspend fun update(phone: Phone) {
        phoneDao.update(phone)
    }

    suspend fun delete(phone: Phone) {
        phoneDao.delete(phone)
    }

    suspend fun deleteLocals(){
        phoneDao.deleteAll()
    }

    suspend fun getAllStatic():List<Phone> {
        return phoneDao.getAllStatic()
    }

    suspend fun getCount(): Int {
        return phoneDao.getCount()
    }
}