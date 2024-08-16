package chaegang.pl_api.domain.statistics.dto

import chaegang.pl_api.domain.athlete.SexType
import chaegang.pl_api.domain.athleteGameRecord.EquipmentType

class StatisticsRequestDto(
    val minBodyWeight: Double,
    val maxBodyWeight: Double,
    val equipmentType: EquipmentType,
    val sexType: SexType,
    val limit: Int
)