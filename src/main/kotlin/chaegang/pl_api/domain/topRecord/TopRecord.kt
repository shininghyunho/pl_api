package chaegang.pl_api.domain.topRecord

import jakarta.persistence.*

/*
    val name: String?,
    val total: Float?,
    val squat: Float?,
    val bench: Float?,
    val deadlift: Float?,
    val sex: String?,
    val bodyWeight: Double?,
    val age: Float?,
    val dots: Double?,
    val wilks: Double?,
    val glossbrenner: Double?,
    val goodlift: Double?,
    val tested: Boolean?,
    val sanctioned: Boolean?,
    val date: LocalDate?,
    val federationName: String?,
    val parentFederationName: String?,
 */
@Entity
class TopRecord (
    @Id
    val name: String,
    val total: Float? = null,
    val squat: Float? = null,
    val bench: Float? = null,
    val deadlift: Float? = null,
    val sex: String? = null,
    val bodyWeight: Double? = null,
    val age: Float? = null,
    val dots: Double? = null,
    val wilks: Double? = null,
    val glossbrenner: Double? = null,
    val goodlift: Double? = null,
    val tested: Boolean? = null,
    val sanctioned: Boolean? = null,
    val date: String? = null,
    val federationName: String? = null,
    val parentFederationName: String? = null,
)