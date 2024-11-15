package chaegang.pl_api.domain.topAthletes

import chaegang.pl_api.domain.athlete.SexType
import chaegang.pl_api.domain.athleteGameRecord.EquipmentType
import chaegang.pl_api.domain.topAthletes.dto.TopAthleteQueryResult
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.TypedQuery
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

const val MAX_RESULT_LIMIT = 10

@Repository
class TopAthletesRepository(
    private val redisTemplate: RedisTemplate<String, Any>
) {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    fun findTopAthletes(
        minExclusiveBodyWeight: Double,
        maxInclusiveBodyWeight: Double,
        equipmentType: EquipmentType,
        sexType: SexType,
    ): List<TopAthleteQueryResult> {
        val equipment = equipmentType.toOriginalName()
        val sex = sexType.toOriginalName()

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
        query.maxResults = MAX_RESULT_LIMIT

        // get cache if exists
        val cacheKey = getCacheKey(minExclusiveBodyWeight,
            maxInclusiveBodyWeight,
            equipmentType,
            sexType)
        redisTemplate.opsForValue().get(cacheKey)?.let {
            return it as List<TopAthleteQueryResult>
        }


        // set cache if not exists
        val result = query.resultList
        redisTemplate.opsForValue().set(cacheKey, result)
        // expire in 7 days
        redisTemplate.expire(cacheKey,7,TimeUnit.DAYS)
        return result
    }

    private fun getCacheKey(
        minExclusiveBodyWeight: Double,
        maxInclusiveBodyWeight: Double,
        equipmentType: EquipmentType,
        sexType: SexType,
    ): String {
        return "topAthletes:${minExclusiveBodyWeight}:${maxInclusiveBodyWeight}:${equipmentType}:${sexType}"
    }
}