import java.io.File
import java.io.InputStream

fun main(args: Array<String>) {
    val inputStream: InputStream = File("C:\\Users\\coldf\\OneDrive\\Desktop\\AoC2023\\input\\d9.txt").inputStream()
    val lineList = mutableListOf<List<Long>>()
    inputStream.bufferedReader().forEachLine { lineList.add(it.split(" ").map{ it.toLong() }) }

    val diffList = mutableListOf<MutableList<MutableList<Long>>>()
    var endSum:Long = 0
    var startSum:Long = 0

    lineList.forEachIndexed() {index, line ->
        var focus = line
        diffList.add(mutableListOf<MutableList<Long>>())
        while (focus.groupingBy { it }.eachCount().get(0) != focus.size) {
            val diff = mutableListOf<Long>()
            for (i in 0..<focus.size-1) {
                diff.add((focus[i+1]- focus[i]))
            }
            focus = diff
            diffList[index].add(diff)
        }
        // Part 1 + 2
        diffList[index][diffList[index].size-1].add(0)
        diffList[index][diffList[index].size-1].add(0, 0)
        for (i in (diffList[index].size - 2) downTo 0){
            diffList[index][i].add((diffList[index][i + 1].last() + diffList[index][i].last()))
            diffList[index][i].add(0,(diffList[index][i].first() - diffList[index][i + 1].first()))

        }
        endSum += line.last() + diffList[index][0].last()
        startSum += line.first() - diffList[index][0].first()
    }
    println(endSum)
    println(startSum)
}

