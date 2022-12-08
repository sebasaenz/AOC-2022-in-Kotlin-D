import java.io.File
import java.io.InputStream

val EXCERCISE_6_FILE_PATH = "path/to/file/6.txt"
val MARKER_LENGTH = 4 // Change to 14 for second part of day #6

fun main() {
    val inputString = mapFileToString(EXCERCISE_6_FILE_PATH)

    var chars = mutableListOf<Char>()
    var ans = -1
    for ((i, char) in inputString.toCharArray().withIndex()) {
        if (i < MARKER_LENGTH) {
            chars.add(char)
            continue
        }

        if (chars.toSet().size == chars.size) {
            ans = i
            break
        }

        chars.removeAt(0)
        chars.add(char)
    }

    println(ans)
}

fun mapFileToString(filePath: String): String {
    val inputStream: InputStream = File(filePath).inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }

    return inputString
}

main()
