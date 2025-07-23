package enums

enum class Cuisine {
    ITALIAN,
    INDONESIAN,
    CHINESE,
    MEXICAN,
    INDIAN,
    AMERICAN,
    FRENCH,
    OTHER,
    UNKNOWN;

    fun displayName(): String {
        return when (this) {
            ITALIAN -> "Italian"
            INDONESIAN -> "Indonesian"
            CHINESE -> "Chinese"
            MEXICAN -> "Mexican"
            INDIAN -> "Indian"
            AMERICAN -> "American"
            FRENCH -> "French"
            OTHER -> "Other"
            UNKNOWN -> "Unknown"
        }
    }
}

