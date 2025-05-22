package com.gwolf.nytbestsellers.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gwolf.nytbestsellers.data.database.dao.NYTBestSellersDao
import com.gwolf.nytbestsellers.data.database.entity.BookDbEntity
import com.gwolf.nytbestsellers.data.database.entity.ListDbEntity
import com.gwolf.nytbestsellers.data.database.entity.ResultDbEntity
import com.gwolf.nytbestsellers.util.DB_VERSION

@Database(
    version = DB_VERSION,
    entities = [ResultDbEntity::class, ListDbEntity::class, BookDbEntity::class]
)
abstract class NYTBestSellersDatabase : RoomDatabase() {

    abstract fun getNYTBestSellersDao(): NYTBestSellersDao
}