import java.io.File
import java.io.InputStream

val EXCERCISE_7_FILE_PATH = "path/to/file/7.txt"

class Node(var totalSpace: Int, val parent: Node?, val children: HashMap<String, Node>)

fun main() {
    val lineList = mapFileToLineList(EXCERCISE_7_FILE_PATH)

    val root = Node(0, null, HashMap())

    buildFileSystemTree(lineList, root)

    val directoriesDiskUsage = getDirectoriesDiskUsage(root)

    // Part #1
    println(directoriesDiskUsage.filter { it <= 100000 }.sum())
    // Part #2
    println(directoriesDiskUsage.filter { it >= 30000000 - 70000000 + root.totalSpace }.minOf { it })

}

fun mapFileToLineList(filePath: String): List<String> {
    val inputStream: InputStream = File(filePath).inputStream()
    val lineList = mutableListOf<String>()
    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    return lineList
}

fun buildFileSystemTree(lineList: List<String>, root: Node): Unit {
    var currentNode = root

    lineList.forEach {
        // Skip first line
        if (it == "$ cd /") {
            return@forEach
        }

        if (isCdCommand(it)) {
            currentNode = if (isGoToParentDirectory(it)) {
                currentNode.parent!!
            } else {
                currentNode.children[getDirectoryName(it)]!!
            }
        } else if (isLsResult(it)) {
            val lsResultArgs = getLsResultArgs(it)

            if (isDirectory(it)) {
                currentNode.children[lsResultArgs[1]] = Node(0, currentNode, HashMap())
            } else {
                val currentNodeTotalDiskUsage = lsResultArgs[0].toInt()
                currentNode.children[lsResultArgs[1]] = Node(currentNodeTotalDiskUsage, currentNode, HashMap())

                addFileDiskUsageToAllParents(currentNode, currentNodeTotalDiskUsage)
            }
        }
    }
}

fun isCdCommand(line: String): Boolean = line.split(' ')[1] == "cd"
fun isLsResult(line: String): Boolean = line[0] != '$'
fun isGoToParentDirectory(command: String): Boolean = getCommandArgs(command)[1] == ".."
fun getCommandArgs(command: String): List<String> = command.substring(2).split(' ')
fun getDirectoryName(command: String): String = command.substring(5)
fun isDirectory(lsResult: String): Boolean = getLsResultArgs(lsResult)[0] == "dir"
fun getLsResultArgs(lsResult: String): List<String> = lsResult.split(' ')
fun addFileDiskUsageToAllParents(fileNode: Node, fileNodeTotalDiskUsage: Int): Unit {
    var currentNodeParentScoped: Node? = fileNode
    while (currentNodeParentScoped != null) {
        currentNodeParentScoped.totalSpace += fileNodeTotalDiskUsage
        currentNodeParentScoped = currentNodeParentScoped.parent
    }
}

// BFS through filesystem to get a list of the disk usage of all directories
fun getDirectoriesDiskUsage(root: Node): List<Int> {
    var queue = mutableListOf(root)
    var directoriesDiskUsage = mutableListOf<Int>()

    while (queue.size > 0) {
        val currentNode = queue[0]
        directoriesDiskUsage.add(currentNode.totalSpace)

        for (child in currentNode.children.values) {
            if (child.children.isNotEmpty()) {
                queue.add(child)
            }
        }

        queue.removeAt(0)
    }

    return directoriesDiskUsage
}

main()