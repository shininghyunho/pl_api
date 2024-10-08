package chaegang.pl_api.domain.athleteGameRecord

import chaegang.pl_api.domain.game.Game
import chaegang.pl_api.domain.athlete.Athlete
import jakarta.persistence.*

@Entity
@Table(
    indexes = [Index(name="idx_athlete_game", columnList = "athlete_id, game_id", unique = true)],
)
class AthleteGameRecord(
    val equipment: String? = null,
    val age:Float? = null,
    val ageClass: String? = null,
    val birthYearClass: String? = null,
    val division: String? = null,
    val bodyWeight: Double? = null,
    val weightClass: String? = null,
    val squat1: Float? = null,
    val squat2: Float? = null,
    val squat3: Float? = null,
    val squat4: Float? = null,
    val bestSquat: Float? = null,
    val bench1: Float? = null,
    val bench2: Float? = null,
    val bench3: Float? = null,
    val bench4: Float? = null,
    val bestBench: Float? = null,
    val deadlift1: Float? = null,
    val deadlift2: Float? = null,
    val deadlift3: Float? = null,
    val deadlift4: Float? = null,
    val bestDeadlift: Float? = null,
    val total: Float? = null,
    val place: Int? = null,
    val dots: Double? = null,
    val wilks: Double? = null,
    val glossbrenner: Double? = null,
    val goodlift: Double? = null,
    val tested: Boolean? = null,
    val sanctioned: Boolean? = null,
    @ManyToOne
    @JoinColumn(name = "athlete_id", nullable = false)
    val athlete: Athlete,
    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    val game: Game,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}