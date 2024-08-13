package chaegang.pl_api.domain.statistics.dto

import java.time.LocalDate

class BenchResultDto (
    name: String,
    sex: String?,
    bodyWeight: Float?,
    age: Float?,
    total: Int?,
    dots: Double?,
    wilks: Double?,
    glossbrenner: Double?,
    goodlift: Double?,
    tested: Boolean?,
    sanctioned: Boolean?,
    date: LocalDate?,
    federationName: String?,
    parentFederationName: String?,
    val bench: Double?
) : BaseResultDto(name,sex,bodyWeight,age,total,dots,wilks,glossbrenner,goodlift,tested,sanctioned,date,federationName,parentFederationName)
