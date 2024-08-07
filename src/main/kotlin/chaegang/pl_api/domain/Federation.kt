package chaegang.pl_api.domain

import jakarta.persistence.*

@Entity
class Federation (
    @Column(unique = true)
    val  name: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_federation_id")
    val parentFederation: Federation? = null
){
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
}