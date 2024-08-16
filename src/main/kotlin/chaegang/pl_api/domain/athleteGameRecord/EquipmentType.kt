package chaegang.pl_api.domain.athleteGameRecord

enum class EquipmentType {
    MULTI_PLY, RAW, SINGLE_PLY, STRAPS, UNLIMITED, WRAPS;

    fun toOriginalName(): String {
        return when (this) {
            MULTI_PLY -> "Multi_ply"
            RAW -> "Raw"
            SINGLE_PLY -> "Single_ply"
            STRAPS -> "Straps"
            UNLIMITED -> "Unlimited"
            WRAPS -> "Wraps"
        }
    }
}