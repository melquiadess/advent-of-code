package season_2017

import AbstractDay
import java.util.*

/**
 * Created by melquiadess on 21/12/2017.
 * Part 1:
 * Part 2:
 *  0 - wrong
 */
class Day23 : AbstractDay("Day23") {
    var list: MutableList<String> = mutableListOf()

    data class Instruction(val name: String, val register: String, var value: String)

    private val instructions: MutableList<Instruction> = mutableListOf()

    var a: Long = 1L
    var b: Long = 0L
    var c: Long = 0L
    var d: Long = 0L
    var e: Long = 0L
    var f: Long = 0L
    var g: Long = 0L
    var h: Long = 0L

    var a1: Long = 0L
    var b1: Long = 0L
    var c1: Long = 0L
    var d1: Long = 0L
    var e1: Long = 0L
    var f1: Long = 0L
    var g1: Long = 0L
    var h1: Long = 0L

    var opIndex = 0

    private fun getInput() {
//        list = readFile("src/season_2017/input/day23-input-test")
        list = readFile("src/season_2017/input/day23-input")
    }

    private fun initialiseRegisters() {
        list.forEach { instr ->
            // set b 79
            
            val splits = instr.split(" ")
            val name = splits[0].trim()
            val reg = splits[1].trim()
            var instr: String = ""

            if (splits.size>2) {
                instr = splits[2].trim()
            }
            instructions.add(Instruction(name, reg, instr))
        }
    }

    fun String.isLetter(): Boolean {
        return this.isNotEmpty() && this in "abcdefghijklmnopqrstuvwxyz"
    }

    fun getRegisterValue(register: String, pid: Int): Long = when (register) {
        "a" -> if (pid == 0) a else a1
        "b" -> if (pid == 0) b else b1
        "c" -> if (pid == 0) c else c1
        "d" -> if (pid == 0) d else d1
        "e" -> if (pid == 0) e else e1
        "f" -> if (pid == 0) f else f1
        "g" -> if (pid == 0) g else g1
        "h" -> if (pid == 0) h else h1
        else -> -666
    }

    fun setRegisterValue(register: String, value: Long, pid: Int) {
        when (register) {
            "a" -> if (pid == 0) a = value else a1 = value
            "b" -> if (pid == 0) b = value else b1 = value
            "c" -> if (pid == 0) c = value else c1 = value
            "d" -> if (pid == 0) d = value else d1 = value
            "e" -> if (pid == 0) e = value else e1 = value
            "f" -> if (pid == 0) f = value else f1 = value
            "g" -> if (pid == 0) g = value else g1 = value
            "h" -> if (pid == 0) h = value else h1 = value
            else -> println("Can't set register: $register")
        }
    }

    fun addRegisterValue(register: String, value: Long, pid: Int) {
        when (register) {
            "a" -> if (pid == 0) a += value else a1 += value
            "b" -> if (pid == 0) b += value else b1 += value
            "c" -> if (pid == 0) c += value else c1 += value
            "d" -> if (pid == 0) d += value else d1 += value
            "e" -> if (pid == 0) e += value else e1 += value
            "f" -> if (pid == 0) f += value else f1 += value
            "g" -> if (pid == 0) g += value else g1 += value
            "h" -> if (pid == 0) h += value else h1 += value
            else -> println("Can't set register: $register")
        }
    }

    private fun startOperations(pid: Int) {
        var mulCount: Long = 0L

        loop@ while (true) {
            if (opIndex >= instructions.size) break@loop
            var op = instructions[opIndex]
            print("==> operation: ${op.name} ${op.register} ${op.value}  :::  ")

            when (op.name) {
                "set" -> {
                    // set X Y sets register X to the value of Y.
                    // set a 1
                    var value = 0L
                    if (!op.value.isLetter()) {
                        value = op.value.toLong()
                    } else {
                        println("Getting value from ${op.value}")
                        value = getRegisterValue(op.value, pid)
                    }

                    setRegisterValue(op.register, value, pid)
                    opIndex++
                }
                "sub" -> {
                    // sub X Y decreases register X by the value of Y.
                    var value = 0L
                    if (!op.value.isLetter()) {
                        value = op.value.toLong()
                    } else {
                        println("Getting value from ${op.value}")
                        value = getRegisterValue(op.value, pid)
                    }

                    addRegisterValue(op.register, -value, pid)
                    opIndex++
                }
                "mul" -> {
                    // mul X Y sets register X to the result of multiplying the value contained in register X by the value of Y.
                    var multFactor = 1L
                    if (!op.value.isLetter()) {
                        multFactor = op.value.toLong()
                    } else {
                        multFactor = getRegisterValue(op.value, pid)
                    }
                    val currentValue = getRegisterValue(op.register, pid)
                    setRegisterValue(op.register, currentValue * multFactor, pid)
                    opIndex++

                    mulCount++
                }
                "jnz" -> {
                    // jnz X Y jumps with an offset of the value of Y, but only if the value of X is not zero.
                    // (An offset of 2 skips the next instruction, an offset of -1 jumps to the previous instruction, and so on.)
                    var regValue = 0L
                    if (!op.register.isLetter()) {
                        regValue = op.register.toLong()
                    } else {
                        regValue = getRegisterValue(op.register, pid)
                    }

                    if (regValue != 0L) {
                        val offset = op.value.toInt()
                        opIndex += offset
                    }
                    else {
                        opIndex++
                    }
                }
            }

            printRegisters()
        }

        println("MUL invoked $mulCount times")
    }

    private fun startOperations2(pid: Int) {
        var mulCount: Long = 0L

        loop@ while (true) {
            if (opIndex >= instructions.size) break@loop
            var op = instructions[opIndex]
            //print("==> operation: ${op.name} ${op.register} ${op.value}  :::  ")

            when (op.name) {
                "set" -> {
                    // set X Y sets register X to the value of Y.
                    // set a 1
                    var value = 0L
                    if (!op.value.isLetter()) {
                        value = op.value.toLong()
                    } else {
                        //println("Getting value from ${op.value}")
                        value = getRegisterValue(op.value, pid)
                    }

                    setRegisterValue(op.register, value, pid)
                    opIndex++
                }
                "sub" -> {
                    // sub X Y decreases register X by the value of Y.
                    var value = 0L
                    if (!op.value.isLetter()) {
                        value = op.value.toLong()
                    } else {
                        //println("Getting value from ${op.value}")
                        value = getRegisterValue(op.value, pid)
                    }

                    addRegisterValue(op.register, -value, pid)
                    opIndex++
                }
                "mul" -> {
                    // mul X Y sets register X to the result of multiplying the value contained in register X by the value of Y.
                    var multFactor = 1L
                    if (!op.value.isLetter()) {
                        multFactor = op.value.toLong()
                    } else {
                        multFactor = getRegisterValue(op.value, pid)
                    }
                    val currentValue = getRegisterValue(op.register, pid)
                    setRegisterValue(op.register, currentValue * multFactor, pid)
                    opIndex++

                    mulCount++
                }
                "jnz" -> {
                    // jnz X Y jumps with an offset of the value of Y, but only if the value of X is not zero.
                    // (An offset of 2 skips the next instruction, an offset of -1 jumps to the previous instruction, and so on.)
                    var regValue = 0L
                    if (!op.register.isLetter()) {
                        regValue = op.register.toLong()
                    } else {
                        regValue = getRegisterValue(op.register, pid)
                    }

                    if (regValue != 0L) {
                        val offset = op.value.toInt()
                        opIndex += offset
                    }
                    else {
                        opIndex++
                    }
                }
            }

            //printRegisters()
        }

        println("h register: $h")
    }

    fun printRegisters() {
        println("[a=$a, b=$b, c=$c d=$d e=$e f=$f, g=$g, h=$h] ==> (at index $opIndex)")
    }

    override fun part1() {
        println("PART 1")
//        getInput()
//
//        println(list)
//
//        initialiseRegisters()
//        println(instructions)
//
//        startOperations(0)
    }

    override fun part2() {
        println()
        println("PART 2")

        getInput()

        println(list)

        initialiseRegisters()
        println(instructions)

        startOperations2(0)
    }
}