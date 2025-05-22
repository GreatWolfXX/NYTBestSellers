package com.gwolf.nytbestsellers.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.gwolf.nytbestsellers.util.BOOKS_TABLE_NAME

@Entity(
    tableName = BOOKS_TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = ListDbEntity::class,
        parentColumns = ["list_id"],
        childColumns = ["list_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class BookDbEntity(
    @PrimaryKey @ColumnInfo(name = "primary_isbn13") val primaryIsbn13: String,
    @ColumnInfo(name = "list_id") val listId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "publisher") val publisher: String,
    @ColumnInfo(name = "book_image") val bookImage: String,
    @ColumnInfo(name = "rank") val rank: Int,
    @ColumnInfo(name = "amazon_product_url") val amazonProductUrl: String,
)