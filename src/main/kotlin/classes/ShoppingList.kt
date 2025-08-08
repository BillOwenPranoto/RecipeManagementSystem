package classes

import enums.IngredientCategory

data class ShoppingList(val items: MutableList<ShoppingItem> = mutableListOf(),
                    val groupedByCategory: Map<IngredientCategory, List<ShoppingItem>> = mutableMapOf()) {

    fun getTotalItems() : Int =
        groupedByCategory.values.sumOf { it.size }

    fun getTotalItemsCount(): Int =
         groupedByCategory.values
            .flatten()
            .sumOf { kotlin.math.round(it.totalAmount).toInt() }


    fun addItem(item: ShoppingItem) {
        if (items.contains(item)) {
            val index = items.indexOf(item)
            items[index] = items[index].combine(item)
        }

        else {
            items.add(item)
        }
    }

    fun removeItemAt(index: Int) {
        if (index > items.size || index < 0) {
            println("Shopping list doesn't have that specific item anymore!")
            return
        }

        else {
            items.removeAt(index)
        }
    }

    fun printList(): String {
        var counter: Int = 1
        val sb = StringBuilder()

        println("Current items in your shopping list: ")
        for (item in items) {

            sb.append("$counter. $item")
            counter++
        }

        return sb.toString()
    }
}