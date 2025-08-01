package utilities

import enums.Difficulty

object ConsolePrompter {

    fun promptText(prompt: String): String {
        print(prompt)
        return readLine()?.trim().orEmpty()
    }

    fun promptInt(prompt: String): Int {
        while (true) {
            print("$prompt ")
            val input = readLine()?.toIntOrNull()
            if (input != null) return input
            println("Invalid number, try again.")
        }
    }

    fun promptOptionalInt(prompt: String) : Int? {
        print(prompt)
        return readLine()?.trim()?.toInt()
    }

    inline fun <reified T: Enum<T>> promptEnum(prompt: String): T {
        val enumValues = enumValues<T>()
        println(prompt)
        enumValues.forEach { println("- ${it.name}") }
        print("Enter your response: ")
        while (true) {
            val input = readLine()?.trim()?.uppercase()
            enumValues.find { it.name == input }?.let { return it }
            println("Invalid choice, try again.")
        }
    }

    fun promptYesOrNo(prompt: String) : Boolean {
        print("$prompt (y/n): ")
        return readLine()?.trim()?.lowercase() == "y"
    }

    fun promptDouble(prompt: String): Double {
        while (true) {
            print("$prompt ")
            val input = readLine()?.toDoubleOrNull()
            if (input != null) return input
            println("Invalid number, try again.")
        }
    }
}