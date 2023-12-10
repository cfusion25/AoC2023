import java.io.File
import java.io.InputStream


fun checkStart(y: Int, x: Int, map: MutableList<MutableList<Char>>) :Pair<Pair<Int,Int>,Pair<Int,Int>> {
    val dir = mutableListOf<Pair<Int,Int>>()

    val up = map.getOrNull(y-1)?.getOrNull(x)
    val down = map.getOrNull(y+1)?.getOrNull(x)
    val left = map.getOrNull(y)?.getOrNull(x-1)
    val right = map.getOrNull(y)?.getOrNull(x+1)
    if (up != null) {
        val p = traverse(y-1,x,up)
        if ((p.first.first == y && p.first.second == x) || (p.second.first == y && p.second.second == x)) {
            dir.add(Pair(y-1,x))
        }
    }
    if (down != null) {
        val p = traverse(y+1,x,down)
        if ((p.first.first == y && p.first.second == x) || (p.second.first == y && p.second.second == x)) {
            dir.add(Pair(y+1,x))
        }
    }
    if (left != null) {
        val p = traverse(y,x-1,left)
        if ((p.first.first == y && p.first.second == x) || (p.second.first == y && p.second.second == x)) {
            dir.add(Pair(y,x-1))
        }
    }
    if (right != null) {
        val p = traverse(y,x+1,right)
        if ((p.first.first == y && p.first.second == x) || (p.second.first == y && p.second.second == x)) {
            dir.add(Pair(y,x+1))
        }
    }

    return Pair(dir[0],dir[1])
}

fun traverse(y: Int, x: Int, c: Char) :Pair<Pair<Int,Int>,Pair<Int,Int>> {
    if (c == '|') {
        return Pair(Pair(y-1,x),Pair(y+1,x))
    }
    if (c == '-') {
        return Pair(Pair(y,x-1),Pair(y,x+1))
    }
    if (c == 'L') {
        return Pair(Pair(y,x+1),Pair(y-1,x))
    }
    if (c == 'J') {
        return Pair(Pair(y,x-1),Pair(y-1,x))
    }
    if (c == '7') {
        return Pair(Pair(y+1,x),Pair(y,x-1))
    }
    if (c == 'F') {
        return Pair(Pair(y+1,x),Pair(y,x+1))
    }
    return Pair(Pair(y,x),Pair(y,x))
}



fun main(args: Array<String>) {
    val inputStream: InputStream = File("C:\\Users\\coldf\\OneDrive\\Desktop\\AoC2023\\input\\d10.txt").inputStream()
    val lineList = mutableListOf<MutableList<Char>>()

    inputStream.bufferedReader().forEachLine { line->
        val charList = mutableListOf<Char>()
        line.forEach {
            charList.add(it)
        }
        lineList.add(charList)
    }
    val pastLoc = mutableListOf<Pair<Int,Int>>()
    var start: Pair<Int, Int>
    var dir = Pair(Pair(0,0),Pair(0,0))
    lineList.forEachIndexed { i, line ->
        line.forEachIndexed { j, c ->
            if (c == 'S') {
                start = Pair(i,j)
                pastLoc.add(start)
                dir = checkStart(start.first, start.second, lineList)
            }
        }
    }

    var sCount = 0
    while(true) {
        sCount += 1
        pastLoc.add(dir.first)
        pastLoc.add(dir.second)
        val temp = mutableListOf<Pair<Int,Int>>()

        var p = traverse(dir.first.first,dir.first.second, lineList[dir.first.first][dir.first.second])
        var p11 = true
        var p12 = true
        pastLoc.forEach {
            if(p.first.first == it.first && p.first.second == it.second) {
                p11 = false
            }
            if(p.second.first == it.first && p.second.second == it.second) {
                p12 = false
            }
        }
        if (p11) {
            temp.add(Pair(p.first.first,p.first.second))
        }
        if (p12) {
            temp.add(Pair(p.second.first,p.second.second))
        }
        if(!p11 && !p12) {
            break
        }

        p = traverse(dir.second.first,dir.second.second, lineList[dir.second.first][dir.second.second])
        var p21 = true
        var p22 = true
        pastLoc.forEach {
            if(p.first.first == it.first && p.first.second == it.second) {
                p21 = false
            }
            if(p.second.first == it.first && p.second.second == it.second) {
                p22 = false
            }
        }
        if (p21) {
            temp.add(Pair(p.first.first,p.first.second))
        }
        if (p22) {
            temp.add(Pair(p.second.first,p.second.second))
        }
        if(!p21 && !p22) {
            break
        }

        dir = Pair(temp[0],temp[1])
    }
    println(sCount)

//    lineList[start.first][start.second] = '7'
    val pastSet = pastLoc.toSet()
    var encircled = 0
    lineList.forEachIndexed { i, line ->
        line.forEachIndexed inner@ { j, c ->
            var inPastCountR = 0
            var bend = ' '
            pastSet.forEach {
                if(it.first == i && it.second == j) {
                    return@inner
                }
            }
            for (k in j+1..<line.size) {
                run loop@{
                    pastSet.forEach {
                        if(it.first == i && it.second == k) {
                            if (lineList[i][k] == '|') {
                                inPastCountR += 1
                            }
                            if (lineList[i][k] == 'F' || lineList[i][k] == 'L') {
                                bend = lineList[i][k]
                            }
                            if ((lineList[i][k] == 'J' && bend == 'L') || (lineList[i][k] == '7' && bend == 'F')) {
                                inPastCountR += 2
                            }
                            if ((lineList[i][k] == 'J' && bend == 'F') || (lineList[i][k] == '7' && bend == 'L')) {
                                inPastCountR += 1
                            }
                            return@loop
                        }
                    }
                }
            }
            if((inPastCountR % 2 == 1)) {
                encircled += 1
            }
        }
    }
    println(encircled)
}