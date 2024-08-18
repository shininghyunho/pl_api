package chaegang.pl_api.domain.topAthletes

import chaegang.pl_api.domain.athlete.Athlete
import chaegang.pl_api.domain.athlete.AthleteRepository
import chaegang.pl_api.domain.athlete.SexType
import chaegang.pl_api.domain.athleteGameRecord.AthleteGameRecord
import chaegang.pl_api.domain.athleteGameRecord.AthleteGameRecordRepository
import chaegang.pl_api.domain.athleteGameRecord.EquipmentType
import chaegang.pl_api.domain.federation.Federation
import chaegang.pl_api.domain.federation.FederationRepository
import chaegang.pl_api.domain.game.Game
import chaegang.pl_api.domain.game.GameRepository
import chaegang.pl_api.domain.topAthletes.dto.TopAthleteQueryResult
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.doubles.shouldBeLessThanOrEqual
import io.kotest.matchers.ints.shouldBeLessThanOrEqual
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import java.time.LocalDate

@DataJpaTest
@Import(TopAthletesRepository::class)
class TopAthletesRepositoryTest (
    @Autowired val topAthletesRepository: TopAthletesRepository,
    @Autowired val athleteRepository: AthleteRepository,
    @Autowired val federationRepository: FederationRepository,
    @Autowired val gameRepository: GameRepository,
    @Autowired val athleteGameRecordRepository: AthleteGameRecordRepository
) :ShouldSpec({
    // 각 Test Case 마다 Transactional 적용
    extensions(SpringTestExtension(SpringTestLifecycleMode.Root))

    fun saveEntity() {
        val ipf = federationRepository.save(Federation(
            name = "IPF"
        ))
        val fpr = federationRepository.save(Federation(
            name = "FPR",
            parentFederation = ipf
        ))
        val testGame = gameRepository.save(Game(
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
        val austin = athleteRepository.save(Athlete(
            name = "Austin",
            sex = "M"
        ))
        val austinGameRecord = athleteGameRecordRepository.save(AthleteGameRecord(
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
        val taylor = athleteRepository.save(Athlete(
            name = "Taylor",
            sex = "M"
        ))
        val taylorGameRecord = athleteGameRecordRepository.save(AthleteGameRecord(
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
        val heavyMan = athleteRepository.save(Athlete(
            name ="heavyMan",
            sex = "M"
        ))
        val heavyManGameRecord = athleteGameRecordRepository.save(AthleteGameRecord(
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
    context("findTopAthletes success") {
        saveEntity()
        val minExclusiveBodyWeight = 66.0
        val maxInclusiveBodyWeight = 74.0
        val response : List<TopAthleteQueryResult> = topAthletesRepository.findTopAthletes(
            minExclusiveBodyWeight = minExclusiveBodyWeight,
            maxInclusiveBodyWeight = maxInclusiveBodyWeight,
            equipmentType = EquipmentType.RAW,
            sexType = SexType.M,
        )
        should("response should not be empty") {
            response.isEmpty() shouldBe false
        }
        should("response > minExclusiveBodyWeight") {
            response.forEach {
                it.bodyWeight shouldBeGreaterThan(minExclusiveBodyWeight)
            }
        }
        should("response <=  maxInclusiveBodyWeight") {
            response.forEach {
                it.bodyWeight shouldBeLessThanOrEqual(maxInclusiveBodyWeight)
            }
        }
        should("response should be sorted by total") {
            response.forEachIndexed { index, athleteResultDto ->
                if (index < response.size - 1) {
                    athleteResultDto.total shouldBeGreaterThan response[index + 1].total!!
                }
            }
        }
        should("default response count <= 10") {
            response.size shouldBeLessThanOrEqual(10)
        }
    }
    context("findTopAthletes minExclusiveBodyWeight > maxInclusiveBodyWeight") {
        saveEntity()
        val minExclusiveBodyWeight = 74.0
        val maxInclusiveBodyWeight = 66.0
        val response : List<TopAthleteQueryResult> = topAthletesRepository.findTopAthletes(
            minExclusiveBodyWeight = minExclusiveBodyWeight,
            maxInclusiveBodyWeight = maxInclusiveBodyWeight,
            equipmentType = EquipmentType.RAW,
            sexType = SexType.M
        )
        should("response should be empty") {
            response.isEmpty() shouldBe true
        }
    }
    context("findTopAthletes limit < 1") {
        saveEntity()
        val minExclusiveBodyWeight = 66.0
        val maxInclusiveBodyWeight = 74.0
        val response : List<TopAthleteQueryResult> = topAthletesRepository.findTopAthletes(
            minExclusiveBodyWeight = minExclusiveBodyWeight,
            maxInclusiveBodyWeight = maxInclusiveBodyWeight,
            equipmentType = EquipmentType.RAW,
            sexType = SexType.M,
            limit = -10
        )
        should("response should be empty") {
            response.isEmpty() shouldBe true
        }
    }
})