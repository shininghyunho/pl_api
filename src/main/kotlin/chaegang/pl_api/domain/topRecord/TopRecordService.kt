package chaegang.pl_api.domain.topRecord

import chaegang.pl_api.domain.topRecord.dto.TopRecordQueryResult
import chaegang.pl_api.domain.topRecord.dto.TopRecordRequest
import chaegang.pl_api.domain.topRecord.dto.TopRecordResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TopRecordService(
    private val topRecordRepository: TopRecordRepository
) {
    @Transactional(readOnly = true)
    fun findTopRecord(request: TopRecordRequest): TopRecordResponse {
        val topRecordList= topRecordRepository.findTopRecords(
            minExclusiveBodyWeight = request.minExclusiveBodyWeight,
            maxInclusiveBodyWeight = request.maxInclusiveBodyWeight,
            equipmentType = request.equipmentType.toOriginalName(),
            sexType = request.sexType.toOriginalName(),
            limit = request.limit
        )
        return TopRecordResponse.fromTopRecordList(topRecordList)
    }

    @Transactional
    fun saveTopRecord(queryResult: TopRecordQueryResult) {
        // already exists name then return
        if(queryResult.name == null
            || topRecordRepository.findById(queryResult.name).isPresent) return
        // save
        topRecordRepository.save(TopRecord(
            name = queryResult.name,
            total = queryResult.total,
            squat = queryResult.squat,
            bench = queryResult.bench,
            deadlift = queryResult.deadlift,
            sex = queryResult.sex,
            bodyWeight = queryResult.bodyWeight,
            age = queryResult.age,
            dots = queryResult.dots,
            wilks = queryResult.wilks,
            glossbrenner = queryResult.glossbrenner,
            goodlift = queryResult.goodlift,
            tested = queryResult.tested,
            sanctioned = queryResult.sanctioned,
            date = queryResult.date.toString(),
            federationName = queryResult.federationName,
            parentFederationName = queryResult.parentFederationName
        ))
    }

    @Transactional
    fun deleteAllTopRecord() {
        topRecordRepository.deleteAll()
    }
}