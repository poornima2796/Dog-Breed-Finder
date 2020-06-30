package com.poorni.breedfinder.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DogObj(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name")val name: String?

)