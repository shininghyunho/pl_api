package chaegang.pl_api.domain.topAthletes

import chaegang.pl_api.domain.topAthletes.dto.TopAthletesRequest
import chaegang.pl_api.domain.topAthletes.dto.TopAthletesResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TopAthletesService(
    private val topAthletesRepository: TopAthletesRepository
) {
    @Transactional(readOnly = true)
    fun findTopAthletes(request: TopAthletesRequest): TopAthletesResponse {
        val athleteResults =  topAthletesRepository.findTopAthletes(
            minExclusiveBodyWeight = request.minExclusiveBodyWeight,
            maxInclusiveBodyWeight = request.maxInclusiveBodyWeight,
            equipmentType = request.equipmentType,
            sexType = request.sexType,
        )
        return TopAthletesResponse.fromAthleteResultDtoList(athleteResults)
    }
}