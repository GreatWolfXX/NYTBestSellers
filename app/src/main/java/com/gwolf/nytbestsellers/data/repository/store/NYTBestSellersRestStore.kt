package com.gwolf.nytbestsellers.data.repository.store

import com.gwolf.nytbestsellers.BuildConfig
import com.gwolf.nytbestsellers.data.dto.OverviewDto
import com.gwolf.nytbestsellers.data.dto.ResultDto
import com.gwolf.nytbestsellers.util.API_KEY_PARAM
import com.gwolf.nytbestsellers.util.NYT_BEST_SELLERS_BASE_URL
import com.gwolf.nytbestsellers.util.OVERVIEW_ENDPOINT
import com.gwolf.nytbestsellers.util.SUCCESS_STATUS_CODE
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NYTBestSellersRestStore @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun getOverview(): ResultDto {
        val response = withContext(Dispatchers.IO) {
            httpClient.post(NYT_BEST_SELLERS_BASE_URL + OVERVIEW_ENDPOINT) {
                contentType(ContentType.Application.Json)
                parameter(API_KEY_PARAM, BuildConfig.NYT_BEST_SELLERS_API)
            }
        }
        val data = response.body<OverviewDto>()
        if (data.status == SUCCESS_STATUS_CODE) {
            return data.results
        } else {
            throw ResponseException(response, response.status.description)
        }
    }
}