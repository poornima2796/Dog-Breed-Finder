package com.poorni.breedfinder.Model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AppDAO {


    @Query("SELECT COUNT(*) FROM dogobj")
    fun countDogs(): Int

    @Insert
    fun insertDogObj(dogObj: DogObj)

    @Query("SELECT * FROM dogobj")
    fun allDogs(): List<DogObj>

    @Query("SELECT * FROM dogobj WHERE id = :id LIMIT 1")
    fun findRandomDog(id: Int): DogObj?
}