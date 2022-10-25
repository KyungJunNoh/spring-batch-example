package com.start.springbatch.slack.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.start.springbatch.book.service.BookService
import org.springframework.stereotype.Service

/**
 * Slack 의 Notification 을 사용할때 필요한 서비스 레이어
 */
@Service
class SlackNotiService(
    val objectMapper: ObjectMapper,
    val bookService: BookService
) {

    fun createText(): String {
        val responseStringBuilder = StringBuilder()
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true)
        val prettyPrinter = objectMapper.writerWithDefaultPrettyPrinter()
        val bookList = bookService.get50()
        for (book in bookList) {
            responseStringBuilder.append("${prettyPrinter.writeValueAsString(book)},\n")
        }
        return responseStringBuilder.toString()
    }
}