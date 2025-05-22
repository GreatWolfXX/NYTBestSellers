package com.gwolf.nytbestsellers.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gwolf.nytbestsellers.data.database.dao.NYTBestSellersDao
import com.gwolf.nytbestsellers.data.database.entity.BookDbEntity
import com.gwolf.nytbestsellers.data.database.entity.ResultDbEntity
import com.gwolf.nytbestsellers.util.DB_VERSION

@Database(
    version = DB_VERSION,
    entities = [ResultDbEntity::class, List::class, BookDbEntity::class]
)
abstract class NYTBestSellersDatabase : RoomDatabase() {

    abstract fun getNYTBestSellersDao(): NYTBestSellersDao
}