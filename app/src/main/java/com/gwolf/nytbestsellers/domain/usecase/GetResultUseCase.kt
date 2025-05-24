package com.gwolf.nytbestsellers.domain.usecase

import com.gwolf.nytbestsellers.domain.entity.ResultEntity
import com.gwolf.nytbestsellers.domain.repository.NYTBestSellersRepository
import com.gwolf.nytbestsellers.util.AppError
import com.gwolf.nytbestsellers.util.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException
import javax.inject.Inject

class GetResultUseCase @Inject constructor(
    private val nytBestSellersRepository: NYTBestSellersRepository
) {
    operator fun invoke(): Flow<DataResult<ResultEntity>> = flow {
        try {
            nytBestSellersRepository.getResult().collect { response ->
                if (response != null) {
                    emit(DataResult.Success(response))
                } else {
                    emit(DataResult.Error(AppError.FailedRetrieveData))
                }
            }
        } catch (e: UnknownHostException) {
            emit(DataResult.Error(AppError.UnknownHostException))
        } catch (e: Exception) {
            emit(DataResult.Error(AppError.Unexpected(e)))
        }
    }
}