import java.io.File
import java.io.InputStream

fun checkPart(y: Int, x: Int, len: Int, engine: MutableList<MutableList<Char>>) :Boolean {
    // Find if the number at location [y][x] of length len is next to a symbol
    var string = ""
    val regex = Regex("[^0-9 .]")

    // Unrolls the 2D engine schematic into a 1D string
    for (i in maxOf(y - 1, 0)..minOf(y + 1, engine.size - 1)) {
        for (j in maxOf(x - 1, 0)..minOf(x + len, engine[y].size - 1)) {
            string += engine[i][j]
        }
    }

    // Run regex to see if there is a symbol in the string
    val matches = regex.find(string)
    if (matches != null) {
        return true
    }

    return false
}

fun checkGear(y: Int, x: Int, engine: MutableList<MutableList<Char>>) :Int {
    // PAIN PAIN PAIN PAIN PAIN PAIN PAIN PAIN PAIN PAIN PAIN PAIN PAIN PAIN PAIN PAIN PAIN PAIN PAIN PAIN PAIN PAIN PAIN PAIN PAIN

    // Checks to see if there are EXACTLY two numbers bordering a gear
    var strings = mutableListOf<String>()
    val regex = Regex("[0-9]+")

    // Takes a 3x3 slice center on the gear
    for (i in maxOf(y - 1, 0)..minOf(y + 1, engine.size - 1)) {
        var string = ""
        for (j in maxOf(x - 1, 0)..minOf(x + 1, engine[y].size - 1)) {
            string += engine[i][j]
        }
        strings.add(string)
    }

    // Checks if there is a number in the 3x3 slice and notes how many numbers are in each row
    var nums = 0
    val loc = mutableListOf<Int>()
    strings.forEachIndexed {index, line ->
        val matches = regex.findAll(line)
        val string = matches.map{it.groupValues[0]}.joinToString()
        if (string != "") {
            val test = string.split(", ")
            nums += test.size
            loc.add(test.size)
        }
        else {
            loc.add(0)
        }
    }

    // Avert your eyes, for if you stare into the abyss, the abyss stares back
    // If there are EXACTLY 2 numbers bordering the gear identify the numbers
    var gear = mutableListOf<Int>()
    if (nums == 2) {
        for (i in 0..2) {
            if (loc[i] == 1) {
                // If the row has a single number
                for (k in 0..2) {
                    // Iterate through the row to find a digit then iterate left and right up to 2 tiles to find the full number
                    var mul = ""
                    val point = engine[y + i - 1][x + k - 1]
                    if (point.isDigit() && point != '.') {
                        mul += point
                        for (l in 1..2) {
                            var left = engine[y + i - 1][x + k - 1 - l]
                            if (left.isDigit() && left != '.') {
                                mul = left + mul
                            }
                            else {
                                break
                            }
                        }
                        for (r in 1..2) {
                            var right = engine[y + i - 1][x + k - 1 + r]
                            if (right.isDigit() && right != '.') {
                                mul = mul + right
                            }
                            else {
                                break
                            }
                        }
                        // Store the number in the gear list
                        gear.add(mul.toInt())
                        break
                    }
                }
            }
            if (loc[i] == 2) {
                // If the row has both numbers in it they must be in the corners of the 3x3 centered on the gear
                val point1 = engine[y + i - 1][x - 1]
                val point2 = engine[y + i - 1][x + 1]

                var mul1 = "" + point1
                var mul2 = "" + point2

                // Starting from the corners of the 3x3 iterate left and right respectively
                for (l in 1..2) {
                    var left = engine[y + i - 1][x - 1 - l]
                    if (left.isDigit() && left != '.') {
                        mul1 = left + mul1
                    } else {
                        break
                    }
                }
                for (r in 1..2) {
                    var right = engine[y + i - 1][x + 1 + r]
                    if (right.isDigit() && right != '.') {
                        mul2 = mul2 + right
                    } else {
                        break
                    }
                }
                // Return the multiple of the two numbers
                return mul1.toInt() * mul2.toInt()
            }
        }
        var rvalue = 1
        gear.forEach{
            // Iterate through the gear list and return the multiple
            rvalue *= it
        }
        return rvalue
    }

    // Return 0 if there are not EXACTLY 2 bordering numbers
    return 0
}

fun main(args: Array<String>) {
    val inputStream: InputStream = File("C:\\Users\\coldf\\OneDrive\\Desktop\\AoC2023\\input\\d3.txt").inputStream()
    val lineList = mutableListOf<MutableList<Char>>()

    var eSum = 0

    // Populate List of Lists to represent the 2D engine schematic
    inputStream.bufferedReader().forEachLine { line->
        val charList = mutableListOf<Char>()
        line.forEach {
            charList.add(it)
        }
        lineList.add(charList) }

    // Part 1
    lineList.forEachIndexed { i, line ->
        var it = 0
        while (it < line.size) {
            var num = ""
            // Iterating through the list of lists by character to find a number
            if (line[it].isDigit() && line[it] != '.'){
                var len = 1
                num += line[it]
                // Once you find a number, continue iterating to find the end of the number or line
                while (it + len < line.size && line[it + len].isDigit() && line[it] != '.') {
                    num += line[it + len]
                    len += 1
                }
                // Check if a number is a part then add it to the sum if it is
                if (checkPart(i, it, len, lineList)) {
                    eSum += num.toInt()
                }
                // Move the iterator by the length of the number to avoid double counting
                it += len
            }
            else {
                it += 1
            }
        }
    }
    println(eSum)

    // Part 2
    var gSum = 0
    lineList.forEachIndexed { i, line ->
        for (j in 0..line.size - 1) {
            // Iterate through the engine schematic until you find a *
            if (line[j] == '*'){
                // Check if the * is a gear and add it to the sum
                val gear = checkGear(i,j,lineList)
                gSum += gear
            }
        }
    }
    println(gSum)
}
