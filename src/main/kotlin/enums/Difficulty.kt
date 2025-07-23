package enums

enum class Difficulty {
    EASY,
    MEDIUM,
    HARD,
    EXPERT,
    UNSPECIFIED;

    fun displayName() : String {
        return when (this) {
            EASY -> "Easy"
            MEDIUM -> "Medium"
            HARD -> "Hard"
            EXPERT -> "Expert"
            UNSPECIFIED -> "Unspecified"
        }
    }
}