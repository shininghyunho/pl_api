package chaegang.pl_api.domain.topRecord.dto

import chaegang.pl_api.domain.athlete.SexType
import chaegang.pl_api.domain.athleteGameRecord.EquipmentType

class TopRecordRequest(
    val minExclusiveBodyWeight: Double,
    val maxInclusiveBodyWeight: Double,
    equipment:String,
    sex:String,
    val limit: Int = 10
) {
    val equipmentType: EquipmentType = EquipmentType.valueOf(equipment)
    val sexType: SexType = SexType.valueOf(sex)
}