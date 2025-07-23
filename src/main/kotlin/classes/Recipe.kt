package classes
import enums.Cuisine
import enums.Difficulty
import enums.IngredientCategory

data class Recipe(var id: Int, var name: String,
             var description: String, val servings: Int, var prepTimeMinutes: Int,
             var cookTimeMinutes: Int, var rating: Int?, var notes: String?,
             private val _ingredients: List<RecipeIngredient> = emptyList(),
             private val _instructions: List<String> = emptyList(),
             private val _tags: Set<String> = emptySet(),
             var difficulty: Difficulty = Difficulty.UNSPECIFIED,
             var cuisine: Cuisine = Cuisine.UNKNOWN) {

    val totalTimeMinutes:Int = prepTimeMinutes + cookTimeMinutes

    val ingredients: List<RecipeIngredient> get() = _ingredients
    val instructions: List<String> get() = _instructions
    val tags: Set<String> get() = _tags

    fun getName(): String {
        return "Recipe's name: $name";
    }

    fun getDescription(): String{
        return description;
    }

    fun getServings(): Int {
        return servings;
    }

    fun getId(): Int {
        return id;
    }

    fun setRating(rate: Int?) {
        this.rating = rate
    }

    fun getRating(): Int? {
        return rating
    }

    fun setNotes(notes: String?) {
        this.notes = notes
    }

    fun getNotes() : String? {
        return notes
    }

    fun setDifficulty(difficulty: Difficulty) {
        this.difficulty = difficulty
    }

    fun getDifficulty(): Difficulty {
        return difficulty
    }

    fun setCuisine(cuisine: Cuisine) {
        this.cuisine = cuisine
    }

    fun getCuisine() : Cuisine {
        return cuisine
    }

    fun getTotalTimeMinutes(): Int {
        return totalTimeMinutes
    }

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

        return copy(_instructions = _instructions + instruction)
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
        return _tags.contains(tag)
    }

    fun addTag(tag: String): Recipe {
        if(this.hasTag(tag)) {
            println("Recipe has already been tagged with this!")
            return this
        }

        return copy(_tags = _tags + tag)
    }

    fun removeTag(tag: String): Recipe {
        return copy(_tags = _tags-tag)
    }

    fun isQuickMeal(): Boolean {
        if (getTotalTimeMinutes() < 20) {
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
            if (recipe.ingredient.getCategory() in nonVegetarianCategories) {
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
            if(recipe.ingredient.getCategory() in nonVeganCategories) {
                return false;
            }
        }

        return true;
    }

    fun isValidRating(rating: Double?): Boolean {
        if (rating != null) {
            if (rating > 0.0 || rating < 10.0) {
                return true
            }
        }
        return false
    }

}
