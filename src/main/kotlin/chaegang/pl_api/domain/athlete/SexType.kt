package chaegang.pl_api.domain.athlete

enum class SexType {
    F, M, MX;

    fun toOriginalName(): String {
        return when (this) {
            F -> "F"
            M -> "M"
            MX -> "Mx"
        }
    }
}