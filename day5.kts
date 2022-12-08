import java.io.File
import java.io.InputStream

val EXERCISE_5_FILE_PATH = "path/to/file/5.txt"

// I was too lazy to parse the stacks from the output, sorry :)
val stacksExercise1 = mutableListOf(
    mutableListOf("T", "R", "D", "H", "Q", "N", "P", "B"),
    mutableListOf("V", "T", "J", "B", "G", "W"),
    mutableListOf("Q", "M", "V", "S", "D", "H", "R", "N"),
    mutableListOf("C", "M", "N", "Z", "P"),
    mutableListOf("B", "Z", "D"),
    mutableListOf("Z", "W", "C", "V"),
    mutableListOf("S", "L", "Q", "V", "C", "N", "Z", "G"),
    mutableListOf("V", "N", "D", "M", "J", "G", "L"),
    mutableListOf("G", "C", "Z", "F", "M", "P", "T")
)

// Very inelegant and probably inefficient way of deep-copying a MutableList
// There should be a simpler way
val stacksExercise2 = stacksExercise1
    .toTypedArray()
    .map { it.toTypedArray().clone().toMutableList() }
    .toMutableList()

fun main() {
    val lineList = mapFileToLineList(EXERCISE_5_FILE_PATH)

    lineList.forEachIndexed { idx, action ->
        // Skip lines with the stacks diagram
        if (idx < 10) {
            return@forEachIndexed
        }

        moveCratesOneByOne(action)
        moveCratesAllAtOnce(action)
    }

    println(stacksExercise1.joinToString("") { it.first() })
    println(stacksExercise2.joinToString("") { it.first() })
}

fun mapFileToLineList(filePath: String): List<String> {
    val inputStream: InputStream = File(filePath).inputStream()
    val lineList = mutableListOf<String>()
    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    return lineList
}

fun getActionQuantity(action: String): Int = getFormattedAction(action)[0]
fun getActionFromStack(action: String): Int = getFormattedAction(action)[1]
fun getActionToStack(action: String): Int = getFormattedAction(action)[2]

fun getFormattedAction(action: String): List<Int> {
    return action
        .replace("move ", "")
        .replace(" from ", "-")
        .replace(" to ", "-")
        .split("-")
        .map { it.toInt() }
}

fun moveCratesOneByOne(action: String): Unit {
    for (i in 1..getActionQuantity(action)) {
        val take = stacksExercise1[getActionFromStack(action) - 1].removeFirst()
        stacksExercise1[getActionToStack(action) - 1].add(0, take)
    }
}

fun moveCratesAllAtOnce(action: String): Unit {
    val subList = stacksExercise2[getActionFromStack(action) - 1].subList(0, getActionQuantity(action))
    stacksExercise2[getActionToStack(action) - 1] = (subList.toMutableList() + stacksExercise2[getActionToStack(action) - 1]) as MutableList<String>
    subList.clear()
}

main()