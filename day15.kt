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
    // I heard you like HashMaps so I put a HashMap<Int,HashMap<String,Int>> in your HashMap
    var boxes = HashMap<Int,HashMap<Int,HashMap<String, Int>>?>()
    hashes.forEachIndexed {pos, hash ->
        if (hash.contains('=')) {
            var temp = hash.split('=')
            for (k in pos..<hashes.size) {
                val check = hashes[k]
                if (check.contains('-')) {
                    if (check.split('-')[0] == temp[0]) {
                        return@forEachIndexed
                    }
                }
            }
            var boxNum = hashValue(temp[0])
            var i = -1
            boxes[boxNum]?.forEach {index, maps->
                maps.forEach {lens ->
                    if(lens.key == temp[0]) {
                        i = index
                    }
                }
            }
            if (i > -1) {
                boxes[boxNum]?.set(i, hashMapOf(temp[0] to temp[1].toInt()))
            } else {
                if (boxes[boxNum] == null) {
                    boxes[boxNum] = hashMapOf(0 to hashMapOf(temp[0] to temp[1].toInt()))
                } else {
                    boxes[boxNum]?.set(boxes[boxNum]!!.size, hashMapOf(temp[0] to temp[1].toInt()))
                }
            }
        }
    }
    var boxSum = 0
    boxes.forEach{boxNum, box ->
        box?.forEach { index, maps ->
            maps.forEach { lens ->
                boxSum += (boxNum + 1) * (index + 1) * lens.value.toInt()
            }
        }
    }
    println(boxSum)
}
