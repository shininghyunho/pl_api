package chaegang.pl_api.domain.topAthletes.dto

class TopAthletesResponse(
    val topAthletes: List<TopAthlete>
) {
    companion object {
        fun fromAthleteResultDtoList(dtoList : List<TopAthleteQueryResult>): TopAthletesResponse {
            return TopAthletesResponse(
                topAthletes = dtoList.map {
                    TopAthlete(
                        name = it.name ?: "",
                        total = it.total ?: 0f,
                        squat = it.squat ?: 0f,
                        bench = it.bench ?: 0f,
                        deadlift = it.deadlift ?: 0f,
                        sex = it.sex ?: "",
                        bodyWeight = it.bodyWeight ?: 0.0,
                        age = it.age ?: 0f,
                        dots = it.dots ?: 0.0,
                        wilks = it.wilks ?: 0.0,
                        glossbrenner = it.glossbrenner ?: 0.0,
                        goodlift = it.goodlift ?: 0.0,
                        tested = it.tested ?: false,
                        sanctioned = it.sanctioned ?: false,
                        date = it.date.toString(),
                        federationName = it.federationName ?: "",
                        parentFederationName = it.parentFederationName ?: "",
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