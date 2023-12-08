import java.io.File
import java.io.InputStream
import java.lang.Math.sqrt


fun findSteps (current: String, full: Int, instructions: List<Char>, path: MutableMap<String, Pair<String, String>>) :Int {
    var loc = current
    var steps = 0
    if(full == 1) {
        while (loc != "ZZZ") {
            run iterator@{
                instructions.forEach { direction ->
                    steps += 1
                    if (direction == 'L') {
                        loc = path[loc]!!.first
                    } else {
                        loc = path[loc]!!.second
                    }
                    if (loc == "ZZZ") {
                        return@iterator
                    }
                }
            }
        }
    } else {
        while (loc.last() != 'Z') {
            run iterator@{
                instructions.forEach { direction ->
                    steps += 1
                    if (direction == 'L') {
                        loc = path[loc]!!.first
                    } else {
                        loc = path[loc]!!.second
                    }
                    if (loc.last() == 'Z') {
                        return@iterator
                    }
                }
            }
        }

    }
    return steps
}

fun main(args: Array<String>) {
    val inputStream: InputStream = File("C:\\Users\\coldf\\OneDrive\\Desktop\\AoC2023\\input\\d8.txt").inputStream()
    val lineList = mutableListOf<String>()
    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    // Part 1
    val path = mutableMapOf<String, Pair<String,String>>()
    val instructions = lineList[0].toList()
//    print(instructions)
    lineList.slice(2..lineList.size-1).forEach {
        val parse = it.split(" = ")
        val leftRight = parse[1].substring(1..8).split(", ")
        path[parse[0]] = Pair(leftRight[0],leftRight[1])
    }

    println(findSteps("AAA",1,instructions,path))

    // Part 2 - Yo wtf is this question???
    val starts = mutableListOf<String>()
    path.forEach {
        if(it.key.last() == 'A') {
            starts.add(it.key)
        }
    }
    val ends = mutableListOf<Int>()
    starts.forEach {
        ends.add(findSteps(it,0,instructions,path))
    }

    val primes = mutableListOf<Int>()
    ends.forEach {
        var n = it
        while(n % 2 == 0) {
            primes.add(2)
            n /= 2
        }
        val squareRoot = sqrt(n.toDouble()).toInt()
        for (i in 3..squareRoot step 2) {
            while(n % i == 0) {
                primes.add(i)
                n /= i
            }
        }
        if ( n > 2) {
            primes.add(n)
        }
    }

    var product:Long = 1
    primes.toSet().forEach {
        product *= it.toLong()
    }
    println(product)
}