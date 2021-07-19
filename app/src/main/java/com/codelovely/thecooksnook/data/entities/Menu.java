package com.codelovely.thecooksnook.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity (
        tableName="menus",
        foreignKeys = {@ForeignKey(entity = User.class, parentColumns = "User code", childColumns = "User code")}
)
public class Menu {
    private @PrimaryKey(autoGenerate=true) @ColumnInfo(name="Menu code") int menuId;
    @NonNull private @ColumnInfo(name="User code") String userId;
    private @ColumnInfo(name="Date created") LocalDate dateCreated;

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public void setUserId(String userId) { this.userId = userId; }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getMenuId() {
        return menuId;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }
}
