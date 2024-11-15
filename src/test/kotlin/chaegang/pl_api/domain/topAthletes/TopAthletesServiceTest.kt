package chaegang.pl_api.domain.topAthletes

import chaegang.pl_api.domain.topAthletes.dto.TopAthletesRequest
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TopAthletesServiceTest : BehaviorSpec({
    val topAthletesRepository = mockk<TopAthletesRepository> {
        every { findTopAthletes(any(), any(), any(), any()) } returns emptyList()
    }
    val topAthletesService = TopAthletesService(topAthletesRepository)

    Given("Call findAthletes with cache") {
        val request = TopAthletesRequest(
            minExclusiveBodyWeight = 0.0,
            maxInclusiveBodyWeight = 100.0,
            equipment = "RAW",
            sex = "M",
        )
        When("Call several times") {
            val time = 5
            repeat(time) {
                topAthletesService.findTopAthletes(request)
            }
            Then("Repository should be called only once") {
                verify(exactly = time) {
                    topAthletesRepository.findTopAthletes(any(), any(), any(), any())
                }
            }

        }
    }
})