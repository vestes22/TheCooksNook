package com.codelovely.thecooksnook.data;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(
        tableName = "shoppingLists",
        foreignKeys = {@ForeignKey(entity=User.class, parentColumns = "User code", childColumns = "User code")}
)
public class ShoppingList {
    @PrimaryKey @ColumnInfo(name="Shopping list code") int shoppingCode;
    @ColumnInfo(name="User code") int userCode;
    @ColumnInfo(name="Date created") LocalDate dateCreated;
}
