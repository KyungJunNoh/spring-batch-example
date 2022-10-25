package com.start.springbatch.book.service

import com.start.springbatch.book.repository.BookRepository
import com.start.springbatch.book.entity.Book
import org.springframework.stereotype.Service

@Service
class BookService(
    val bookRepository: BookRepository
) {

    fun get50(): List<Book> {
        return bookRepository.get50()
    }
}