package com.codelovely.thecooksnook.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "derivDesc")

public class DerivDesc {
    private @PrimaryKey @NonNull @ColumnInfo(name="Derivation code") String derivationCode;
    private @ColumnInfo(name="Derivation description") String derivationDesc;

    public void setDerivationCode(String derivationCode) {
        this.derivationCode = derivationCode;
    }

    public void setDerivationDesc(String derivationDesc) {
        this.derivationDesc = derivationDesc;
    }

    public String getDerivationCode() {
        return derivationCode;
    }

    public String getDerivationDesc() {
        return derivationDesc;
    }
}
