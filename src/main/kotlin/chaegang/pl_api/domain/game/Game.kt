package chaegang.pl_api.domain.game

import chaegang.pl_api.domain.federation.Federation
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(
    indexes = [Index(name="idx_date_meet_name", columnList = "date, meet_name", unique = true)],
)
class Game (
    val event: String? = null,
    val country: String? = null,
    val state: String? = null,
    val date: LocalDate? = null,
    val meetCountry: String? = null,
    val meetState: String? = null,
    val meetTown: String? = null,
    val meetName: String? = null,
    @ManyToOne
    @JoinColumn(name = "federation_id", nullable = false)
    val federation: Federation,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
}