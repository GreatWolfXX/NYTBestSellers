package com.gwolf.nytbestsellers.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gwolf.nytbestsellers.util.RESULT_TABLE_NAME

@Entity(tableName = RESULT_TABLE_NAME)
data class ResultDbEntity(
    @PrimaryKey
    @ColumnInfo("bestsellers_date")
    val bestsellersDate: String,
    @ColumnInfo("published_date")
    val publishedDate: String,
)