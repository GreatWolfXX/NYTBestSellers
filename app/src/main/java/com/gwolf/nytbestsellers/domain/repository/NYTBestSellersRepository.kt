package com.gwolf.nytbestsellers.domain.repository

import com.gwolf.nytbestsellers.domain.entity.BookEntity
import com.gwolf.nytbestsellers.domain.entity.ListEntity
import com.gwolf.nytbestsellers.domain.entity.ResultEntity
import kotlinx.coroutines.flow.Flow

interface NYTBestSellersRepository {
    fun getResult(): Flow<ResultEntity>
    fun getListsByResultBestsellersDate(resultBestsellersDate: String): Flow<List<ListEntity>>
    fun getBooksByListId(listId: String): Flow<List<BookEntity>>
    fun getBookByIsbn(isbn: String): Flow<BookEntity>
}