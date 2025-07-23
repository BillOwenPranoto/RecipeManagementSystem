package classes
import enums.MeasurementUnit
import jdk.jfr.DataAmount

data class RecipeIngredient(val ingredient: Ingredient, val amount: Double,
                       var notes: String?, val unit: MeasurementUnit) {


    fun getIngredient() : Ingredient {
        return ingredient
    }

    fun getAmount(): Double {
        return amount
    }

    fun setNotes(note: String) {
         notes += note
    }

    fun getNotes() : String? {
        return notes
    }

    fun getMeasurementUnit(): MeasurementUnit {
        return unit
    }

}