package chaegang.pl_api.domain.topRecord

import chaegang.pl_api.domain.athlete.Athlete
import chaegang.pl_api.domain.athlete.AthleteRepository
import chaegang.pl_api.domain.athleteGameRecord.AthleteGameRecord
import chaegang.pl_api.domain.athleteGameRecord.AthleteGameRecordRepository
import chaegang.pl_api.domain.federation.Federation
import chaegang.pl_api.domain.federation.FederationRepository
import chaegang.pl_api.domain.game.Game
import chaegang.pl_api.domain.game.GameRepository
import chaegang.pl_api.support.IntegrationTest
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate

@IntegrationTest
class TopRecordUpdaterIntegrationTest(
    @Autowired val topRecordUpdater: TopRecordUpdater,
    @Autowired val athleteRepository: AthleteRepository,
    @Autowired val federationRepository: FederationRepository,
    @Autowired val gameRepository: GameRepository,
    @Autowired val athleteGameRecordRepository: AthleteGameRecordRepository,
    @Autowired val topRecordRepository: TopRecordRepository
) : DescribeSpec() {
    init {
        describe("updateTopRecord test") {
            context("saved original and topRecord data") {
                saveTopRecordData()
                saveOriginalData()
                val priorTopRecords= topRecordRepository.findAll()
                topRecordUpdater.updateTopRecord()
                it("prior topRecord should be different from new topRecord") {
                    val newTopRecords= topRecordRepository.findAll()
                    newTopRecords shouldNotBe priorTopRecords
                }
                it("new topRecord should not be empty") {
                    topRecordRepository.findAll().size shouldNotBe 0
                }
                it("topRecord should not have same name") {
                    val topRecords = topRecordRepository.findAll()
                    val names = topRecords.map { it.name }
                    names.toSet().size shouldBe names.size
                }
            }
        }
    }


    fun saveOriginalData() {
        val ipf = federationRepository.save(
            Federation(name = "IPF")
        )
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
        val testGame2 = gameRepository.save(Game(
            event = "Test Event2",
            country = "Korea",
            state = "Seoul",
            date = LocalDate.now(),
            meetCountry = "Korea",
            meetState = "Seoul",
            meetTown = "Seoul",
            meetName = "Test Meet2",
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
        val austinGameRecord2 = athleteGameRecordRepository.save(AthleteGameRecord(
            athlete = austin,
            game = testGame2,
            age = 30f,
            bodyWeight = 66.0,
            bestSquat = 310f,
            bestBench = 210f,
            bestDeadlift = 410f,
            total = 930f,
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
        val taylorGameRecord2 = athleteGameRecordRepository.save(AthleteGameRecord(
            athlete = taylor,
            game = testGame2,
            age = 30f,
            bodyWeight = 68.0,
            bestSquat = 310f,
            bestBench = 210f,
            bestDeadlift = 310f,
            total = 830f,
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
    fun saveTopRecordData() {
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
}