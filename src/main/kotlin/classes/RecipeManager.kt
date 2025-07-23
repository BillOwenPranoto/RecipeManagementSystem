package classes

import enums.Cuisine
import enums.Difficulty

class RecipeManager {
    val recipes: MutableMap<Int, Recipe> = mutableMapOf()
    val ingredients: MutableMap<Int, Ingredient> = mutableMapOf()

    var nextRecipeId = 1
    var nextIngredientId = 1

    fun addRecipe(recipe: Recipe) {
        if (recipe !in recipes.values) {
            recipes.put(nextRecipeId, recipe)
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

    fun getRecipe(id: Int): Recipe {
        return recipes[id] ?: throw NoSuchElementException("No recipe with ID $id")
    }

    fun getRecipeCount(): Int {
        return recipes.size
    }

    fun getRecipeNextId(): Int {
        return nextRecipeId
    }

    fun getAllRecipes(): List<Recipe> {
        return recipes.values.toList()
    }

    fun searchRecipesByName(name: String): List<Recipe> {
       return recipes.filterValues {it.name.equals(name,ignoreCase = true) }.values.toList()
    }

    fun searchRecipesByIngredient(byIngredient: Ingredient): List<Recipe> {
       return recipes.filterValues { it.ingredients == byIngredient }.values.toList()
    }

    fun getRecipesByTag(byTag: String): List<Recipe> =
        recipes.values.filter { it.tags.contains(byTag) }

    fun filterRecipeByDifficulty(difficulty: Difficulty) : List<Recipe> {
        return recipes.filterValues { it.getDifficulty() == difficulty }.values.toList()
    }

    fun filterRecipeByCuisine(cuisine: Cuisine) : List<Recipe> {
        return recipes.filterValues { it.getCuisine() == cuisine }.values.toList()
    }

    fun filterRecipeByRating(rating: Int) : List<Recipe> {
        return recipes.filterValues {it.getRating() == rating}.values.toList()
    }

    fun addIngredient(ingredient: Ingredient) {
        ingredients.put(nextIngredientId, ingredient)
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

    fun getIngredient(id: Int) : Ingredient? {
        return ingredients[id] ?: throw NoSuchElementException("No ingredients with such ID: $id")
    }

    fun getAllIngredients() : List<Ingredient> {
        return ingredients.values.toList()
    }

}