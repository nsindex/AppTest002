package com.example.apptest002

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ItemDao {
    @Insert
    suspend fun insert(item: Item)

    @Query("SELECT * FROM items WHERE id = :id")
    suspend fun getItemById(id: Int): Item?

    @Query("SELECT * FROM items")
    suspend fun getAllItems(): List<Item>
}
