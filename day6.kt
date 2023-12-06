import java.io.File
import java.io.InputStream


fun main(args: Array<String>) {
    val inputStream: InputStream = File("C:\\Users\\coldf\\OneDrive\\Desktop\\AoC2023\\input\\d6.txt").inputStream()
    val lineList = mutableListOf<String>()
    val races = mutableListOf<Pair<String,String>>()


    inputStream.bufferedReader().forEachLine { lineList.add(it.split(":")[1]) }

    val time = lineList[0].split(Regex("\\s+"))
    val dist = lineList[1].split(Regex("\\s+"))

    // Part 1
    for (i in 1..4) {
        races.add(Pair(time[i],dist[i]))
    }
    var mult = 1
    races.forEach { race ->
        val t = race.first.toInt()
        val d = race.second.toInt()
        var count = 0
        for (i in 1..<t) {
            if (i * (t - i) > d) {
                count += 1
            }
        }
        mult *= count
    }
    println(mult)

    // Part 2
    var t = ""
    var d = ""
    var count = 0
    races.forEach { race ->
        t += race.first
        d += race.second
    }
    for (i in 1..<(t.toLong()/2)) {
        if (i * (t.toLong() - i) > d.toLong()) {
            count += 1
        }
    }
    println(count*2+1)
}