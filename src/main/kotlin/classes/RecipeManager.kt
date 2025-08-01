package classes

import enums.Cuisine
import enums.Difficulty
import enums.IngredientCategory
import enums.MeasurementUnit
import utilities.ConsolePrompter

class RecipeManager {
    val recipes: MutableMap<Int, Recipe> = mutableMapOf()
    val recipeIngredients: MutableMap<Int, RecipeIngredient> = mutableMapOf()
    val ingredients: MutableMap<Int, Ingredient> = mutableMapOf()

    var nextRecipeId = 1
    var nextIngredientId = 1
    var nextRecipeIngredientId = 1

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

//    fun searchRecipesByIngredient(byIngredient: Ingredient): List<Recipe> =
//        recipes.values.filter { it.ingredients == byIngredient }.toList()

    fun getRecipesByTag(byTag: String): List<Recipe> =
        recipes.values.filter { it.tags.contains(byTag) }

    fun filterRecipeByDifficulty(difficulty: Difficulty) : List<Recipe> =
         recipes.values.filter { it.difficulty == difficulty }.toList()


    fun filterRecipeByCuisine(cuisine: Cuisine) : List<Recipe> =
         recipes.values.filter { it.cuisine == cuisine }.toList()


    fun filterRecipeByRating(rating: Int) : List<Recipe> =
         recipes.values.filter {it.rating == rating}.toList()


    fun addIngredient(ingredient: Ingredient) {
        ingredients.put(this.nextIngredientId, ingredient)
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

    fun getIngredientCount() : Int =
        ingredients.size

    fun searchIngredient(name: String): List<Ingredient> =
        ingredients.values.filter { it.name.equals(name,ignoreCase = true) }.toList()

    fun getIngredientsByCategory(category: IngredientCategory): List<Ingredient> =
        ingredients.values.filter { it.category == category }.toList()

    fun addRecipeIngredient(recipeIngredient: RecipeIngredient) {
        recipeIngredients.put(nextRecipeIngredientId, recipeIngredient)
        nextRecipeIngredientId++
    }

    fun handleUserInput(input: Int?) {

        when(input) {
            1 -> handleAddRecipe()
            2 -> handleUpdateRecipe()
            4 -> handleViewCurrentRecipes()
        }
    }

    fun handleAddRecipe()  {
        println("\n---------------------------------")
        println("Please fill in the recipe details below!")

        val name = ConsolePrompter.promptText("Enter the recipe's name: ")
        val description = ConsolePrompter.promptText("Enter a brief description: ")
        val servings = ConsolePrompter.promptInt("How many servings?: ")
        val notes = ConsolePrompter.promptText("Any notes for this? (optional): ")
        val difficulty = ConsolePrompter.promptEnum<Difficulty>("How would you rate this recipe's difficulty?\nHere's the list: ")
        val cuisine = ConsolePrompter.promptEnum<Cuisine>("What cuisine would this menu be?\nHere's the list: ")
        val prepTime = ConsolePrompter.promptInt("How long roughly will it take in minutes to prepare for the recipe?: ")
        val cookTime = ConsolePrompter.promptInt("And how long will it take in minutes to cook?: ")

        print("Let's fill in the ingredients. First, let us know how many ingredients needed for this recipe: " )
        val numOfIngredient = readLine()?.toIntOrNull()

        if (numOfIngredient != null) {
           for (i in 0 until numOfIngredient) {
               val ingredientName = ConsolePrompter.promptText("Fill in ingredient number ${i+1}: ")

               println("\nHere's every possible category you can fill in for $ingredientName: ")
               IngredientCategory.entries.forEachIndexed { index, category ->
                   println("${index + 1}. ${category.name}")
               }

               val category = ConsolePrompter.promptEnum<IngredientCategory>("Fill in the category for this ingredient: ")
               val amount = ConsolePrompter.promptDouble("Enter amount for this ingredient: ")
               val unit = ConsolePrompter.promptEnum<MeasurementUnit>("Enter unit for this (grams/teaspoon/etc.): ")
               val ingredientNotes = ConsolePrompter.promptText("Any notes for this ingredient? (optional): ")

               val ingredient = Ingredient(nextIngredientId,ingredientName,category)
               this.addIngredient(ingredient)

               val recipeIngredient = RecipeIngredient(ingredient,amount,ingredientNotes,unit)
               this.addRecipeIngredient(recipeIngredient)

               val recipe = Recipe(nextRecipeId, name, description,servings,prepTime,cookTime,0,difficulty,
                   cuisine,notes, recipeIngredients.values.toList())
               this.addRecipe(recipe)

               println("Recipe $name has been succesfully created!")
           }
        }

    }

    fun handleUpdateRecipe() {

    }

    fun handleViewCurrentRecipes() {

    }

    fun showMenu() {

        print("\n====================================")
        println("1. Create a recipe.")
        println("2. Update a recipe.")
        println("3. Search for a specific recipe.")
        println("4. View all recipes in my list.")
        println("5. Delete a recipe.")
        println("6. Exit")
        print("Enter your choice here: ")
    }

    fun run() {

        println("\nWelcome to Recipe Manager! What can I do for you? ")

        inputLoop@ while(true) {
            showMenu()
            val userInput = readLine()?.toIntOrNull()

            when (userInput) {
                null -> {
                    println("\nInput can't be null. Please re-enter!")
                    continue@inputLoop
                }
                in 1..5 -> handleUserInput(userInput)
                6 -> break@inputLoop
                else -> {
                    println("\nNumber entered invalid. Please enter a valid one!")
                    continue@inputLoop
                }
            }
        }
    }
}