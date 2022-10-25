package com.start.springbatch.batch.scheduler

import com.start.springbatch.batch.config.CustomReaderJobConfig
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.batch.core.JobParameter
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersInvalidException
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.text.SimpleDateFormat
import javax.batch.operations.JobExecutionAlreadyCompleteException

@Component
class JobScheduler(
    val jobLauncher: JobLauncher, // Job 을 실행하는 JobLauncher
    val customReaderJobConfig: CustomReaderJobConfig, // Job 을 정의한 Config Class
) {
    private val logger: Log = LogFactory.getLog(JobScheduler::class.java)
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")

    @Scheduled(initialDelay = 10000, fixedDelay = 3600000) // 메소드 생성 후 10초뒤에 1시간에 한번씩 실행
    fun runJob() {
        val jobConf = hashMapOf<String, JobParameter>() // JobParameter 을 만들기 위한 hashMapOf<String, JobParameter> 추가
        jobConf["time"] = JobParameter(dateFormat.format(System.currentTimeMillis())) // 현재시간을 포멧하여 JobParameter 생성
        val jobParameters = JobParameters(jobConf) // 현재시간을 포멧하여 JobParameter 생성

        try{
            val job = customReaderJobConfig.customReaderJob() // 정의된 Job 을 호출
            jobLauncher.run(job, jobParameters) // Job 을 JobParameters 와 함께하여 실행
        } catch(e: JobExecutionAlreadyCompleteException){
            logger.error(e.localizedMessage)
        } catch(e: JobExecutionAlreadyRunningException){
            logger.error(e.localizedMessage)
        } catch(e: JobParametersInvalidException){
            logger.error(e.localizedMessage)
        }
    }
}