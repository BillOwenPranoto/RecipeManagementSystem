package classes

import enums.MeasurementUnit
import utilities.UnitConverter

data class ShoppingItem(val ingredient: Ingredient, var totalAmount: Double, var unit: MeasurementUnit) {

    fun getIngredient() : Ingredient =
        ingredient

    fun getTotalAmount() : Double =
        totalAmount

    fun setTotalAmount(amount: Double) {
        if (isValidAmount(amount)) {
            totalAmount = amount
        }
        else {
            println("Can't insert 0 amount for the ingredient!")
        }
    }

    fun getUnit() : MeasurementUnit =
        unit

    fun setUnit(unit: MeasurementUnit) {
        this.unit = unit
    }

    fun getDisplayString() : String {
        val sb = StringBuilder()

        sb.append("\nIngredient: ${getIngredient()}\n")
        sb.append("Amount: ${getTotalAmount()} ${getUnit()}\n")

        return sb.toString()
    }

    fun convertUnit(unit : MeasurementUnit) : ShoppingItem {
        require(this.unit.canConvertTo(unit)) {
            "Cannot convert from ${this.unit} to $unit"
        }

        val converted = UnitConverter.convert(totalAmount,this.unit,unit)

        return ShoppingItem (ingredient = getIngredient(), totalAmount = converted, unit = unit)
    }

    fun canCombineWith(item: ShoppingItem) : Boolean {
        return this.ingredient == item.ingredient &&
                this.unit.canConvertTo(item.getUnit())
    }

    fun combine(other: ShoppingItem) : ShoppingItem {
        require(this.canCombineWith(other)) {
            "Cannot combine this item as they either are not of the same type or unpairable units!"
        }

        val converted = UnitConverter.convert(other.getTotalAmount(),other.unit,this.unit)

        return ShoppingItem(this.getIngredient(),this.totalAmount + converted,this.getUnit())
    }

    fun isValidAmount(amount: Double) : Boolean {
        return amount > 0 && amount.isFinite()
    }
}