package chaegang.pl_api.domain.topRecord

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.util.concurrent.TimeUnit

@SpringBootTest
@ActiveProfiles("scheduler-activated")
class TopRecordUpdateSchedulerTest : ShouldSpec () {
    private final val topRecordUpdater= mockk<TopRecordUpdater>(relaxed = true)
    val topRecordUpdateScheduler = TopRecordUpdateScheduler(topRecordUpdater)

    @Value("\${scheduler.enabled}")
    val schedulerEnabled: Boolean = false
    @Value("\${scheduler.topRecordUpdate.cron}")
    val cron: String? = null

    init {
        extensions(SpringTestExtension(SpringTestLifecycleMode.Root))

        context("TopRecordUpdateScheduler") {
            should("scheduler enabled") {
                schedulerEnabled shouldBe true
            }
            xshould("cron is valid") {
                println(cron)
                // cron 이 비어있지 않음
                cron.shouldNotBeNull()
            }
            xshould("called updateTopRecord") {
                // 5 초 대기
                TimeUnit.SECONDS.sleep(5)
                verify(atLeast = 1) { topRecordUpdateScheduler.updateTopRecord() }
            }
        }
    }
}