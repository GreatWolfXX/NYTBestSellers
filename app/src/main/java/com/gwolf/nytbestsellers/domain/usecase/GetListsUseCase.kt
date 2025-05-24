package com.gwolf.nytbestsellers.domain.usecase

import com.gwolf.nytbestsellers.domain.entity.ListEntity
import com.gwolf.nytbestsellers.domain.repository.NYTBestSellersRepository
import com.gwolf.nytbestsellers.util.AppError
import com.gwolf.nytbestsellers.util.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException
import javax.inject.Inject

class GetListsUseCase @Inject constructor(
    private val nytBestSellersRepository: NYTBestSellersRepository
) {
    operator fun invoke(resultBestsellersDate: String): Flow<DataResult<List<ListEntity>>> = flow {
        try {
            nytBestSellersRepository.getListsByResultBestsellersDate(resultBestsellersDate)
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