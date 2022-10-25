package com.start.springbatch.book.entity

import javax.persistence.*

@Entity
@Table(name = "book")
class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var title: String = ""
    var contents: String = ""
    var author: String = ""
}