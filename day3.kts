import java.io.File
import java.io.InputStream

val EXERCISE_3_FILE_PATH = "path/to/file/3.txt"

fun main() {
    val lineList = mapFileToLineList(EXERCISE_3_FILE_PATH)

    var sum1 = 0
    var sum2 = 0
    lineList.forEachIndexed { idx, it ->
        sum1 += getPriorityOfCurrentRucksackRepeatedItem(it)

        if (idx % 3 == 2) {
            sum2 += getPriorityInCommonItemOfGroupsOfThree(listOf(lineList[idx], lineList[idx - 1], lineList[idx - 2]))
        }
    }

    println(sum1)
    println(sum2)
}

fun getPriorityOfCurrentRucksackRepeatedItem(line: String): Int {
    val firstCompartment = line.substring(0, line.length / 2)
    val secondCompartment = line.substring(line.length / 2)

    val commonItem = firstCompartment.toSet() intersect secondCompartment.toSet()

    return getCommonItemPriority(commonItem.first())
}

fun getPriorityInCommonItemOfGroupsOfThree(lines: List<String>): Int {
    val commonItem = (lines[0].toSet() intersect lines[1].toSet() intersect lines[2].toSet()).first()
    return getCommonItemPriority(commonItem)
}

fun getCommonItemPriority(item: Char): Int {
    val itemAsciiCode = item.code

    return if (itemAsciiCode <= 90) itemAsciiCode - 38 else itemAsciiCode - 96
}

fun mapFileToLineList(filePath: String): List<String> {
    val inputStream: InputStream = File(filePath).inputStream()
    val lineList = mutableListOf<String>()
    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    return lineList
}

main()