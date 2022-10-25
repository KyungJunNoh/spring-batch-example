package com.start.springbatch.batch

import com.start.springbatch.book.repository.BookRepository
import com.start.springbatch.book.entity.Book
import org.springframework.batch.item.ItemReader
import org.springframework.beans.factory.annotation.Autowired
import javax.annotation.PostConstruct

open class CustomItemReader: ItemReader<Book> {

    @Autowired
    private lateinit var bookRepository: BookRepository
    private lateinit var list: MutableList<Book>
    private var nextIndex: Int = 0

    @PostConstruct
    fun postConstruct() {
        list = bookRepository.findAll()
    }

    override fun read(): Book? {
        if(nextIndex < list.size) {
            return list[nextIndex++]
        }
        return null
    }
}