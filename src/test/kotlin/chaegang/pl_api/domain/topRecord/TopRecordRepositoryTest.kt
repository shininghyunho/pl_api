package chaegang.pl_api.domain.topRecord

import chaegang.pl_api.domain.athlete.SexType
import chaegang.pl_api.domain.athleteGameRecord.EquipmentType
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.doubles.shouldBeGreaterThan
import io.kotest.matchers.doubles.shouldBeLessThanOrEqual
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class TopRecordRepositoryTest (
    @Autowired val topRecordRepository: TopRecordRepository
) :ShouldSpec({
    // 각 Test Case 마다 Transactional 적용
    extensions(SpringTestExtension(SpringTestLifecycleMode.Root))

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

    context("findTopRecords success") {
        saveEntity()
        val minExclusiveBodyWeight = 70.0
        val maxInclusiveBodyWeight = 80.0
        val equipmentType = EquipmentType.RAW.toOriginalName()
        val sexType = SexType.M.toOriginalName()
        val response = topRecordRepository.findTopRecords(
            minExclusiveBodyWeight = minExclusiveBodyWeight,
            maxInclusiveBodyWeight = maxInclusiveBodyWeight,
            equipmentType = equipmentType,
            sexType = sexType
        )
        should("bodyWeight > minExclusiveBodyWeight") {
            response.forEach {
                it.bodyWeight!! shouldBeGreaterThan minExclusiveBodyWeight
            }
        }
        should("bodyWeight <= maxInclusiveBodyWeight") {
            response.forEach {
                it.bodyWeight!! shouldBeLessThanOrEqual maxInclusiveBodyWeight
            }
        }
        should("order by total DESC") {
            response.forEachIndexed { index, topRecord ->
                if(index < response.size - 1) {
                    topRecord.total!! shouldBeGreaterThan response[index + 1].total!!
                }
            }
        }
    }

})