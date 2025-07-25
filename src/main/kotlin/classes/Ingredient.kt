package classes

import enums.IngredientCategory

data class Ingredient(val id:Int, val name: String, val category: IngredientCategory) {

    fun getId() : Int {
        return id
    }

    fun getName(): String {
        return name
    }

    fun getCategory() : IngredientCategory {
        return category
    }

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
}