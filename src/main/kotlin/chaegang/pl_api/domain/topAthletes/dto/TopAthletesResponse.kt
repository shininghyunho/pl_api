package chaegang.pl_api.domain.topAthletes.dto

class TopAthletesResponse(
    val topAthletes: List<TopAthlete>
) {
    companion object {
        fun fromAthleteResultDtoList(dtoList : List<TopAthleteQueryResult>): TopAthletesResponse {
            return TopAthletesResponse(
                topAthletes = dtoList.map {
                    TopAthlete(
                        name = it.name,
                        total = it.total,
                        squat = it.squat,
                        bench = it.bench,
                        deadlift = it.deadlift,
                        sex = it.sex,
                        bodyWeight = it.bodyWeight,
                        age = it.age,
                        dots = it.dots,
                        wilks = it.wilks,
                        glossbrenner = it.glossbrenner,
                        goodlift = it.goodlift,
                        tested = it.tested,
                        sanctioned = it.sanctioned,
                        date = it.date.toString(),
                        federationName = it.federationName,
                        parentFederationName = it.parentFederationName
                    )
                }
            )
        }
    }
    class TopAthlete(
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
        val date: String,
        val federationName: String,
        val parentFederationName: String
    )
}