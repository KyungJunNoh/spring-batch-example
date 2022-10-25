package com.start.springbatch.slack.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    name = "SlackNotiClient",
    url = "https://hooks.slack.com/services",
)
interface SlackNotiClient {

    @PostMapping(value = ["/{path}"])
    fun sendMassage(@RequestBody message: Map<String,String>, @PathVariable path: String)
}