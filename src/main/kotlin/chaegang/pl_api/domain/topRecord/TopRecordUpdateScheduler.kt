package chaegang.pl_api.domain.topRecord

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty(value = ["scheduler.enabled"], havingValue = "true", matchIfMissing = false)
class TopRecordUpdateScheduler(
    private val topRecordUpdater: TopRecordUpdater
) {
    @Value("\${scheduler.topRecordUpdate.cron}")
    val cron: String? = null

    @Value("\${scheduler.enabled}")
    val schedulerEnabled: Boolean = false

    @Value("\${spring.datasource.url}")
    val datasourceUrl: String? = null

    init {
        // TODO : 아 이거 왜 안되지
        println("scheduler enabled")
        println("cron: $cron")
        println("schedulerEnabled: $schedulerEnabled")
        println("datasourceUrl: $datasourceUrl")
    }
    @Scheduled(cron="\${scheduler.topRecordUpdate.cron}", zone="Asia/Seoul")
    fun updateTopRecord() {
        println("scheduler activated")
        //topRecordUpdater.updateTopRecord()
    }
}