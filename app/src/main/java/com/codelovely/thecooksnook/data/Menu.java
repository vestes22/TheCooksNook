package com.codelovely.thecooksnook.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Date;

@Entity (
        tableName="menus",
        foreignKeys = {@ForeignKey(entity = User.class, parentColumns = "User code", childColumns = "User code")}
)
public class Menu {
    @PrimaryKey @ColumnInfo(name="Menu code") int menuCode;
    @ColumnInfo(name="User code") int userCode;
    @ColumnInfo(name="Date created") Date dateCreated;
    @ColumnInfo(name="Title") String title;
}
