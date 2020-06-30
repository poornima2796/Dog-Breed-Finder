package com.poorni.breedfinder.Model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DogObj::class], version = 2, exportSchema = false)
abstract class DB : RoomDatabase() {
    abstract fun AppDAO(): AppDAO?

    companion object {
        @Volatile
        private var INSTANCE: DB? = null

        fun getDatabase(context: Context): DB? {
            if (INSTANCE == null) {
                synchronized(DB::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            DB::class.java, "db_doglisting"
                        )
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}