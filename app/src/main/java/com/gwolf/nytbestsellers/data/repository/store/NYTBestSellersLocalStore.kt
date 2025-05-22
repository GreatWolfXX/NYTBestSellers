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
import javax.inject.Inject

class NYTBestSellersLocalStore @Inject constructor(
    private val database: NYTBestSellersDatabase
) {

    private val dao: NYTBestSellersDao = database.getNYTBestSellersDao()

    suspend fun getResult(): ResultEntity? = dao.selectResult()?.toDomain()

    suspend fun getListsByResultBestsellersDate(resultBestsellersDate: String): List<ListEntity> =
        dao.selectListsByResultBestsellersDate(resultBestsellersDate).map { it.toDomain() }

    suspend fun getBooksByListId(listId: String): List<BookEntity> =
        dao.selectBooksByListId(listId).map { it.toDomain() }

    suspend fun getBookByIsbn(isbn: String): BookEntity? =
        dao.selectBookByIsbn(isbn).toDomain()

    suspend fun insertResult(result: ResultDbEntity) = dao.insertResult(result)

    suspend fun insertLists(lists: List<ListDbEntity>) = dao.insertLists(lists)

    suspend fun insertBooks(books: List<BookDbEntity>) = dao.insertBooks(books)

    suspend fun clearAll() = dao.clearAll()
}