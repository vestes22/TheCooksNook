package com.codelovely.thecooksnook.data.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Date;

@Entity(
        tableName = "shoppingLists",
        foreignKeys = {@ForeignKey(entity=User.class, parentColumns = "User code", childColumns = "User code")}
)
public class ShoppingList {
    private @PrimaryKey(autoGenerate=true) @ColumnInfo(name="Shopping list code") int shoppingListId;
    @NonNull private @ColumnInfo(name="User code") String userId;
    private @ColumnInfo(name="Date created") LocalDate dateCreated;

    public void setShoppingListId(int shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getShoppingListId() {
        return shoppingListId;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

}
