package chaegang.pl_api.domain.statistics

import chaegang.pl_api.domain.statistics.dto.AthleteResultDto
import chaegang.pl_api.domain.statistics.dto.StatisticsRequestDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StatisticsService(
    private val statisticsRepository: StatisticsRepository
) {
    @Transactional(readOnly = true)
    fun findTopAthletes(request: StatisticsRequestDto): List<AthleteResultDto> {
        // TODO : Cache 적용
        val result =  statisticsRepository.findTopAthletes(
            minBodyWeight = request.minBodyWeight,
            maxBodyWeight = request.maxBodyWeight,
            equipmentType = request.equipmentType,
            sexType = request.sexType,
            limit = request.limit
        )
        return result
    }
}