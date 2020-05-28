package com.example.bricklist

import android.graphics.Bitmap
import androidx.room.*


@Entity(tableName = "Colors")
data class DbColors (
    @ColumnInfo(name = "NamePL") var NamePL: String?,
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "Code") var Code: Int,
    @ColumnInfo(name = "Name") var Name: String
)

@Entity(tableName = "Codes")
data class DbCodes (
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "ItemID") var ItemID: Int,
    @ColumnInfo(name = "ColorID") var ColorID: Int?,
    @ColumnInfo(name = "Code") var Code: Int?,
    @ColumnInfo(name = "Image") var Image: ByteArray?
)

@Entity(tableName = "Parts")
data class DbParts (
    @ColumnInfo(name = "NamePL") var NamePL: String?,
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "Code") var Code: String,
    @ColumnInfo(name = "Name") var Name: String,
    @ColumnInfo(name = "TypeID") var TypeID: Int,
    @ColumnInfo(name = "CategoryID") var CategoryID: Int
)

@Entity(tableName = "ItemTypes")
data class DbItemTypes (
    @ColumnInfo(name = "NamePL") var NamePL: String?,
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "Code") var Code: String,
    @ColumnInfo(name = "Name") var Name: String
)

@Entity(tableName = "Inventories")
data class DbInventories (
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "Active") var Active: Int,
    @ColumnInfo(name = "LastAccessed") var LastAccessed: Int,
    @ColumnInfo(name = "Name") var Name: String
)

@Entity(tableName = "InventoriesParts")
data class DbInventoriesParts (
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "InventoryID") var InventoryID: Int,
    @ColumnInfo(name = "TypeID") var TypeID: Int,
    @ColumnInfo(name = "ItemID") var ItemID: Int,
    @ColumnInfo(name = "QuantityInSet") var QuantityInSet: Int,
    @ColumnInfo(name = "QuantityInStore") var QuantityInStore: Int,
    @ColumnInfo(name = "ColorID") var ColorID: Int,
    @ColumnInfo(name = "Extra") var Extra: Int
)

@Dao
interface MyDao{
    @Query("select name from Colors")
    fun loadColors(): List<String>

    @Query("select name from ItemTypes")
    fun loadItems(): List<String>

    @Query("select name from Inventories")
    fun getInvNames(): List<String>

    @Insert
    fun insertInventory(inventory: DbInventories)
}
