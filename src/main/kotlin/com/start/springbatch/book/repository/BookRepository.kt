package com.start.springbatch.book.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.start.springbatch.book.entity.Book
import com.start.springbatch.entity.QBook
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository: JpaRepository<Book, Long>, BookRepositoryCustom {
}

interface BookRepositoryCustom {
    fun get50(): List<Book>
}

class BookRepositoryCustomImpl(
    val jpaQueryFactory: JPAQueryFactory
): BookRepositoryCustom {

    override fun get50(): List<Book> {
        return jpaQueryFactory.select(QBook.book)
            .from(QBook.book)
            .where(QBook.book.id.loe(50))
            .fetch()
    }
}