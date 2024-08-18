package chaegang.pl_api.domain.topAthletes

import chaegang.pl_api.domain.athlete.SexType
import chaegang.pl_api.domain.athleteGameRecord.EquipmentType
import chaegang.pl_api.domain.topAthletes.dto.TopAthleteQueryResult
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.TypedQuery
import org.springframework.stereotype.Repository

@Repository
class TopAthletesRepository {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    fun findTopAthletes(
        minExclusiveBodyWeight: Double,
        maxInclusiveBodyWeight: Double,
        equipmentType: EquipmentType,
        sexType: SexType,
        limit: Int = 10
    ): List<TopAthleteQueryResult> {
        val equipment = equipmentType.toOriginalName()
        val sex = sexType.toOriginalName()
        // validate parameters
        if(limit<1) return emptyList()

        val query:TypedQuery<TopAthleteQueryResult> = entityManager.createQuery(
            """
            SELECT new chaegang.pl_api.domain.topAthletes.dto.TopAthleteQueryResult(
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
            WHERE r.bodyWeight > :minInclusiveBodyWeight AND r.bodyWeight <= :maxInclusiveBodyWeight
            AND r.equipment = :equipment
            AND a.sex = :sex
            ORDER BY r.total DESC
        """, TopAthleteQueryResult::class.java)

        // set parameters
        query.setParameter("minInclusiveBodyWeight", minExclusiveBodyWeight)
        query.setParameter("maxInclusiveBodyWeight", maxInclusiveBodyWeight)
        query.setParameter("equipment", equipment)
        query.setParameter("sex",sex)
        query.maxResults = limit
        return query.resultList
    }
}