package com.gwolf.nytbestsellers.data.repository

import com.gwolf.nytbestsellers.data.repository.store.NYTBestSellersLocalStore
import com.gwolf.nytbestsellers.data.repository.store.NYTBestSellersRestStore
import com.gwolf.nytbestsellers.domain.entity.BookEntity
import com.gwolf.nytbestsellers.domain.entity.ListEntity
import com.gwolf.nytbestsellers.domain.entity.ResultEntity
import com.gwolf.nytbestsellers.domain.repository.NYTBestSellersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NYTBestSellersRepositoryImpl @Inject constructor(
    private val restStore: NYTBestSellersRestStore,
    private val localStore: NYTBestSellersLocalStore
) : NYTBestSellersRepository {

    override fun getResult(): Flow<ResultEntity> = flow {
        val localResult = localStore.getResult()

        if(localResult != null) {
            emit(localResult)
        }

        val restResult = restStore.getOverview().result
        val restLists = restResult.lists
        val restBooks = restLists.flatMap { it.books }
        localStore.insertResult(restResult)
        localStore.insertLists(restLists)
        localStore.insertBooks(restBooks)

        emit(localResult)
    }

    override fun getListsByResultBestsellersDate(resultBestsellersDate: String)
            : Flow<List<ListEntity>> = flow {
        emit(localStore.getListsByResultBestsellersDate(resultBestsellersDate))
    }

    override fun getBooksByListId(listId: String): Flow<List<BookEntity>> = flow {
        emit(localStore.getBooksByListId(listId))
    }

    override fun getBookByIsbn(isbn: String): Flow<BookEntity> = flow {
        emit(localStore.getBookByIsbn(isbn))
    }
}