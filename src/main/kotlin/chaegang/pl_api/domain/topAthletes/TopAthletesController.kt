package chaegang.pl_api.domain.topAthletes

import chaegang.pl_api.domain.topAthletes.dto.TopAthletesRequest
import chaegang.pl_api.domain.topAthletes.dto.TopAthletesResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TopAthletesController(
    private val topAthletesService: TopAthletesService
) {
    @GetMapping("/top-athletes")
    fun findTopAthletes(request: TopAthletesRequest): TopAthletesResponse {
        return topAthletesService.findTopAthletes(request)
    }
}