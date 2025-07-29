package classes
import enums.MeasurementUnit
import jdk.jfr.DataAmount

data class RecipeIngredient(val ingredient: Ingredient, val amount: Double,
                       var notes: String?, val unit: MeasurementUnit) {


}