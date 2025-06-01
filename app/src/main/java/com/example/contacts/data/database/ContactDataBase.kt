package com.example.contacts.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactDataBase: RoomDatabase() {
    abstract fun getDao(): Dao
}