package chaegang.pl_api.domain.statistics

import chaegang.pl_api.domain.athlete.SexType
import chaegang.pl_api.domain.athleteGameRecord.EquipmentType
import chaegang.pl_api.domain.statistics.dto.AthleteResultDto
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.TypedQuery
import org.springframework.stereotype.Repository

@Repository
class StatisticsRepository {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    fun findTopAthletes(
        minBodyWeight: Double,
        maxBodyWeight: Double,
        equipmentType: EquipmentType,
        sexType: SexType,
        limit: Int = 10
    ): List<AthleteResultDto> {
        val equipment = equipmentType.toOriginalName()
        val sex = sexType.toOriginalName()
        // validate parameters
        if(limit<1) return emptyList()

        val query:TypedQuery<AthleteResultDto> = entityManager.createQuery("""
            SELECT new chaegang.pl_api.domain.statistics.dto.AthleteResultDto(
                a.name,
                r.total,
                r.bestSquat,
                r.bestBench,
                r.bestDeadlift,
                a.sex,
                r.bodyWeight,
                r.age,
                r.dots,
                r.wilks,
                r.glossbrenner,
                r.goodlift,
                r.tested,
                r.sanctioned,
                g.date,
                f.name,
                pf.name
            )
            FROM Athlete a
            JOIN AthleteGameRecord r ON a.id = r.athlete.id
            JOIN Game g ON r.game.id = g.id
            JOIN Federation f ON g.federation.id = f.id
            JOIN Federation pf ON f.parentFederation.id = pf.id
            WHERE r.bodyWeight > :minBodyWeight AND r.bodyWeight <= :maxBodyWeight
            AND r.equipment = :equipment
            AND a.sex = :sex
            ORDER BY r.total DESC
        """, AthleteResultDto::class.java)

        // set parameters
        query.setParameter("minBodyWeight", minBodyWeight)
        query.setParameter("maxBodyWeight", maxBodyWeight)
        query.setParameter("equipment", equipment)
        query.setParameter("sex",sex)
        query.maxResults = limit
        return query.resultList
    }
}