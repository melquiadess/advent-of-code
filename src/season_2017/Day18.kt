package season_2017

import AbstractDay
import java.util.*

/**
 * Created by melquiadess on 18/12/2017.
 * Part 1:
 *  0 - wrong
 *
 * Part 2:
 */
class Day18 : AbstractDay("Day18") {
    var list: MutableList<String> = mutableListOf()

    data class Instruction(val name: String, val register: String, var value: String)

    private val instructions: MutableList<Instruction> = mutableListOf()

    var a: Long = 0L
    var b: Long = 0L
    var i: Long = 0L
    var f: Long = 0L
    var p: Long = 0L

    var opIndex = 0

    var a1: Long = 0L
    var b1: Long = 0L
    var i1: Long = 0L
    var f1: Long = 0L
    var p1: Long = 0L

    var opIndex1 = 0

    var queue1 = ArrayDeque<Instruction>()
    var queue2 = ArrayDeque<Instruction>()

    private fun getInput() {
//        list = readFile("src/season_2017/input/day18-input-test")
        list = readFile("src/season_2017/input/day18-input")
    }

    private fun initialiseRegisters() {
        list.forEach { instr ->
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
        "i" -> if (pid == 0) i else i1
        "f" -> if (pid == 0) f else f1
        "p" -> if (pid == 0) p else p1
        else -> -666
    }

    fun setRegisterValue(register: String, value: Long, pid: Int) {
        when (register) {
            "a" -> if (pid == 0) a = value else a1 = value
            "b" -> if (pid == 0) b = value else b1 = value
            "i" -> if (pid == 0) i = value else i1 = value
            "f" -> if (pid == 0) f = value else f1 = value
            "p" -> if (pid == 0) p = value else p1 = value
            else -> println("Can't set register: $register")
        }
    }

    private fun startOperations(pid: Int) {
        var lastFrequencyPlayed: Long = 0L

        loop@ while (true) {
            var op = instructions[opIndex]
            print("==> operation: ${op.name} ${op.register} ${op.value}  :::  ")

            when (op.name) {
                "snd" -> {
                    // snd X plays a sound with a frequency equal to the value of X.
                    println("PLAYING ${op.register}")
                    lastFrequencyPlayed = getRegisterValue(op.register, pid)
                    opIndex++
                }
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
                "add" -> {
                    // add X Y increases register X by the value of Y.
                    var increaseValue = 0L
                    if (!op.value.isLetter()) {
                        increaseValue = op.value.toLong()
                    } else {
                        increaseValue = getRegisterValue(op.value, pid)
                    }
                    var currentValue = getRegisterValue(op.register, pid)
                    setRegisterValue(op.register, currentValue + increaseValue, pid)
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
                }
                "mod" -> {
                    // mod X Y sets register X to the remainder of dividing the value contained in register X
                    // by the value of Y (that is, it sets X to the result of X modulo Y)
                    var moduloValue = 1L
                    if (!op.value.isLetter()) {
                        moduloValue = op.value.toLong()
                    } else {
                        moduloValue = getRegisterValue(op.value, pid)
                    }

                    val currentValue = getRegisterValue(op.register, pid)
                    setRegisterValue(op.register, currentValue.rem(moduloValue), pid)
                    opIndex++
                }
                "rcv" -> {
                    // rcv X recovers the frequency of the last sound played, but only when the value of X is not zero.
                    // (If it is zero, the command does nothing.)
                    val regValue = getRegisterValue(op.register, pid)
                    println("Value in ${op.register} is $regValue...")

                    if (regValue != 0L) {
                        break@loop
                    }

                    opIndex++
                }
                "jgz" -> {
                    // jgz X Y jumps with an offset of the value of Y, but only if the value of X is greater than zero.
                    // (An offset of 2 skips the next instruction, an offset of -1 jumps to the previous instruction, and so on.)
                    var regValue = 0L
                    if (!op.register.isLetter()) {
                        regValue = op.register.toLong()
                    } else {
                        regValue = getRegisterValue(op.register, pid)
                    }

                    if (regValue > 0L) {
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

        println("Last played frequency: $lastFrequencyPlayed")
    }

    private fun startOperations2(pid: Int) {
        var lastFrequencyPlayed: Long = 0L

        loop@ while (true) {
            var op = instructions[opIndex]
            print("==> operation: ${op.name} ${op.register} ${op.value}  :::  ")

            when (op.name) {
                "snd" -> {
                    // snd X plays a sound with a frequency equal to the value of X.
                    println("PLAYING ${op.register}")
                    lastFrequencyPlayed = getRegisterValue(op.register, pid)
                    opIndex++
                }
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
                "add" -> {
                    // add X Y increases register X by the value of Y.
                    var increaseValue = 0L
                    if (!op.value.isLetter()) {
                        increaseValue = op.value.toLong()
                    } else {
                        increaseValue = getRegisterValue(op.value, pid)
                    }
                    var currentValue = getRegisterValue(op.register, pid)
                    setRegisterValue(op.register, currentValue + increaseValue, pid)
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
                }
                "mod" -> {
                    // mod X Y sets register X to the remainder of dividing the value contained in register X
                    // by the value of Y (that is, it sets X to the result of X modulo Y)
                    var moduloValue = 1L
                    if (!op.value.isLetter()) {
                        moduloValue = op.value.toLong()
                    } else {
                        moduloValue = getRegisterValue(op.value, pid)
                    }

                    val currentValue = getRegisterValue(op.register, pid)
                    setRegisterValue(op.register, currentValue.rem(moduloValue), pid)
                    opIndex++
                }
                "rcv" -> {
                    // rcv X recovers the frequency of the last sound played, but only when the value of X is not zero.
                    // (If it is zero, the command does nothing.)
                    val regValue = getRegisterValue(op.register, pid)
                    println("Value in ${op.register} is $regValue...")

                    if (regValue != 0L) {
                        break@loop
                    }

                    opIndex++
                }
                "jgz" -> {
                    // jgz X Y jumps with an offset of the value of Y, but only if the value of X is greater than zero.
                    // (An offset of 2 skips the next instruction, an offset of -1 jumps to the previous instruction, and so on.)
                    var regValue = 0L
                    if (!op.register.isLetter()) {
                        regValue = op.register.toLong()
                    } else {
                        regValue = getRegisterValue(op.register, pid)
                    }

                    if (regValue > 0L) {
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

        println("Last played frequency: $lastFrequencyPlayed")
    }

    fun printRegisters() {
        println("[a=$a, b=$b, f=$f, i=$i, p=$p] ==> $opIndex")
    }

    override fun part1() {
        println("PART 1")
        getInput()

        println(list)

        initialiseRegisters()
        println(instructions)

        startOperations(0)
    }

    override fun part2() {
        println("PART 2")
    }
}