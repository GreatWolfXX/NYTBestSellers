package com.gwolf.nytbestsellers.data.repository

import com.gwolf.nytbestsellers.data.repository.store.NYTBestSellersLocalStore
import com.gwolf.nytbestsellers.data.repository.store.NYTBestSellersRestStore
import com.gwolf.nytbestsellers.data.toDbEntity
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

    override fun getResult(): Flow<ResultEntity?> = flow {
        val localResult = localStore.getResult()

        if (localResult != null) {
            emit(localResult)
            return@flow
        }

        val restResult = restStore.getOverview()
        val restLists = restResult.lists
        val lists = restLists.map { it.toDbEntity(restResult.bestsellersDate) }
        val books = restLists.flatMap { list -> list.books.map { it.toDbEntity(list.listId) } }
        localStore.insertResult(restResult.toDbEntity())
        localStore.insertLists(lists)
        localStore.insertBooks(books)

        emit(localStore.getResult())
    }

    override fun getListsByResultBestsellersDate(resultBestsellersDate: String)
            : Flow<List<ListEntity>> = flow {
        emit(localStore.getListsByResultBestsellersDate(resultBestsellersDate))
    }

    override fun getBooksByListId(listId: String): Flow<List<BookEntity>> = flow {
        emit(localStore.getBooksByListId(listId))
    }

    override fun clearCachedData(): Flow<Unit> = flow {
        localStore.clearAll()
    }
}