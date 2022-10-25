package com.start.springbatch.batch.config

import com.start.springbatch.batch.CustomItemReader
import com.start.springbatch.book.entity.Book
import com.start.springbatch.book.repository.BookRepository
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CustomReaderJobConfig(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory,
    val bookRepository: BookRepository
) {
    private final val CUSTOM_READER_JOB = "CUSTOM_READER_JOB"
    private final val CUSTOM_READER_JOB_STEP = CUSTOM_READER_JOB +"_STEP"
    private final val CHUNK_SIZE = 1000

    @Bean
    fun customReaderJob(): Job {
        return jobBuilderFactory.get(CUSTOM_READER_JOB)
            .start(customReaderStep())
            .build()
    }

    @Bean
    fun customReaderStep(): Step {
        return stepBuilderFactory.get(CUSTOM_READER_JOB_STEP)
            .chunk<Book, Book>(CHUNK_SIZE) // 청크란 덩어리로 작업할때 각 커밋 사이에 처리되는 row의 수를 얘기한다. ex) 10000개의 로우를 조회할때 10개씩 끊어서 가져온다는것
            .reader(reader())
            .processor(processor())
            .writer(writer())
            .build()
    }

    @Bean
    @StepScope // Step실행 시에 새로운 CustomItemReader를 만들어 주기 위함
    fun reader(): CustomItemReader {
        return CustomItemReader()
    }

    @Bean
    fun processor(): ItemProcessor<Book, Book> {
        return ItemProcessor {
            it.author = "test_author"
            if(it.id!! % 2 == 0L) { // 짝수
                it.contents = "짝수1"
            } else {
                it.contents = "홀수1"
            }
            it
        }
    }

    @Bean
    fun writer(): ItemWriter<Book> {
        return ItemWriter {
            bookRepository.saveAll(it)
        }
    }
}