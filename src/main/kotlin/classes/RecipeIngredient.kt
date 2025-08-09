package classes
import enums.MeasurementUnit

data class RecipeIngredient(val ingredient: Ingredient, val amount: Double,
                       var notes: String?, val unit: MeasurementUnit) {


}