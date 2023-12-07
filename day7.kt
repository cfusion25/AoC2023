import java.io.File
import java.io.InputStream
import java.lang.Long.parseLong


fun addValue(hand: String, count: MutableMap<Char, Int>, handMap: Map<Char,Int>) :String {
    var handValue = ""
    if(count.size == 1) {
        handValue += "7"
    } else if(count.size == 2) {
        run check@{
            count.forEach{
                if (it.value == 4 || it.value == 1) {
                    handValue += "6"
                } else {
                    handValue += "5"
                }
                return@check
            }
        }
    } else if (count.size == 3) {
        var three = false
        count.forEach{
            if (it.value == 3) {
                three = true
            }
        }
        if (three) {
            handValue += "4"
        } else {
            handValue += "3"
        }
    } else if (count.size == 4) {
        handValue += "2"
    } else {
        handValue += "1"
    }

    hand.forEach {
        handValue += handMap[it]?.let { it1 -> Integer.toHexString(it1) }
    }

    return handValue
}

fun printSum(handList: MutableList<Pair<String, String>>) {
    val sortedHands = handList.sortedBy { parseLong(it.first, 16) }
    var sum1 = 0
    sortedHands.forEachIndexed {index, hand ->
        sum1 += hand.second.toInt() * (index + 1)
    }
    println(sum1)
}

fun main(args: Array<String>) {
    val inputStream: InputStream = File("C:\\Users\\coldf\\OneDrive\\Desktop\\AoC2023\\input\\d7.txt").inputStream()
    val lineList = mutableListOf<Pair<String, String>>()
    inputStream.bufferedReader().forEachLine { lineList.add(it.strip().split(" ").zipWithNext()[0]) }

    // Part 1
    val handMap1 = mapOf('A' to 13, 'K' to 12, 'Q' to 11, 'J' to 10, 'T' to 9, '9' to 8, '8' to 7, '7' to 6, '6' to 5, '5' to 4, '4' to 3, '3' to 2, '2' to 1 )
    val handList1 = mutableListOf<Pair<String, String>>()

    lineList.forEach { pair ->
        val hand = pair.first
        val handValue = addValue(hand, hand.groupingBy { it }.eachCount().toMutableMap(), handMap1)
        handList1.add(Pair(handValue, pair.second))
    }
    printSum(handList1)

    // Part 2
    val handMap2 = mapOf('A' to 13, 'K' to 12, 'Q' to 11, 'J' to 1, 'T' to 10, '9' to 9, '8' to 8, '7' to 7, '6' to 6, '5' to 5, '4' to 4, '3' to 3, '2' to 2)
    val handList2 = mutableListOf<Pair<String, String>>()
    lineList.forEach { pair ->
        val hand = pair.first
        val count = hand.groupingBy { it }.eachCount().toMutableMap()

        // Joker Parsing
        if (count['J'] != null && count.size > 1) {
            val j = count['J']
            count.remove('J')
            val max = count.maxBy { it.value }
            count[max.key] = max.value + j!!
        }

        val handValue = addValue(hand, count, handMap2)
        handList2.add(Pair(handValue, pair.second))
    }
    printSum(handList2)
}
