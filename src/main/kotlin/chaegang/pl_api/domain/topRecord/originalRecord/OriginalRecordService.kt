package chaegang.pl_api.domain.topRecord.originalRecord

import chaegang.pl_api.domain.topRecord.dto.TopRecordRequest
import chaegang.pl_api.domain.topRecord.dto.TopRecordResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OriginalRecordService(
    private val originalRecordRepository: OriginalRecordRepository
) {
    @Transactional(readOnly = true)
    fun findTopRecords(request: TopRecordRequest): TopRecordResponse {
        val topRecordResults =  originalRecordRepository.findTopRecords(
            minExclusiveBodyWeight = request.minExclusiveBodyWeight,
            maxInclusiveBodyWeight = request.maxInclusiveBodyWeight,
            equipmentType = request.equipmentType,
            sexType = request.sexType,
            limit = request.limit
        )
        return TopRecordResponse.fromTopRecordResultsDtoList(topRecordResults)
    }
}