package chaegang.pl_api.domain.topRecord

import chaegang.pl_api.domain.topRecord.originalRecord.OriginalRecordRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk

class TopRecordUpdaterTest : BehaviorSpec() {
    val topRecordRepository = mockk<TopRecordRepository>(relaxed = true) {
        every { save(any()) } returns mockk()
    }
    val originalRepository = mockk<OriginalRecordRepository>(relaxed = true) {
        every { findTopRecords(
            minExclusiveBodyWeight = any(),
            maxInclusiveBodyWeight = any(),
            sexType = any(),
            equipmentType = any()
        ) } returns mockk()
    }
    val topRecordUpdater = TopRecordUpdater(topRecordRepository,originalRepository)

}