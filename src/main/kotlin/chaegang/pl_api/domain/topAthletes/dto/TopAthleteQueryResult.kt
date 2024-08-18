package chaegang.pl_api.domain.topAthletes.dto

import java.time.LocalDate
class TopAthleteQueryResult (
    val name: String,
    val total: Float,
    val squat: Float,
    val bench: Float,
    val deadlift: Float,
    val sex: String,
    val bodyWeight: Double,
    val age: Float,
    val dots: Double,
    val wilks: Double,
    val glossbrenner: Double,
    val goodlift: Double,
    val tested: Boolean,
    val sanctioned: Boolean,
    val date: LocalDate,
    val federationName: String,
    val parentFederationName: String
) {
    override fun toString(): String {
        return "AthleteResultDto(name='$name', total=$total, squat=$squat, bench=$bench, deadlift=$deadlift, sex=$sex, bodyWeight=$bodyWeight, age=$age, dots=$dots, wilks=$wilks, glossbrenner=$glossbrenner, goodlift=$goodlift, tested=$tested, sanctioned=$sanctioned, date=$date, federationName=$federationName, parentFederationName=$parentFederationName)"
    }
}