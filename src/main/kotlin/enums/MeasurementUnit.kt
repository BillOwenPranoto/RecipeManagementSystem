package enums

enum class MeasurementUnit(val category: UnitCategory, val conversionFactor: Double) {
    GRAMS(UnitCategory.MASS, 1.0),
    KILOGRAMS(UnitCategory.MASS, 1000.0),
    CUPS(UnitCategory.VOLUME, 240.0),
    TABLESPOONS(UnitCategory.VOLUME, 15.0),
    TEASPOONS(UnitCategory.VOLUME, 5.0),
    PIECES(UnitCategory.COUNT, 1.0),
    MILLILITERS(UnitCategory.VOLUME, 1.0),
    LITERS(UnitCategory.VOLUME, 1000.0);

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

    fun getAllValues() : List<MeasurementUnit> =
        MeasurementUnit.entries.toList()


    fun canConvertTo(unit: MeasurementUnit) : Boolean =
         this.category == unit.category


    fun conversionFactor(unit: MeasurementUnit) : Double =
        unit.conversionFactor

}