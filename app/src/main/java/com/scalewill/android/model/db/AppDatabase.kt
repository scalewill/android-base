package com.scalewill.android.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.scalewill.android.model.db.daos.ProfileDao
import com.scalewill.android.model.db.daos.SessionDao
import com.scalewill.android.model.entities.Session
import com.scalewill.android.profile.entities.Profile

@Database(
    entities = [Profile::class, Session::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun sessionDao(): SessionDao
}
