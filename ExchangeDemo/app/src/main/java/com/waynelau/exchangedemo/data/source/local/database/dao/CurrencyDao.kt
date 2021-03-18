package com.waynelau.exchangedemo.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.waynelau.exchangedemo.data.model.CurrencyInfo

@Dao
interface CurrencyDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertCurrencyInfo(data: List<CurrencyInfo>)

    @Query("""
        SELECT * FROM currency ORDER BY 
        CASE WHEN :isAsc = 1 THEN name END ASC, 
        CASE WHEN :isAsc = 0 THEN name END DESC
        """)
    suspend fun getCurrencyInfo(isAsc: Boolean): List<CurrencyInfo>

    @Query( """
        SELECT * FROM currency 
        WHERE name LIKE '%' || :keyword || '%' 
        OR symbol LIKE '%' || :keyword || '%'
        """)
    suspend fun getCurrencyInfoByKeyword(keyword: String?): List<CurrencyInfo>
}