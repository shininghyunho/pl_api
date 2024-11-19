package chaegang.pl_api.domain.topRecord.athleteToTopRecord

import chaegang.pl_api.domain.topRecord.dto.TopRecordRequest
import chaegang.pl_api.domain.topRecord.dto.TopRecordResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AthleteToTopRecordService(
    private val athleteToTopRecordRepository: AthleteToTopRecordRepository
) {
    @Transactional(readOnly = true)
    fun findTopRecords(request: TopRecordRequest): TopRecordResponse {
        // TODO : Cache 적용
        val topRecordResults =  athleteToTopRecordRepository.findTopRecords(
            minExclusiveBodyWeight = request.minExclusiveBodyWeight,
            maxInclusiveBodyWeight = request.maxInclusiveBodyWeight,
            equipmentType = request.equipmentType,
            sexType = request.sexType,
            limit = request.limit
        )
        return TopRecordResponse.fromTopRecordResultsDtoList(topRecordResults)
    }
}