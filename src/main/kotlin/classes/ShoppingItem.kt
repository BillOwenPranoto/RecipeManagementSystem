package classes

import enums.MeasurementUnit
import utilities.UnitConverter

data class ShoppingItem(val ingredient: Ingredient, var totalAmount: Double, var unit: MeasurementUnit) {


    fun getDisplayString() : String {
        val sb = StringBuilder()

        sb.append("\nIngredient: $ingredient\n")
        sb.append("Amount: $totalAmount $unit\n")

        return sb.toString()
    }

    fun convertUnit(unit : MeasurementUnit) : ShoppingItem {
        require(this.unit.canConvertTo(unit)) {
            "Cannot convert from ${this.unit} to $unit"
        }

        val converted = UnitConverter.convert(totalAmount,this.unit,unit)

        return ShoppingItem (ingredient = ingredient, totalAmount = converted, unit = unit)
    }

    fun canCombineWith(item: ShoppingItem) : Boolean {
        return this.ingredient == item.ingredient &&
                this.unit.canConvertTo(item.unit)
    }

    fun combine(other: ShoppingItem) : ShoppingItem {
        require(this.canCombineWith(other)) {
            "Cannot combine this item as they either are not of the same type or unpairable units!"
        }

        val converted = UnitConverter.convert(other.totalAmount,other.unit,this.unit)

        return ShoppingItem(this.ingredient,this.totalAmount + converted,this.unit)
    }

    fun isValidAmount(amount: Double) : Boolean {
        return amount > 0 && amount.isFinite()
    }
}