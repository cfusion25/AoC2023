import java.io.File
import java.io.InputStream
import kotlin.math.pow

fun readCard(cardNum: String, dict: Map<String, List<String>>) :Int {
    val nums = dict[cardNum]
    var count = 1
    if (nums?.isEmpty() == null) {
        return count
    }
    nums.forEachIndexed {i, it ->
        count += readCard((cardNum.toInt() + i + 1).toString(), dict)
    }
    return count
}


fun main(args: Array<String>) {
    val inputStream: InputStream = File("C:\\Users\\coldf\\OneDrive\\Desktop\\AoC2023\\input\\d4.txt").inputStream()
    val lineList = mutableListOf<String>()

    var points = 0
    val cardChain = HashMap<String, List<String>>()
    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    var count = 0

    // Part 1
    lineList.forEachIndexed { i, line ->
        count += 1
        var split = line.split(Regex(": "))
        split = split[1].split("| ")
        val winner = split[0].split(" ").filter{ it.isNotBlank() }.map{it}
        val nums = split[1].split(" ").filter{ it.isNotBlank() }.map{it}
        val intersects = nums.intersect(winner)
        if (intersects.isNotEmpty()) {
            // Kotlin is stupid, in built power function must have a double base
            val point = ((2.0).pow(intersects.size - 1).toInt())
            points += point
            cardChain[(i+1).toString()] = intersects.toList()
        }
    }
    println(points)

    // Part 2
    var cards = 0
    cardChain.forEach { card->
        cards += readCard(card.key, cardChain)
    }
    cards += (count-cardChain.size)
    println(cards)
}