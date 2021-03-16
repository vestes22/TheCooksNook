package com.codelovely.thecooksnook.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey @ColumnInfo(name="User code") int userCode;
    @ColumnInfo(name="First name") String firstName;
    @ColumnInfo(name="Last name") String lastName;
}
