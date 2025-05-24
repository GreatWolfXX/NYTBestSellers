package com.gwolf.nytbestsellers.data.repository.store

import com.gwolf.nytbestsellers.data.database.NYTBestSellersDatabase
import com.gwolf.nytbestsellers.data.database.dao.NYTBestSellersDao
import com.gwolf.nytbestsellers.data.database.entity.BookDbEntity
import com.gwolf.nytbestsellers.data.database.entity.ListDbEntity
import com.gwolf.nytbestsellers.data.database.entity.ResultDbEntity
import com.gwolf.nytbestsellers.data.toDomain
import com.gwolf.nytbestsellers.domain.entity.BookEntity
import com.gwolf.nytbestsellers.domain.entity.ListEntity
import com.gwolf.nytbestsellers.domain.entity.ResultEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NYTBestSellersLocalStore @Inject constructor(
    database: NYTBestSellersDatabase
) {

    private val dao: NYTBestSellersDao = database.getNYTBestSellersDao()

    suspend fun getResult(): ResultEntity? = withContext(Dispatchers.IO) { dao.selectResult()?.toDomain() }

    suspend fun getListsByResultBestsellersDate(resultBestsellersDate: String): List<ListEntity> =
        withContext(Dispatchers.IO) { dao.selectListsByResultBestsellersDate(resultBestsellersDate).map { it.toDomain() } }

    suspend fun getBooksByListId(listId: Int): List<BookEntity> =
        withContext(Dispatchers.IO) { dao.selectBooksByListId(listId).map { it.toDomain() } }

    suspend fun insertResult(result: ResultDbEntity) = withContext(Dispatchers.IO) { dao.insertResult(result) }

    suspend fun insertLists(lists: List<ListDbEntity>) = withContext(Dispatchers.IO) { dao.insertLists(lists) }

    suspend fun insertBooks(books: List<BookDbEntity>) = withContext(Dispatchers.IO) { dao.insertBooks(books) }

    suspend fun clearAll() = withContext(Dispatchers.IO) { dao.deleteResult() }
}