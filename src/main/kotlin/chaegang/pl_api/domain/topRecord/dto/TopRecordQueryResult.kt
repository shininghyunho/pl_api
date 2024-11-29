package chaegang.pl_api.domain.topRecord.dto

import chaegang.pl_api.domain.topRecord.TopRecord
import java.time.LocalDate

class TopRecordQueryResult (
    val name: String?=null,
    val equipment: String?=null,
    val total: Float?=null,
    val squat: Float?=null,
    val bench: Float?=null,
    val deadlift: Float?=null,
    val sex: String?=null,
    val bodyWeight: Double?=null,
    val age: Float?=null,
    val dots: Double?=null,
    val wilks: Double?=null,
    val glossbrenner: Double?=null,
    val goodlift: Double?=null,
    val tested: Boolean?=null,
    val sanctioned: Boolean?=null,
    val date: LocalDate?=null,
    val federationName: String?=null,
    val parentFederationName: String?=null,
) {
    override fun toString(): String {
        return "AthleteResultDto(name='$name', total=$total, squat=$squat, bench=$bench, deadlift=$deadlift, sex=$sex, bodyWeight=$bodyWeight, age=$age, dots=$dots, wilks=$wilks, glossbrenner=$glossbrenner, goodlift=$goodlift, tested=$tested, sanctioned=$sanctioned, date=$date, federationName=$federationName, parentFederationName=$parentFederationName)"
    }

    fun toTopRecord(): TopRecord? {
        if(name == null) return null
        return TopRecord(
                name = name,
                equipment = equipment,
                total = total,
                squat = squat,
                bench = bench,
                deadlift = deadlift,
                sex = sex,
                bodyWeight = bodyWeight,
                age = age,
                dots = dots,
                wilks = wilks,
                glossbrenner = glossbrenner,
                goodlift = goodlift,
                tested = tested,
                sanctioned = sanctioned,
                date = date.toString(),
                federationName = federationName,
                parentFederationName = parentFederationName
            )
    }
}