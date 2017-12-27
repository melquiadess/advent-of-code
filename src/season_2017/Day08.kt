package season_2017

import AbstractDay
/**
 * Created by melquiadess on 07/12/2017.
 * Part 1:
 * 965 - too low
 * Part 2:
 */
class Day08 : AbstractDay("Day08") {
    var list: MutableList<String> = mutableListOf()
    var registers: MutableMap<String, Int> = mutableMapOf()

    var highest = Int.MIN_VALUE

    override fun part1() {
        println("PART 1")

        getInput()

        println(list)
        process(list)

        println(registers)
        var max = registers.maxBy { it.value }
        println("max: $max")
        println("highest: $highest")
    }

    private fun getInput() {
        list = readFile("src/season_2017/input/day08-input")
    }

    private fun process(list: MutableList<String>) {
        list.forEach { line ->
            processLine(line)
        }
    }

    private fun processLine(line: String) {
        var splits = line.split(" ")

        // c inc -20 if c == 10
        var reg = splits[0]
        var op = splits[1]
        var opValue = splits[2].toInt()
        var regComp = splits[4]
        var opComp = splits[5]
        var valComp = splits[6].toInt()

        var currentRegisterValue = registers.getOrElse(reg, { 0 })
        var currentRegCompValue = registers.getOrElse(regComp, { 0 })

        when (opComp) {
            ">" -> {
                if (currentRegCompValue > valComp) {
                    currentRegisterValue += doCalculation(op, opValue)
                    if (currentRegisterValue > highest) highest = currentRegisterValue
                    registers.set(reg, currentRegisterValue)
                }
            }
            "<" -> {
                if (currentRegCompValue < valComp) {
                    currentRegisterValue += doCalculation(op, opValue)
                    if (currentRegisterValue > highest) highest = currentRegisterValue
                    registers.set(reg, currentRegisterValue)
                }
            }
            ">=" -> {
                if (currentRegCompValue >= valComp) {
                    currentRegisterValue += doCalculation(op, opValue)
                    if (currentRegisterValue > highest) highest = currentRegisterValue
                    registers.set(reg, currentRegisterValue)
                }
            }
            "<=" -> {
                if (currentRegCompValue <= valComp) {
                    currentRegisterValue += doCalculation(op, opValue)
                    if (currentRegisterValue > highest) highest = currentRegisterValue
                    registers.set(reg, currentRegisterValue)
                }
            }
            "==" -> {
                if (currentRegCompValue == valComp) {
                    currentRegisterValue += doCalculation(op, opValue)
                    if (currentRegisterValue > highest) highest = currentRegisterValue
                    registers.set(reg, currentRegisterValue)
                }
            }
            "!=" -> {
                if (currentRegCompValue != valComp) {
                    currentRegisterValue += doCalculation(op, opValue)
                    if (currentRegisterValue > highest) highest = currentRegisterValue
                    registers.set(reg, currentRegisterValue)
                }
            }
        }
    }

    private fun doCalculation(op: String, value: Int): Int = when (op) {
        "inc" -> value
        "dec" -> -value
        else -> 0
    }

    override fun part2() {
        println("PART 2")
    }
}