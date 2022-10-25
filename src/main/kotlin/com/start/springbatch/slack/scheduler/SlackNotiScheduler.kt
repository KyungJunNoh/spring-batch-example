package com.start.springbatch.slack.scheduler

import com.start.springbatch.slack.client.SlackNotiClient
import com.start.springbatch.slack.service.SlackNotiService
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SlackNotiScheduler(
    val slackNotiClient: SlackNotiClient,
    val slackNotiService: SlackNotiService
) {

    @Value("\${third-party.slack.noti.path}")
    lateinit var SLACK_NOTI_PATH : String

    @Scheduled(initialDelay = 20000, fixedDelay = 5400000) // 메소드 생성 후 20초뒤에 1시간 30분에 한번씩 실행
    fun slackNoti() {
        val text = slackNotiService.createText()
        slackNotiClient.sendMassage(mapOf("text" to text), SLACK_NOTI_PATH)
    }
}