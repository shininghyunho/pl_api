package chaegang.pl_api.domain.statistics.dto

import java.time.LocalDate

open class BaseResultDto (
    val name: String,
    val sex: String?,
    val bodyWeight: Float?,
    val age: Float?,
    val total: Int?,
    val dots: Double?,
    val wilks: Double?,
    val glossbrenner: Double?,
    val goodlift: Double?,
    val tested: Boolean?,
    val sanctioned: Boolean?,
    val date: LocalDate?,
    val federationName: String?,
    val parentFederationName: String?
)