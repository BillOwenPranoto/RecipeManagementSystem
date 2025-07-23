package enums

enum class MeasurementUnit(val category: UnitCategory) {
    GRAMS(UnitCategory.MASS),
    KILOGRAMS(UnitCategory.MASS),
    CUPS(UnitCategory.VOLUME),
    TABLESPOONS(UnitCategory.VOLUME),
    TEASPOONS(UnitCategory.VOLUME),
    PIECES(UnitCategory.COUNT),
    MILLILITERS(UnitCategory.VOLUME),
    LITERS(UnitCategory.VOLUME);

    fun displayNames(): String {
        return when (this) {
            GRAMS -> "grams"
            KILOGRAMS -> "kilograms"
            CUPS -> "cups"
            TABLESPOONS -> "tablespoons"
            TEASPOONS -> "teaspoons"
            PIECES -> "pieces"
            MILLILITERS -> "mL"
            LITERS -> "L"
        }
    }

}