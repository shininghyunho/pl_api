package chaegang.pl_api.domain.topRecord.originalRecord

import chaegang.pl_api.domain.athlete.SexType
import chaegang.pl_api.domain.athleteGameRecord.EquipmentType
import chaegang.pl_api.domain.topRecord.dto.TopRecordQueryResult
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.TypedQuery
import org.springframework.stereotype.Repository

@Repository
class OriginalRecordRepository {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    fun findTopRecords(
        minExclusiveBodyWeight: Double,
        maxInclusiveBodyWeight: Double,
        equipmentType: EquipmentType,
        sexType: SexType,
        limit: Int = 10
    ): List<TopRecordQueryResult> {
        val equipment = equipmentType.toOriginalName()
        val sex = sexType.toOriginalName()
        // validate parameters
        if(limit<1) return emptyList()

        val query:TypedQuery<TopRecordQueryResult> = entityManager.createQuery(
            """
            SELECT new chaegang.pl_api.domain.topRecord.dto.TopRecordQueryResult(
                a.name,
                r.equipment,
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
            AND r.total =(
                SELECT MAX(r2.total)
                FROM AthleteGameRecord r2
                WHERE r2.athlete.id = a.id
            )
            ORDER BY r.total DESC
        """, TopRecordQueryResult::class.java)

        // set parameters
        query.setParameter("minInclusiveBodyWeight", minExclusiveBodyWeight)
        query.setParameter("maxInclusiveBodyWeight", maxInclusiveBodyWeight)
        query.setParameter("equipment", equipment)
        query.setParameter("sex",sex)
        query.maxResults = limit
        return query.resultList
    }
}