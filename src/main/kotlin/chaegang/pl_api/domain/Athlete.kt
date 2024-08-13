package chaegang.pl_api.domain

import jakarta.persistence.*

@Entity
@Table(
    indexes = [Index(name="idx_athlete_name", columnList = "name", unique = true)],
)
class Athlete(
    @Column(unique = true)
    val name: String,
    val sex : String? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
}