package com.example.bricklist

import androidx.room.*


@Entity(tableName = "Colors")
data class DbColors (
    @ColumnInfo(name = "NamePL") var NamePL: String?,
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "Code") var Code: Int,
    @ColumnInfo(name = "Name") var Name: String
)


@Dao
interface MyDao{
    @Query("select name from Colors")
    fun loadColors(): List<String>
}