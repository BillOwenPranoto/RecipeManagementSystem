package classes

import enums.IngredientCategory

data class Ingredient(val id:Int, val name: String, val category: IngredientCategory) {

    fun isVegetarianFriendly(): Boolean {
        val nonVegetarianCategories = setOf(
            IngredientCategory.MEAT,
            IngredientCategory.POULTRY,
            IngredientCategory.SEAFOOD,
            IngredientCategory.ANIMAL_FAT
        )

        return category !in nonVegetarianCategories
    }

    fun isVeganFriendly() : Boolean {
        val nonVeganCategories = setOf(
            IngredientCategory.MEAT,
            IngredientCategory.POULTRY,
            IngredientCategory.SEAFOOD,
            IngredientCategory.ANIMAL_FAT,
            IngredientCategory.EGGS,
            IngredientCategory.DAIRY
        )

        return category !in nonVeganCategories
    }

    override fun toString(): String {
        val sb = StringBuilder()

        sb.appendLine("Ingredient name: $name")
        sb.appendLine("ID: $id")
        sb.appendLine("Category: $category")

        return sb.toString()
    }
}