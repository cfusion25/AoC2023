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
        for (i in 1..<t/2) {
            if (i * (t - i) > d) {
                mult *= t - (2*i - 1)
                break
            }
        }
    }
    println(mult)

    // Part 2
    var t = ""
    var d = ""
    races.forEach { race ->
        t += race.first
        d += race.second
    }
    for (i in 1..<(t.toLong()/2)) {
        if (i * (t.toLong() - i) > d.toLong()) {
            println(t.toLong() - (2*i - 1))
            break
        }
    }
}