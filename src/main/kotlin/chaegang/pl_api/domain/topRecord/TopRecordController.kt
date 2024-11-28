package chaegang.pl_api.domain.topRecord

import chaegang.pl_api.domain.topRecord.dto.TopRecordRequest
import chaegang.pl_api.domain.topRecord.dto.TopRecordResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TopRecordController(
    private val topRecordService: TopRecordService
) {
    @GetMapping("/top-records")
    fun findTopRecords(request: TopRecordRequest): TopRecordResponse {
        return topRecordService.findTopRecord(request)
    }
}