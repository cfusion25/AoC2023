import java.io.File
import java.io.InputStream


fun main(args: Array<String>) {
    val inputStream: InputStream = File("C:\\Users\\coldf\\OneDrive\\Desktop\\AoC2023\\input\\d5.txt").inputStream()
    val lineList = mutableListOf<String>()
    val instructions = mutableListOf<HashMap<String, Pair<String, String>>>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val seeds = lineList[0].split(": ")[1].split(" ")
    val a = mutableListOf<List<String>>()
    // Advent of ICBF to REGEX, Hardcoding Go!
    a.add(lineList.slice(3..25))
    a.add(lineList.slice(28..36))
    a.add(lineList.slice(39..58))
    a.add(lineList.slice(61..100))
    a.add(lineList.slice(103..138))
    a.add(lineList.slice(141..175))
    a.add(lineList.slice(178..203))

    // Part 1
    a.forEachIndexed {i, step ->
        instructions.add(HashMap<String, Pair<String, String>>())
        step.forEach{
            val data = it.split(" ")
            if (data.isNotEmpty()) {
                instructions[i][data[1]] = Pair(data[0],data[2])
            }
        }
    }
    // Instructions[i] - Key: Source Value: (Destination, Length)
    var minLoc = 99999999999999999
    seeds.forEach{ seed->
        var next = seed
        for (i in 0..<instructions.size) {
            run breaking@{
                instructions[i].forEach {
                    val min = it.key.toLong()
                    val max = min + it.value.second.toLong() - 1
                    if (next.toLong() in min..max) {
                        next = (next.toLong() - min + it.value.first.toLong()).toString()
                        return@breaking
                    }
                }
            }
        }
        if (next.toLong() < minLoc) {
            minLoc = next.toLong()
        }
    }
    println(minLoc)

    // Part 2 - Advent of Brute Force! It's only a couple BILLION calculations after all
    // Instructions[i] - Key: Source Value: (Destination, Length)
    // 114137738 too high
    outer@ for (j in 0..114137738) {
        var next = j.toString()
        for (i in instructions.size-1 downTo 0) {
            run breaking2@{
                instructions[i].forEach {
                    val min = it.value.first.toLong()
                    val max = min + it.value.second.toLong() - 1
                    if (next.toLong() in min..max) {
                        next = (next.toLong() - min + it.key.toLong()).toString()
                        return@breaking2
                    }
                }
            }
        }
        for (i in 0..<seeds.size step 2) {
            if(next.toLong() in seeds[i].toLong()..<(seeds[i].toLong() + seeds[i+1].toLong())) {
                print("Minimum Location: ")
                println(j)
                break@outer
            }
        }
    }
}
