package com.gwolf.nytbestsellers.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gwolf.nytbestsellers.data.database.entity.BookDbEntity
import com.gwolf.nytbestsellers.data.database.entity.ListDbEntity
import com.gwolf.nytbestsellers.data.database.entity.ResultDbEntity

@Dao
interface NYTBestSellersDao {
    @Query("SELECT * FROM ResultTable books LIMIT 1")
    suspend fun selectResult(): ResultDbEntity?

    @Query("SELECT * FROM ListsTable WHERE resultBestsellersDate = :resultBestsellersDate")
    suspend fun selectListsByResultBestsellersDate(resultBestsellersDate: String): List<ListDbEntity>

    @Query("SELECT * FROM BooksTable WHERE list_id = :listId")
    suspend fun selectBooksByListId(listId: Int): List<BookDbEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertResult(result: ResultDbEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLists(lists: List<ListDbEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBooks(books: List<BookDbEntity>)

    @Query("DELETE FROM ResultTable")
    suspend fun deleteResult()
}