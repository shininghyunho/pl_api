package chaegang.pl_api.domain.topRecord

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

@IntegrationTest
class TopRecordControllerIntegrationTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired val topRecordRepository: TopRecordRepository,
    @Value("\${spring.profiles.active}") val activeProfile: String,
) : BehaviorSpec() {
    val gson = Gson()
        init {
            Given("Get /top-records") {
                if(activeProfile.equals("local")) saveEntity()
                val request = TopRecordRequest(
                    minExclusiveBodyWeight = 80.0,
                    maxInclusiveBodyWeight = 90.0,
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
        // Charlie
        topRecordRepository.save(TopRecord(
            name = "Charlie",
            equipment = "Raw",
            total = 800f,
            squat = 300f,
            bench = 200f,
            deadlift = 300f,
            sex = "M",
            bodyWeight = 80.0,
            age = 30f,
            dots = 500.0,
            wilks = 400.0,
            glossbrenner = 300.0,
            goodlift = 800.0,
            tested = true,
            sanctioned = true,
            date = "2021-01-01",
            federationName = "IPF",
        ))
        // Austin
        topRecordRepository.save(TopRecord(
            name = "Austin",
            equipment = "Raw",
            total = 900f,
            squat = 400f,
            bench = 300f,
            deadlift = 200f,
            sex = "M",
            bodyWeight = 90.0,
            age = 40f,
            dots = 600.0,
            wilks = 500.0,
            glossbrenner = 400.0,
            goodlift = 900.0
        ))
        // Bob
        topRecordRepository.save(TopRecord(
            name = "Bob",
            equipment = "Raw",
            total = 700f,
            squat = 200f,
            bench = 100f,
            deadlift = 400f,
            sex = "M",
            bodyWeight = 70.0,
            age = 20f,
            dots = 400.0,
            wilks = 300.0,
        ))
        // John
        topRecordRepository.save(TopRecord(
            name = "John",
            equipment = "Raw",
            total = 600f,
            squat = 100f,
            bench = 300f,
            deadlift = 200f,
            sex = "M",
            bodyWeight = 60.0,
        ))
        // Taylor
        topRecordRepository.save(TopRecord(
            name = "Taylor",
            equipment = "Single-ply",
            total = 200f,
            bench = 200f
        ))
    }
    fun convertJsonToTopRecordsResponse(jsonString: String): TopRecordResponse {
        return gson.fromJson(jsonString,TopRecordResponse::class.java)
    }
}