package com.example.bricklist

import android.graphics.Bitmap
import androidx.room.*
import java.sql.Blob


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
    //Blok wypisu invParts po ilości zebranych elementów
    @Query("select * from InventoriesParts where InventoryID= :invId order by QuantityInSet-QuantityInStore desc")
    fun getInvParts(invId: Int): List<DbInventoriesParts>


    @Query("select id from Parts where code= :code")
    fun getItemID(code: String): List<Int>



    @Query("select id from ItemTypes where code= :code")
    fun getTypeID(code: String): List<Int>

    @Query("select code from ItemTypes where id= :id")
    fun getTypeCode(id: Int): String?



    @Query("select id from Colors where code= :code")
    fun getColorID(code: Int): List<Int>

    @Query("select max(id) from InventoriesParts")
    fun getMaxInvPartId(): Int?

    @Query("select name from Parts where id= :id")
    fun getName(id: Int): String?

    @Query("select name from Colors where id= :id")
    fun getColor(id: Int): String?

    @Query("select code from Colors where id= :id")
    fun getColorNumber(id: Int): Int?



    @Query ("select count(*) from Codes where ItemID= :itemID and ColorID= :colorID")
    fun existCode(itemID: Int, colorID: Int): Int

    @Query ("insert into Codes(id, itemID, colorID) values(:id, :itemID, :colorID)")
    fun createCode(id: Int, itemID: Int, colorID: Int)

    @Query("select max(id) from codes")
    fun getMaxCode(): Int



    @Query("select code from Parts where id= :id")
    fun getCode(id: Int): String?

    @Query("update InventoriesParts set QuantityInStore= :amount where id= :id")
    fun updateStore(amount: Int, id: Int)

    @Query("select code from codes where ColorID= :colorID and ItemID= :itemID")
    fun getCode(colorID: Int, itemID: Int): Int?

    @Query("select image from codes where ColorID= :colorID and ItemID= :itemID")
    fun getImage(colorID: Int, itemID: Int): ByteArray?
    @Query("update codes set image= :img where ColorID= :ColorID and ItemID= :ItemID")
    fun setImage(ColorID: Int, ItemID: Int, img: ByteArray)


    @Query("update inventories set active= :value where id= :id")
    fun setArchivize(id: Int, value: Int=1)
    @Query("select active from inventories where id= :id")
    fun getArchive(id: Int): Int

    //Blok wczytywania listy projektów i czasu odczytu
    @Query("select * from Inventories where active>= :value order by lastAccessed desc")
    fun getInvNames(value: Int=2): List<DbInventories>
    @Query ("update inventories set lastAccessed= :time where id= :id")
    fun changeProjectTime(id: Int, time: Int)
    @Query ("select max(lastAccessed) from inventories")
    fun getMaxTime(): Int


    //Blok standardowego zapisu
    @Insert
    fun insertInventory(inventory: DbInventories)
    @Insert
    fun insertInventoryPart(inventoryPart: DbInventoriesParts)
}
