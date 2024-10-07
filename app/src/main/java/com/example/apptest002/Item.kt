package com.example.apptest002

// ここにインポート文を追加します
import androidx.room.Entity
import androidx.room.PrimaryKey

// データクラスを定義
@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val expirationDate: Long,
    val addedDate: Long = System.currentTimeMillis()
)
