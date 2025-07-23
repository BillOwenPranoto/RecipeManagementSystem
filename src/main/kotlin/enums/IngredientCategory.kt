package enums

enum class IngredientCategory() {
    MEAT,
    POULTRY,
    SEAFOOD,
    EGGS,
    ANIMAL_FAT,
    VEGETABLE,
    FRUIT,
    GRAIN,
    DAIRY,
    SPICE,
    OTHER;

    fun displayNames(): String {
        return when (this) {
            MEAT -> "Meat"
            POULTRY -> "Poultry"
            SEAFOOD -> "Seafood"
            EGGS -> "Eggs"
            ANIMAL_FAT -> "Animal Fat"
            VEGETABLE -> "Vegetable"
            FRUIT -> "Fruit"
            GRAIN -> "Grain"
            DAIRY -> "Dairy"
            SPICE -> "Spice"
            OTHER -> "Other"
        }
    }
}