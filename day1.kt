import java.io.File
import java.io.InputStream


fun replaceText(text: String, keys: Map<String, String>): String {
    return keys.entries.fold(text) { acc, (key, value) -> acc.replace(key, value) }
}

fun part1(text: String): String {
    val numbers = text.filter { it.isDigit() }
    val first = numbers.first()
    val last = numbers.last()
    return "$first$last"
}

fun main(args: Array<String>) {
    var p1_value = 0
    var p2_value = 0

    val inputStream: InputStream = File("C:\\Users\\coldf\\OneDrive\\Desktop\\AoC2023\\input\\d1.txt").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }
    lineList.forEach{ line ->
        //Part 1
        val code1 = part1(line)
        p1_value += code1.toInt()

        //Part 2
        val regex = Regex("one|two|three|four|five|six|seven|eight|nine|[1-9]")
        var s = ""
        var l = line
        while (true) {
            val matches = regex.find(l)
            if (matches == null) {
                break
            }
            s += matches.value
            l = l.substring(l.indexOf(matches.value)+1)
        }

        val regexMap = mapOf("one" to "1", "two" to "2", "three" to "3", "four" to "4", "five" to "5", "six" to "6", "seven" to "7", "eight" to "8", "nine" to "9")
        val x = replaceText(s, regexMap)
        val code2 = part1(x)
        p2_value += code2.toInt()
    }

    println(p1_value)
    println(p2_value)
}
