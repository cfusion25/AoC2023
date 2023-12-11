import java.io.File
import java.io.InputStream
import kotlin.math.abs

fun getDistance(eSize: Int, lineList: MutableList<MutableList<Char>>) {
    val initialGalaxyList = mutableListOf<Pair<Int,Int>>()

    lineList.forEachIndexed() { i, line->
        line.forEachIndexed() { j, c->
            if(c == '#') {
                initialGalaxyList.add(Pair(i,j))
            }
        }
    }

    val yExpand = mutableListOf<Int>()
    val xExpand = mutableListOf<Int>()

    for(i in 0..< lineList.size) {
        var galaxy = false
        for (j in 0..<lineList[0].size) {
            if (lineList[i][j] == '#') { galaxy = true }
        }
        if(!galaxy) { yExpand.add(i)}
    }

    for(j in 0..<lineList[0].size) {
        var galaxy = false
        for (i in 0..<lineList.size) {
            if (lineList[i][j] == '#') { galaxy = true }
        }
        if(!galaxy) { xExpand.add(j) }
    }

    var dSum:Long = 0
    for (i in 0..<initialGalaxyList.size) {
        for (j in i..<initialGalaxyList.size) {
            val g1 = initialGalaxyList[i]
            val g2 = initialGalaxyList[j]

            var xit1 = 0
            var yit1 = 0

            var xit2 = 0
            var yit2 = 0

            yExpand.forEach {
                if(g1.first > it) { yit1++ }
                if(g2.first > it) { yit2++ }
            }
            xExpand.forEach {
                if(g1.second > it) { xit1++ }
                if(g2.second > it) { xit2++ }
            }

            dSum += abs((g1.first + (yit1 * eSize)) - (g2.first + (yit2 * eSize))) + abs((g1.second + (xit1 * eSize)) - (g2.second + (xit2 * eSize)))
        }
    }
    println(dSum)
}

fun main(args: Array<String>) {
    val inputStream: InputStream = File("C:\\Users\\coldf\\OneDrive\\Desktop\\AoC2023\\input\\d11.txt").inputStream()
    val lineList = mutableListOf<MutableList<Char>>()

    inputStream.bufferedReader().forEachLine { line->
        val charList = mutableListOf<Char>()
        line.forEach {
            charList.add(it)
        }
        lineList.add(charList)
    }

    getDistance(1,lineList)
    getDistance(999999,lineList)
}
