package chaegang.pl_api.domain

import jakarta.persistence.*

@Entity
class Federation (
    var name: String? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_federation_id")
    var parentFederation: Federation? = null
){
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
}