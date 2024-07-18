package chaegang.pl_api.domain

import jakarta.persistence.*
import java.time.LocalTime

@Entity
class Game (
    val event: String? = null,
    val country: String? = null,
    val state: String? = null,
    val date: LocalTime? = null,
    val meetCountry: String? = null,
    val meetState: String? = null,
    val meetTown: String? = null,
    val meetName: String? = null,
    @ManyToOne
    @JoinColumn(name = "federation_id")
    val federation: Federation,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
}