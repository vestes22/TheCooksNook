package com.codelovely.thecooksnook.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "derivDesc")

public class DerivDesc {
    @PrimaryKey @NonNull @ColumnInfo(name="Derivation code") String derivationCode;
    @ColumnInfo(name="Derivation description") String derivationDesc;
}
