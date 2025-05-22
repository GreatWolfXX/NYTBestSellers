package com.gwolf.nytbestsellers.data

import com.gwolf.nytbestsellers.data.database.entity.BookDbEntity
import com.gwolf.nytbestsellers.data.database.entity.ListDbEntity
import com.gwolf.nytbestsellers.data.database.entity.ResultDbEntity
import com.gwolf.nytbestsellers.data.dto.BookDto
import com.gwolf.nytbestsellers.data.dto.ListDto
import com.gwolf.nytbestsellers.data.dto.ResultDto
import com.gwolf.nytbestsellers.domain.entity.BookEntity
import com.gwolf.nytbestsellers.domain.entity.ListEntity
import com.gwolf.nytbestsellers.domain.entity.ResultEntity

fun ResultDto.toDbEntity() = ResultDbEntity(
    bestsellersDate = this.bestsellersDate,
    publishedDate = this.publishedDate,
)

fun ListDto.toDbEntity(resultBestsellersDate: String) = ListDbEntity(
    resultBestsellersDate = resultBestsellersDate,
    listId = this.listId,
    displayName = this.displayName,
)

fun BookDto.toDbEntity(listId: Int) = BookDbEntity(
    listId = listId,
    title = this.title,
    description = this.description,
    author = this.author,
    publisher = this.publisher,
    bookImage = this.bookImage,
    rank = this.rank,
    amazonProductUrl = this.amazonProductUrl,
    primaryIsbn13 = this.primaryIsbn13,
)

fun ResultDbEntity.toDomain() = ResultEntity(
    bestsellersDate = this.bestsellersDate,
    publishedDate = this.publishedDate,
)

fun ListDbEntity.toDomain() = ListEntity(
    resultBestsellersDate = this.resultBestsellersDate,
    listId = this.listId,
    displayName = this.displayName,
)

fun BookDbEntity.toDomain() = BookEntity(
    listId = this.listId,
    title = this.title,
    description = this.description,
    author = this.author,
    publisher = this.publisher,
    bookImage = this.bookImage,
    rank = this.rank,
    amazonProductUrl = this.amazonProductUrl,
    primaryIsbn13 = this.primaryIsbn13,
)