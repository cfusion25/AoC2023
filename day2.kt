import java.io.File
import java.io.InputStream


fun main(args: Array<String>) {
    val inputStream: InputStream = File("C:\\Users\\coldf\\OneDrive\\Desktop\\AoC2023\\input\\d2.txt").inputStream()
    val lineList = mutableListOf<String>()

    val rLimit = 12
    val gLimit = 13
    val bLimit = 14

    var idSum = 0
    var pSum = 0

    inputStream.bufferedReader().forEachLine { lineList.add(it) }
    lineList.forEach { line ->
        //Part 1
        val l = line.split(Regex(":|;"))
        val g = l[0].split(" ")[1]
        var possible = true

        //Part 2
        var rMin = 0
        var gMin = 0
        var bMin = 0

        for (i in 1..l.size - 1) {

            for (j in l[i].split(',')) {
                val t = j.split(" ")
                if (t[2].first()==('r')) {
                    if (t[1].toInt() > rLimit) {
                        possible = false
                    }
                    if (t[1].toInt() > rMin) {
                        rMin = t[1].toInt()
                    }
                } else if (t[2].first()==('g')) {
                    if (t[1].toInt() > gLimit) {
                        possible = false
                    }
                    if (t[1].toInt() > gMin) {
                        gMin = t[1].toInt()
                    }
                } else if (t[2].first()==('b')) {
                    if (t[1].toInt() > bLimit) {
                        possible = false
                    }
                    if (t[1].toInt() > bMin) {
                        bMin = t[1].toInt()
                    }
                }
            }
        }

        if (possible) {
            idSum += l[0].split(" ")[1].toInt()
        }
        pSum += rMin * gMin * bMin
    }
    println(idSum)
    println(pSum)
}