import java.io.File
import java.io.InputStream
import java.lang.Math.sqrt
import kotlin.math.pow


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

    val primes = mutableListOf<List<Int>>()
    ends.forEach {
        var n = it
        val l = mutableListOf<Int>()
        while(n % 2 == 0) {
            l.add(2)
            n /= 2
        }
        val squareRoot = sqrt(n.toDouble()).toInt()
        for (i in 3..squareRoot step 2) {
            while(n % i == 0) {
                l.add(i)
                n /= i
            }
        }
        if ( n > 2) {
            l.add(n)
        }
        primes.add(l)
    }

    val primeExp = mutableMapOf<Int,Int>()
    primes.forEach {num ->
        val temp = num.groupingBy { it }.eachCount()
        temp.forEach {
            if (primeExp[it.key] == null) {
                primeExp[it.key] = it.value
            } else if (primeExp[it.key]!! > it.value) {
                primeExp[it.key] = it.value
            }
        }
    }

    var product:Long = 1
    primeExp.forEach {
        product *= it.key.toDouble().pow(it.value).toLong()
    }

    println(product)
}