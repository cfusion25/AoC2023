import java.io.File
import java.io.InputStream

fun hashValue (hash: String):Int {
    var hSum = 0
    hash.forEach {
        hSum += it.toInt()
        hSum *= 17
        hSum %= 256
    }
    return hSum
}

fun main(args: Array<String>) {
    val inputStream: InputStream = File("C:\\Users\\coldf\\OneDrive\\Desktop\\AoC2023\\input\\d15.txt").inputStream()
    var hashes = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { line ->
        hashes = line.split(',').toMutableList()
    }

    var totalSum = 0
    hashes.forEach {
        totalSum += hashValue(it)
    }
    println(totalSum)

    // Part 2
    var boxes = MutableList<MutableList<Pair<String, Int>>?>(256) { mutableListOf<Pair<String, Int>>() }
    hashes.forEach { hash ->
        if (hash.contains('=')) {
            var temp = hash.split('=')
            var boxNum = hashValue(temp[0])
            var i = -1
            boxes[boxNum]?.forEachIndexed {index, lens ->
                if(lens.first == temp[0]) {
                    i = index
                }
            }
            if (i > -1) {
                boxes[boxNum]?.set(i, Pair(temp[0],temp[1].toInt()))
            } else {
                boxes[boxNum]?.add(Pair(temp[0],temp[1].toInt()))
            }
        }
        if (hash.contains('-')) {
            var temp = hash.split('-')
            var boxNum = hashValue(temp[0])
            var i = -1
            boxes[boxNum]?.forEachIndexed() {index, lens ->
                if (lens.first == temp[0]) {
                    i = index
                }
            }
            if (i > -1) {
                boxes[boxNum]?.removeAt(i)
            }
        }
    }
    var boxSum = 0
    boxes.forEachIndexed{boxNum, box ->
        box?.forEachIndexed { index, lens ->
            boxSum += (boxNum + 1) * (index + 1) * lens.second
        }
    }
    println(boxSum)
}
