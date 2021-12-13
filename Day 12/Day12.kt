import java.io.File

class Node(val id: String) {
    var isSmallCave = true
    val connections = arrayListOf<Node>()
    var visitCnt = 0

    init {
        if (id != "start" && id != "end") {
            setIsSmallCave()
        }
    }

    private fun setIsSmallCave() {
        id.forEach { c ->
            if (c.isUpperCase()) {
                isSmallCave = false
                return@forEach
            }
        }
    }

    fun addConnection(node: Node) = connections.add(node)
}

var validPathsA = 0
var validPathsB = 0

fun main() {
    val graph = mutableMapOf<String, Node>()

    File("C:/Users/Steli/Desktop/day12.txt").forEachLine { line ->
        val nodes = line.split("-")
        // add nodes to the graph
        if (!graph.containsKey(nodes[0])) graph[nodes[0]] = Node(nodes[0])
        if (!graph.containsKey(nodes[1])) graph[nodes[1]] = Node(nodes[1])
        // make start and end unidirectional and all other nodes bidirectional
        if (nodes[0] == "start" || nodes[1] == "end") {
            graph[nodes[0]]!!.addConnection(graph[nodes[1]]!!)
            return@forEachLine
        } else if (nodes[1] == "start" || nodes[0] == "end") {
            graph[nodes[1]]!!.addConnection(graph[nodes[0]]!!)
            return@forEachLine
        }
        graph[nodes[0]]!!.addConnection(graph[nodes[1]]!!)
        graph[nodes[1]]!!.addConnection(graph[nodes[0]]!!)
    }

    traverse(graph["start"]!!, mutableListOf(graph["start"]!!))
    println("partA = $validPathsA | partB = $validPathsB")
}

private fun traverse(node: Node, path: MutableList<Node>) { // dfs

    if (node.id == "end") {
        //printPath(path)
        if (path.groupBy { it.visitCnt }.containsKey(2)) validPathsB++
        else validPathsA++
        return
    }


    if (node.isSmallCave) {
        if (node.visitCnt == 0 || (node.visitCnt == 1 && !path.groupBy { it.visitCnt }.containsKey(2))) node.visitCnt++
    }


    node.connections.forEach { nd ->
        if (nd.visitCnt == 1 && !path.groupBy { it.visitCnt }.containsKey(2)
            || nd.visitCnt == 0
        ) {
            path.add(nd)
            traverse(nd, path)
            path.removeLast()
        }
    }
    if (node.isSmallCave) {
        if (node.visitCnt > 0) node.visitCnt--
    }
}

fun printPath(path: MutableList<Node>) {
    path.forEach {
        if (path.last() != it) print("${it.id} -> ") else print(it.id)
    }
    println()
}
