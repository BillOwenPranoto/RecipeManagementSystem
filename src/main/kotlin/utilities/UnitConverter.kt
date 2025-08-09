package utilities

import enums.MeasurementUnit

object UnitConverter {

    fun convert(
        amount: Double,
        fromUnit: MeasurementUnit,
        toUnit: MeasurementUnit
    ): Double {
        require(fromUnit.category == toUnit.category) {
            "Cannot convert between different unit categories: ${fromUnit.category} and ${toUnit.category}"
        }

        // Define base conversion factors (to grams or milliliters)
        val conversionToBase = mapOf(
            // Mass
            MeasurementUnit.GRAMS to 1.0,
            MeasurementUnit.KILOGRAMS to 1000.0,

            // Volume
            MeasurementUnit.MILLILITERS to 1.0,
            MeasurementUnit.LITERS to 1000.0,
            MeasurementUnit.CUPS to 240.0,
            MeasurementUnit.TABLESPOONS to 15.0,
            MeasurementUnit.TEASPOONS to 5.0,
            MeasurementUnit.PIECES to 1.0
        )

        val fromFactor = conversionToBase[fromUnit]
            ?: throw IllegalArgumentException("Unsupported from-unit: $fromUnit")

        val toFactor = conversionToBase[toUnit]
            ?: throw IllegalArgumentException("Unsupported to-unit: $toUnit")

        // Convert to base unit, then to target unit
        val baseAmount = amount * fromFactor
        return baseAmount / toFactor
    }

    fun canConvertTo(fromUnit: MeasurementUnit, toUnit: MeasurementUnit): Boolean {
        return fromUnit.category == toUnit.category
    }
}