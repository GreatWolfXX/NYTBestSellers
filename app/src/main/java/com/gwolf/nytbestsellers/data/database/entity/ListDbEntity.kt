package com.gwolf.nytbestsellers.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.gwolf.nytbestsellers.util.LISTS_TABLE_NAME

@Entity(
    tableName = LISTS_TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = ResultDbEntity::class,
        parentColumns = ["bestsellers_date"],
        childColumns = ["resultBestsellersDate"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ListDbEntity(
    @PrimaryKey @ColumnInfo("list_id") val listId: String,
    @ColumnInfo("display_name") val displayName: String,
    @ColumnInfo("resultBestsellersDate") val resultBestsellersDate: String,
)