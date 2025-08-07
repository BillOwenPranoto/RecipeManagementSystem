package classes

import enums.Cuisine
import enums.Difficulty
import enums.IngredientCategory
import enums.MeasurementUnit
import utilities.ConsolePrompter

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

    fun updateRecipe(id: Int, field: Int, updatedValue: String) {
        val recipe = recipes[id] ?: return

        when (field) {
            1 -> recipe.name = updatedValue
            2 -> recipe.description = updatedValue
            3 -> recipe.servings = updatedValue.toIntOrNull() ?: recipe.servings
            4 -> recipe.setPrepTime(updatedValue)
            5 -> recipe.setCookingTime(updatedValue)
            6 -> recipe.setRating(updatedValue)
            7 -> recipe.cuisine = Cuisine.valueOf(updatedValue)
            8 -> recipe.difficulty = Difficulty.valueOf(updatedValue)
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
        recipes.values.filter { it.name.equals(name, ignoreCase = true) }.toList()

    fun searchRecipesByIngredientName(ingredientName: String): List<Recipe> {
        return recipes.values.filter { recipe ->
            recipe.ingredients.any {
                it.ingredient.name.equals(ingredientName, ignoreCase = true)
            }
        }
    }

    fun getRecipesByTag(byTag: String): List<Recipe> =
        recipes.values.filter { it.tags.contains(byTag) }

    fun filterRecipeByDifficulty(difficulty: String): List<Recipe> =
        recipes.values.filter { it.difficulty.displayName().equals(difficulty, ignoreCase = true) }.toList()


    fun filterRecipeByCuisine(cuisine: String): List<Recipe> =
        recipes.values.filter { it.cuisine.displayName().equals(cuisine, ignoreCase = true) }.toList()


    fun filterRecipeByRating(rating: String): List<Recipe> =
        recipes.values.filter { it.rating == rating.toInt() }.toList()


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

    fun getIngredient(id: Int): Ingredient? =
        ingredients[id] ?: throw NoSuchElementException("No ingredients with such ID: $id")

    fun getAllIngredients(): List<Ingredient> =
        ingredients.values.toList()

    fun getIngredientCount(): Int =
        ingredients.size

    fun searchIngredient(name: String): List<Ingredient> =
        ingredients.values.filter { it.name.equals(name, ignoreCase = true) }.toList()

    fun getIngredientsByCategory(category: IngredientCategory): List<Ingredient> =
        ingredients.values.filter { it.category == category }.toList()

    fun formatFilteredList(filtered: List<Recipe>): String {
        val sb = StringBuilder()

        sb.append("Found ${filtered.size} recipes: \n")
        filtered.forEachIndexed { i, recipe ->
            sb.append("${i + 1}. ${recipe.name}\n")
        }
        return sb.toString()
    }

    fun handleUserInput(input: Int?) {

        when(input) {
            1 -> handleAddRecipe()
            2 -> handleUpdateRecipe()
            3 -> handleSearchRecipe()
            4 -> {
                println("\nHere are your current recipes:")
                handleViewCurrentRecipes()
            }
            5-> handleDeleteRecipe()
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
        val rating = ConsolePrompter.promptOptionalInt("Lastly, how would you rate this recipe out of 10?: ")


        print("\nLet's fill in the ingredients. First, let us know how many ingredients needed for this recipe: " )
        val numOfIngredient = readLine()?.toIntOrNull()

        val currentRecipeIngredients = mutableListOf<RecipeIngredient>()

        if (numOfIngredient != null) {
           for (i in 0 until numOfIngredient) {
               val ingredientName = ConsolePrompter.promptText("Fill in ingredient number ${i+1}: ")

               println("\nHere's every possible category you can fill in for $ingredientName: ")
               IngredientCategory.entries.forEachIndexed { index, category ->
                   println("${index + 1}. ${category.name}")
               }

               val category = ConsolePrompter.promptEnum<IngredientCategory>("Fill in the category for this ingredient: ")
               val amount = ConsolePrompter.promptDouble("Enter amount for this ingredient (just the nominal, you'll be putting the unit after this): ")
               val unit = ConsolePrompter.promptEnum<MeasurementUnit>("Enter unit for this (grams/teaspoon/etc.): ")
               val ingredientNotes = ConsolePrompter.promptText("Any notes for this ingredient? (optional): ")

               val ingredient = Ingredient(nextIngredientId,ingredientName,category)
               this.addIngredient(ingredient)

               val recipeIngredient = RecipeIngredient(ingredient,amount,ingredientNotes,unit)
               currentRecipeIngredients.add(recipeIngredient)

           }
            val recipe = Recipe(nextRecipeId, name, description,servings,prepTime,cookTime,rating,difficulty,
                cuisine,notes, currentRecipeIngredients)
            this.addRecipe(recipe)

            println("Recipe $name has been successfully created!")
        }

    }

    fun handleUpdateRecipe() {

        println("Current recipe in the list: ")
        handleViewCurrentRecipes()
        val idToUpdate = ConsolePrompter.promptInt("Look up the recipe here by typing the id: ")

        if (recipes[idToUpdate] != null) {
            println("Detailed description of recipe no. $idToUpdate")
            println(recipes[idToUpdate].toString())

            println("Which detail would you like to update for this recipe? ")
            println("1. Name")
            println("2. Description")
            println("3. Servings")
            println("4. Prep Time")
            println("5. Cooking Time")
            println("6. Rating")
            println("7. Cuisine")
            println("8. Difficulty")

            val answer = ConsolePrompter.promptInt("Enter your choice: ")

            when (answer) {
                1 -> {
                    val newName = ConsolePrompter.promptText("Enter new name: ")
                    updateRecipe(idToUpdate, 1, newName)
                }
                2 -> {
                    val newDescription = ConsolePrompter.promptText("Enter new description: ")
                    updateRecipe(idToUpdate, 2, newDescription)
                }

                3 -> {
                    val newServings = ConsolePrompter.promptText("Enter new servings: ")
                    updateRecipe(idToUpdate, 3, newServings)
                }

                4 -> {
                    val newPrepTime = ConsolePrompter.promptText("Enter new prep time in minutes: ")
                    updateRecipe(idToUpdate, 4, newPrepTime)
                }

                5 -> {
                    val newCookTime = ConsolePrompter.promptText("Enter new cooking time in minutes: ")
                    updateRecipe(idToUpdate, 5, newCookTime)
                }

                6 -> {
                    val newRating = ConsolePrompter.promptText("Enter new rating: ")
                    updateRecipe(idToUpdate, 6, newRating)
                }

                7 -> {
                    val newCuisine = ConsolePrompter.promptEnum<Cuisine>("Enter new cuisine: ")
                    updateRecipe(idToUpdate,7,newCuisine.name)
                }

                8 -> {
                    val newDifficulty = ConsolePrompter.promptEnum<Difficulty>("Enter new difficulty: ")
                    updateRecipe(idToUpdate,8,newDifficulty.name)
                }

                else -> {
                    println("Invalid number. Please choose between 1 to 8!")
                    return
                }
            }

            println("Recipe updated successfully!") // Add confirmation
        } else {
            println("Recipe with ID $idToUpdate not found.")
        }
    }

    fun handleViewCurrentRecipes() {

        recipes.entries.forEach { (id, recipe) ->
            println("ID $id → ${recipe.name}")
        }
    }

    fun handleSearchRecipe() {
        println("How would you filter your search? ")
        println("1. By name")
        println("2. By ingredient")
        println("3. By tag")
        println("4. By difficulty")
        println("5. By cuisine")
        println("6. By rating")
        val choice = ConsolePrompter.promptInt("Enter your choice: ")
        val keyword = ConsolePrompter.promptText("Enter the keyword here: ")

        when (choice) {
            1 -> {
                val filteredList = searchRecipesByName(keyword)

                if (filteredList.isEmpty()) {
                    println("No recipe has the keyword \"$keyword\".")
                }
                else {
                    println(formatFilteredList(filteredList))
                }
            }
            2 -> {
                val filteredList = searchRecipesByIngredientName(keyword)

                if (filteredList.isEmpty()) {
                    println("No recipe has the keyword \"$keyword\".")
                }
                else {
                    println(formatFilteredList(filteredList))
                }
            }
            3 -> {
                val filteredList = getRecipesByTag(keyword)

                if (filteredList.isEmpty()) {
                    println("No recipe has the keyword \"$keyword\".")
                }
                else {
                    println(formatFilteredList(filteredList))
                }
            }
            4 -> {
                val filteredList = filterRecipeByDifficulty(keyword)

                if (filteredList.isEmpty()) {
                    println("No recipe has the keyword \"$keyword\".")
                }
                else {
                    println(formatFilteredList(filteredList))
                }
            }
            5 -> {
                val filteredList = filterRecipeByCuisine(keyword)

                if (filteredList.isEmpty()) {
                    println("No recipe has the keyword \"$keyword\".")
                }
                else {
                    println(formatFilteredList(filteredList))
                }
            }
            6 -> {
                val filteredList = filterRecipeByRating(keyword)

                if (filteredList.isEmpty()) {
                    println("No recipe has the keyword \"$keyword\".")
                }
                else {
                    println(formatFilteredList(filteredList))
                }
            }
        }
    }

    fun handleDeleteRecipe() {
        if (recipes.isNotEmpty()) {
            println("Here's the list of recipes currently saved. Let me know the number (id) of recipes you wish to remove!")
            handleViewCurrentRecipes()

            deleteLoop@ while(true) {
                val idToRemove = ConsolePrompter.promptInt("Enter your id: ")

                val reassure = ConsolePrompter.promptYesOrNo("Are you sure you'd like to delete this recipe?: (Y/N)")

                if (reassure) {
                    deleteRecipe(idToRemove)
                    break@deleteLoop
                } else {
                    continue@deleteLoop
                }
            }
        }
        else {
            println("There is no recipe stored currently in the list! Please make a new one!")
            return
        }
    }

    fun showMenu() {

        println("====================================")
        println("1. Create a recipe.")
        println("2. Update a recipe.")
        println("3. Search for a specific recipe (by filtering).")
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