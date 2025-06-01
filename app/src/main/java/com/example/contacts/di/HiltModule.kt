package com.example.contacts.di

import android.content.Context
import androidx.room.Room
import com.example.contacts.data.ContactDao
import com.example.contacts.data.ContactDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    @Singleton
    fun provideContactDatabase(
        @ApplicationContext context: Context
    ): ContactDatabase {
        return Room.databaseBuilder(
            context,
            ContactDatabase::class.java,
            "contact_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideContactDao(database: ContactDatabase): ContactDao {
        return database.contactDao()
    }
} 