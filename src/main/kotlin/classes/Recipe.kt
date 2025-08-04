package classes
import enums.Cuisine
import enums.Difficulty
import enums.IngredientCategory

data class Recipe(var id: Int, var name: String,
             var description: String, val servings: Int, var prepTimeMinutes: Int,
             var cookTimeMinutes: Int, var rating: Int?, var difficulty: Difficulty,
                  var cuisine: Cuisine, var notes: String?,
                    private val _ingredients: List<RecipeIngredient> = emptyList(),
                    private val _instructions: List<String> = emptyList(),
                    private val _tags: Set<String> = emptySet(),
             )
 {
     init {
         require(!(name.isEmpty() || name.isBlank())) { "Name can't be empty!"}
         require(servings > 0 && prepTimeMinutes in 0..600 && cookTimeMinutes in 0..1440) {"Servings/Preparation Time/Cooking Time must be greater than 0!"}
     }

    val totalTimeMinutes:Int = prepTimeMinutes + cookTimeMinutes

    val ingredients: List<RecipeIngredient> get() = _ingredients
    val instructions: List<String> get() = _instructions
    val tags: Set<String> get() = _tags

    fun addIngredient(ingredient: RecipeIngredient): Recipe {

        if(_ingredients.contains(ingredient)) {
            println("This recipe contains this ingredient already!")
            return this
        }
        return copy(_ingredients = _ingredients + ingredient)
    }

    fun removeIngredient(ingredientId: Int) : Recipe {

        return copy(_ingredients = _ingredients.filterNot({it.ingredient.id == ingredientId}))
    }

    fun addInstruction(instruction: String): Recipe {
        if(_instructions.contains(instruction)) {
            println("This instruction already exists!")
            return this
        }

        return copy(_instructions = _instructions + instruction.trim())
    }

    fun updateInstruction(instructionId: Int, updated: String): Recipe {
        if (instructionId !in _instructions.indices) {
            println("Invalid instruction ID")
            return this
        }

        val updatedInstructions = _instructions.toMutableList()
        updatedInstructions[instructionId] = updated

        return copy(_instructions = updatedInstructions)
    }

    fun hasTag(tag: String) : Boolean {
        return _tags.contains(tag.trim())
    }

    fun addTag(tag: String): Recipe {
        if(this.hasTag(tag.trim())) {
            println("Recipe has already been tagged with this!")
            return this
        }

        return copy(_tags = _tags + tag.trim())
    }

    fun removeTag(tag: String): Recipe {
        return copy(_tags = _tags-tag.trim())
    }

    fun isQuickMeal(): Boolean {
        if (this.totalTimeMinutes < 20) {
            return true;
        } else {
            return false;
        }
    }

    fun isVegetarianFriendly(): Boolean {
        val nonVegetarianCategories = setOf(
            IngredientCategory.MEAT,
            IngredientCategory.POULTRY,
            IngredientCategory.SEAFOOD,
            IngredientCategory.ANIMAL_FAT
        )

        for (recipe : RecipeIngredient in _ingredients) {
            if (recipe.ingredient.category in nonVegetarianCategories) {
                return false
            }
        }

        return true
    }

    fun isVeganFriendly(): Boolean {
        val nonVeganCategories = setOf(
            IngredientCategory.MEAT,
            IngredientCategory.POULTRY,
            IngredientCategory.SEAFOOD,
            IngredientCategory.DAIRY,
            IngredientCategory.EGGS,
            IngredientCategory.ANIMAL_FAT
        )

        for (recipe : RecipeIngredient in _ingredients) {
            if(recipe.ingredient.category in nonVeganCategories) {
                return false;
            }
        }

        return true;
    }

    fun isValidRating(rating: Int?): Boolean {
        if (rating != null) {
            if (rating > 0 && rating < 10) {
                return true
            }
        }
        return false
    }

     fun isValidCookingTime(time: Int): Boolean {
         return time in 0..1440
     }

     fun isValidPrepTime(time: Int): Boolean {
         return time in 0..600
     }

     override fun toString(): String {
         val sb = StringBuilder()

         sb.appendLine("Recipe: $name")
         sb.appendLine("ID: $id")
         sb.appendLine("Description: $description")
         sb.appendLine("Servings: $servings")
         sb.appendLine("Prep Time: $prepTimeMinutes minutes")
         sb.appendLine("Cook Time: $cookTimeMinutes minutes")
         sb.appendLine("Rating: ${rating ?: "N/A"}")
         sb.appendLine("Cuisine: $cuisine")
         sb.appendLine("Difficulty: $difficulty")

         sb.appendLine("\nIngredients:")
         _ingredients.forEachIndexed { index, ingredient ->
             sb.appendLine("  ${index + 1}. $ingredient")
         }

         sb.appendLine("\nInstructions:")
         _instructions.forEachIndexed { index, step ->
             sb.appendLine("  Step ${index + 1}: $step")
         }

         if (_tags.isNotEmpty()) {
             sb.appendLine("\nTags: ${_tags.joinToString(", ")}")
         }

         if (!notes.isNullOrBlank()) {
             sb.appendLine("\nNotes: $notes")
         }

         return sb.toString()
     }
}
