import java.io.File
import java.io.InputStream

val EXERCISE_4_FILE_PATH = "path/to/file/4.txt"

fun main() {
    val lineList = mapFileToLineList(EXERCISE_4_FILE_PATH)
    var sum = 0

    lineList.forEach {
        val firstRange = it.split(',')[0].split('-').map { el -> el.toInt() }.sorted()
        val secondRange = it.split(',')[1].split('-').map { el -> el.toInt() }.sorted()

        val chosenPredicate = ::doRangesContainEachOther // Change depending on the part

        sum += if (chosenPredicate(firstRange, secondRange)) 1 else 0
    }

    println(sum)
}

fun mapFileToLineList(filePath: String): List<String> {
    val inputStream: InputStream = File(filePath).inputStream()
    val lineList = mutableListOf<String>()
    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    return lineList
}

// Part #1
fun doRangesContainEachOther(firstRange: List<Int>, secondRange: List<Int>): Boolean = (firstRange[0] >= secondRange[0] && firstRange[1] <= secondRange[1])
        || (secondRange[0] >= firstRange[0] && secondRange[1] <= firstRange[1])

// Part #2
fun doRangesOverlap(firstRange: List<Int>, secondRange: List<Int>): Boolean = (firstRange[0] >= secondRange[0] && firstRange[0] <= secondRange[1])
        || (secondRange[0] >= firstRange[0] && secondRange[0] <= firstRange[1])

main()