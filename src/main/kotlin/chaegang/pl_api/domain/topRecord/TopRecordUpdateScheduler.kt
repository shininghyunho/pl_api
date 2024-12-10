package chaegang.pl_api.domain.topRecord

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty(value = ["scheduler.enabled"], havingValue = "true", matchIfMissing = false)
class TopRecordUpdateScheduler(
    private val topRecordUpdater: TopRecordUpdater
) {
    val logger = LoggerFactory.getLogger(TopRecordUpdateScheduler::class.java)
    // 매달 1일 3시에 실행
    @Scheduled(cron="0 0 3 1 * ?")
    fun updateTopRecord() {
        logger.info("scheduler activated")
        topRecordUpdater.updateTopRecord()
    }
}