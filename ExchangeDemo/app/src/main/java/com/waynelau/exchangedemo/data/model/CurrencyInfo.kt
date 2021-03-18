package com.waynelau.exchangedemo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity (tableName = "currency")
data class CurrencyInfo (
    @PrimaryKey
    var id: String = "",
    var name: String? = "",
    var symbol: String? = ""
): Serializable {
    fun getFirstNameIndex(): String {
        return name?.substring(0,1).orEmpty()
    }
}