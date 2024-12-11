package chaegang.pl_api.domain.topRecord

import chaegang.pl_api.domain.athlete.SexType
import chaegang.pl_api.domain.athleteGameRecord.EquipmentType
import chaegang.pl_api.domain.topRecord.originalRecord.OriginalRecordRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * OriginalRecord 로 부터 TopRecord 를 업데이트하는 서비스
 */

// 남자 체급 리스트
val maleWeightClasses = listOf(0.0,59.0, 66.0, 74.0, 83.0, 93.0, 105.0, 120.0, 1000.0)
// 여자 체급 리스트
val femaleWeightClasses = listOf(0.0,47.0, 52.0, 57.0, 63.0, 72.0, 84.0, 1000.0)
// 장비 리스트
val equipmentTypes = listOf(EquipmentType.RAW, EquipmentType.SINGLE_PLY)
@Service
class TopRecordUpdater (
    private val topRecordRepository: TopRecordRepository,
    private val originalRecordRepository: OriginalRecordRepository,
) {
    // update 로직
    @Transactional
    fun updateTopRecord() {
        // deleteAll 은 하나씩 삭제해서 느림
        topRecordRepository.truncateTable()
        saveMaleTopRecord()
        saveFemaleTopRecord()
    }
    private fun saveMaleTopRecord() {
        equipmentTypes.forEach { equipmentType ->
            // male
            for(i in 0 until maleWeightClasses.size-1) {
                val minExclusiveBodyWeight = maleWeightClasses[i]
                val maxInclusiveBodyWeight = maleWeightClasses[i+1]
                val topRecordResults = originalRecordRepository.findTopRecords(
                    minExclusiveBodyWeight = minExclusiveBodyWeight,
                    maxInclusiveBodyWeight = maxInclusiveBodyWeight,
                    sexType = SexType.M,
                    equipmentType = equipmentType,
                )
                // save
                topRecordResults.forEach { topRecordResult ->
                    val topRecord = topRecordResult.toTopRecord()
                    topRecord?.let { topRecordRepository.save(it) }
                }
            }
        }
    }
    private fun saveFemaleTopRecord() {
        equipmentTypes.forEach { equipmentType ->
            // female
            for (i in 0 until femaleWeightClasses.size - 1) {
                val minExclusiveBodyWeight = femaleWeightClasses[i]
                val maxInclusiveBodyWeight = femaleWeightClasses[i + 1]
                val topRecordResults = originalRecordRepository.findTopRecords(
                    minExclusiveBodyWeight = minExclusiveBodyWeight,
                    maxInclusiveBodyWeight = maxInclusiveBodyWeight,
                    sexType = SexType.F,
                    equipmentType = equipmentType,
                )
                // save
                topRecordResults.forEach { topRecordResult ->
                    val topRecord = topRecordResult.toTopRecord()
                    topRecord?.let { topRecordRepository.save(it) }
                }
            }
        }
    }
}