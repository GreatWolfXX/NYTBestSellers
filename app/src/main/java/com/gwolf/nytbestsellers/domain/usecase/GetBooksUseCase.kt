package com.gwolf.nytbestsellers.domain.usecase

import com.gwolf.nytbestsellers.domain.entity.BookEntity
import com.gwolf.nytbestsellers.domain.repository.NYTBestSellersRepository
import com.gwolf.nytbestsellers.util.AppError
import com.gwolf.nytbestsellers.util.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(
    private val nytBestSellersRepository: NYTBestSellersRepository
) {
    operator fun invoke(listId: Int): Flow<DataResult<List<BookEntity>>> = flow {
        try {
            nytBestSellersRepository.getBooksByListId(listId)
                .collect { response ->
                    emit(DataResult.Success(response))
                }
        } catch (e: UnknownHostException) {
            emit(DataResult.Error(AppError.UnknownHostException))
        } catch (e: Exception) {
            emit(DataResult.Error(AppError.Unexpected(e)))
        }
    }
}