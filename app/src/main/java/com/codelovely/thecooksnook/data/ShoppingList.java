package com.codelovely.thecooksnook.data;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(
        tableName = "shoppingLists",
        foreignKeys = {@ForeignKey(entity=User.class, parentColumns = "User code", childColumns = "User code")}
)
public class ShoppingList {
    private @PrimaryKey(autoGenerate=true) @ColumnInfo(name="Shopping list code") int shoppingListId;
    private @ColumnInfo(name="User code") int userId;
    private @ColumnInfo(name="Date created") Date dateCreated;
    private @ColumnInfo(name="Title") String title;

    public void setShoppingListId(int shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getShoppingListId() {
        return shoppingListId;
    }

    public int getUserId() {
        return userId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public String getTitle() {
        return title;
    }
}
