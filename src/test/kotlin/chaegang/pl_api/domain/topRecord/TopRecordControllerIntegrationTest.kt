package chaegang.pl_api.domain.topRecord

import chaegang.pl_api.domain.athlete.Athlete
import chaegang.pl_api.domain.athlete.AthleteRepository
import chaegang.pl_api.domain.athleteGameRecord.AthleteGameRecord
import chaegang.pl_api.domain.athleteGameRecord.AthleteGameRecordRepository
import chaegang.pl_api.domain.federation.Federation
import chaegang.pl_api.domain.federation.FederationRepository
import chaegang.pl_api.domain.game.Game
import chaegang.pl_api.domain.game.GameRepository
import chaegang.pl_api.domain.topRecord.dto.TopRecordRequest
import chaegang.pl_api.domain.topRecord.dto.TopRecordResponse
import chaegang.pl_api.support.IntegrationTest
import com.google.gson.Gson
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.doubles.shouldBeGreaterThan
import io.kotest.matchers.doubles.shouldBeLessThanOrEqual
import io.kotest.matchers.floats.shouldBeLessThanOrEqual
import io.kotest.matchers.string.shouldContain
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import java.time.LocalDate

@IntegrationTest
class TopRecordControllerIntegrationTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired val athleteRepository: AthleteRepository,
    @Autowired val federationRepository: FederationRepository,
    @Autowired val gameRepository: GameRepository,
    @Autowired val athleteGameRecordRepository: AthleteGameRecordRepository,
    @Value("\${spring.profiles.active}") val activeProfile: String,
) : BehaviorSpec() {
    val gson = Gson()
        init {
            Given("Get /top-records") {
                if(activeProfile.equals("local")) saveEntity()
                val request = TopRecordRequest(
                    minExclusiveBodyWeight = 66.0,
                    maxInclusiveBodyWeight = 74.0,
                    equipment = "RAW",
                    sex = "M",
                )
                When("Requesting with valid parameters") {
                    val result = mockMvc.perform(MockMvcRequestBuilders.get("/top-records?" +
                            "minExclusiveBodyWeight=${request.minExclusiveBodyWeight}&" +
                            "maxInclusiveBodyWeight=${request.maxInclusiveBodyWeight}&" +
                            "equipment=${request.equipmentType.name}&" +
                            "sex=${request.sexType.name}&" +
                            "limit=${request.limit}"
                    )
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                        .andDo(MockMvcResultHandlers.print())
                        .andReturn().response.contentAsString
                    val topRecordsResponse = convertJsonToTopRecordsResponse(result)
                    val topRecords = topRecordsResponse.topRecords
                    Then("Return 200 OK") {
                        result shouldContain HttpStatus.OK.value().toString()
                    }
                    Then("top records should be in range") {
                        topRecords.forEach { topRecord ->
                            topRecord.bodyWeight shouldBeLessThanOrEqual(request.maxInclusiveBodyWeight)
                            topRecord.bodyWeight shouldBeGreaterThan(request.minExclusiveBodyWeight)
                        }
                    }
                    Then("top records should be sorted by total") {
                        var prevTotal = Float.MAX_VALUE
                        topRecords.forEach { topRecord ->
                            topRecord.total shouldBeLessThanOrEqual(prevTotal)
                            prevTotal = topRecord.total
                        }
                    }
            }
        }
    }
    fun saveEntity() {
        val ipf = federationRepository.save(
            Federation(
            name = "IPF"
        ))
        val fpr = federationRepository.save(
            Federation(
            name = "FPR",
            parentFederation = ipf
        ))
        val testGame = gameRepository.save(
            Game(
            event = "Test Event",
            country = "Korea",
            state = "Seoul",
            date = LocalDate.now(),
            meetCountry = "Korea",
            meetState = "Seoul",
            meetTown = "Seoul",
            meetName = "Test Meet",
            federation = fpr
        ))
        val austin = athleteRepository.save(
            Athlete(
            name = "Austin",
            sex = "M"
        ))
        val austinGameRecord = athleteGameRecordRepository.save(
            AthleteGameRecord(
            athlete = austin,
            game = testGame,
            age = 30f,
            bodyWeight = 66.0,
            bestSquat = 300f,
            bestBench = 200f,
            bestDeadlift = 400f,
            total = 900f,
            dots = 123.0,
            wilks = 456.0,
            glossbrenner = 789.0,
            goodlift = 1000.0,
            tested = true,
            sanctioned = true,
            equipment = "Raw"
        ))
        val taylor = athleteRepository.save(
            Athlete(
            name = "Taylor",
            sex = "M"
        ))
        val taylorGameRecord = athleteGameRecordRepository.save(
            AthleteGameRecord(
            athlete = taylor,
            game = testGame,
            age = 30f,
            bodyWeight = 68.0,
            bestSquat = 300f,
            bestBench = 200f,
            bestDeadlift = 300f,
            total = 800f,
            dots = 123.0,
            wilks = 456.0,
            glossbrenner = 789.0,
            goodlift = 900.0,
            tested = true,
            sanctioned = true,
            equipment = "Raw"
        ))
        val heavyMan = athleteRepository.save(
            Athlete(
            name ="heavyMan",
            sex = "M"
        ))
        val heavyManGameRecord = athleteGameRecordRepository.save(
            AthleteGameRecord(
            athlete = heavyMan,
            game = testGame,
            age = 30f,
            bodyWeight = 74.0,
            bestSquat = 300f,
            bestBench = 400f,
            bestDeadlift = 300f,
            total = 1000f,
            dots = 123.0,
            wilks = 456.0,
            glossbrenner = 789.0,
            goodlift = 900.0,
            tested = true,
            sanctioned = true,
            equipment = "Raw"
        ))
    }
    fun convertJsonToTopRecordsResponse(jsonString: String): TopRecordResponse {
        return gson.fromJson(jsonString,TopRecordResponse::class.java)
    }
}