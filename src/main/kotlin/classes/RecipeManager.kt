package classes

import enums.Cuisine
import enums.Difficulty
import enums.IngredientCategory

class RecipeManager {
    val recipes: MutableMap<Int, Recipe> = mutableMapOf()
    val ingredients: MutableMap<Int, Ingredient> = mutableMapOf()

    var nextRecipeId = 1
    var nextIngredientId = 1

    fun addRecipe(recipe: Recipe) {
        if (recipe !in recipes.values) {
            recipes.put(this.getRecipeNextId(), recipe)
            nextRecipeId++
        } else {
            println("Recipe already exists!")
            return
        }
    }

    fun updateRecipe(id: Int, updated: Recipe) {
        if (recipes.contains(id)) {
            recipes.replace(id, updated)
        } else {
            println("No recipe with this id! Please double check")
            return
        }
    }

    fun deleteRecipe(id: Int) {
        if (recipes.contains(id)) {
            recipes.remove(id)
        } else {
            println("Recipe either has already been deleted before or never exists")
            return
        }
    }

    fun getRecipe(id: Int): Recipe =
         recipes[id] ?: throw NoSuchElementException("No recipe with ID $id")


    fun getRecipeCount(): Int =
         recipes.size


    fun getRecipeNextId(): Int =
         nextRecipeId


    fun getAllRecipes(): List<Recipe> =
         recipes.values.toList()


    fun searchRecipesByName(name: String): List<Recipe> =
        recipes.values.filter {it.name.equals(name,ignoreCase = true) }.toList()


    fun searchRecipesByIngredient(byIngredient: Ingredient): List<Recipe> =
        recipes.values.filter { it.ingredients == byIngredient }.toList()


    fun getRecipesByTag(byTag: String): List<Recipe> =
        recipes.values.filter { it.tags.contains(byTag) }

    fun filterRecipeByDifficulty(difficulty: Difficulty) : List<Recipe> =
         recipes.values.filter { it.getDifficulty() == difficulty }.toList()


    fun filterRecipeByCuisine(cuisine: Cuisine) : List<Recipe> =
         recipes.values.filter { it.getCuisine() == cuisine }.toList()


    fun filterRecipeByRating(rating: Int) : List<Recipe> =
         recipes.values.filter {it.getRating() == rating}.toList()


    fun addIngredient(ingredient: Ingredient) {
        ingredients.put(this.getNextIngredientId(), ingredient)
        nextIngredientId++
    }

    fun updateIngredient(id: Int, updated: Ingredient) {
        if (ingredients.contains(id)) {
            ingredients.replace(id, updated)
        } else {
            println("No ingredient with this id! Please double check")
            return
        }
    }

    fun deleteIngredient(id: Int) {
        if (ingredients.contains(id)) {
            ingredients.remove(id)
        } else {
            println("Ingredient either has already been deleted before or never exists")
            return
        }
    }

    fun getIngredient(id: Int) : Ingredient? =
         ingredients[id] ?: throw NoSuchElementException("No ingredients with such ID: $id")


    fun getAllIngredients() : List<Ingredient> =
         ingredients.values.toList()


    fun getNextIngredientId() : Int  =
        nextIngredientId

    fun getIngredientCount() : Int =
        ingredients.size

    fun searchIngredient(name: String): List<Ingredient> =
        ingredients.values.filter { it.name.equals(name,ignoreCase = true) }.toList()

    fun getIngredientsByCategory(category: IngredientCategory): List<Ingredient> =
        ingredients.values.filter { it.getCategory() == category }.toList()

    fun handleUserInput(input: String) {

    }

    fun showMenu() {
        println("====================================")
        println("Welcome to Recipe Manager! What can I do for you? ")
        println("1. Create a recipe.")
        println("2. Update a recipe.")
    }

    fun run() {

    }
}