package chaegang.pl_api.domain.topRecord

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface TopRecordRepository : JpaRepository<TopRecord, String> {
    @Query(
        value = """
            SELECT * FROM TopRecord
            WHERE bodyWeight > :minExclusiveBodyWeight
            AND bodyWeight <= :maxInclusiveBodyWeight
            AND equipment = :equipmentType
            AND sex = :sexType
            ORDER BY total DESC
            LIMIT :limit
        """, nativeQuery = true
    )
    fun findTopRecords(
        @Param("minExclusiveBodyWeight") minExclusiveBodyWeight: Double,
        @Param("maxInclusiveBodyWeight") maxInclusiveBodyWeight: Double,
        @Param("equipmentType") equipmentType: String,
        @Param("sexType") sexType: String,
        @Param("limit") limit: Int
    ): List<TopRecord>
}