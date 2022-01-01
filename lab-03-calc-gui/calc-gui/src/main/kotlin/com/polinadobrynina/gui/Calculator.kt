package com.polinadobrynina.gui

import com.polinadobrynina.calculator.*

import javax.swing.*
import java.awt.EventQueue
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets


class Calculator : JFrame() {

    private val result = JLabel()
    private val input = JTextField()
    private val variables: MutableMap<String, Double> = HashMap()
    private val dataModel = DefaultListModel<String>()

    init {
        title = "calculator;)"
        createLayout()
        defaultCloseOperation = EXIT_ON_CLOSE
        setLocationRelativeTo(null)
        setSize(700, 400)
        input.horizontalAlignment = SwingConstants.LEFT
    }

    private fun createLayout() {
        val gridBagLayout = GridBagLayout()
        val gridBagConstraints = GridBagConstraints()
        contentPane.layout = gridBagLayout
        gridBagConstraints.fill = GridBagConstraints.BOTH
        gridBagConstraints.weightx = 1.0
        gridBagConstraints.weighty = 1.0
        gridBagConstraints.insets = Insets(1, 1, 1, 1)

        gridBagConstraints.gridx = 0
        gridBagConstraints.gridy = 0
        gridBagConstraints.gridwidth = 3
        contentPane.add(input, gridBagConstraints)
        gridBagConstraints.gridwidth = 1

        gridBagConstraints.gridx = 3
        gridBagConstraints.gridy = 0
        contentPane.add(result, gridBagConstraints)

//        var k = 1
//        val digitButtons : ArrayList<JButton> = arrayListOf()
//        for (i in 4 downTo 2) {
//            for (j in 0..2) {
//                digitButtons.add(JButton(k.toString()))
//                digitButtons[k-1].addActionListener { addChar((k+39).toChar()) }
//                gridBagConstraints.gridx = j
//                gridBagConstraints.gridy = i
//                contentPane.add(digitButtons[k-1], gridBagConstraints)
//                k += 1
//            }
//        }

        val button0 = JButton("0")
        button0.addActionListener { addChar('0') }
        gridBagConstraints.gridx = 1
        gridBagConstraints.gridy = 5
        contentPane.add(button0, gridBagConstraints)

        val button1 = JButton("1")
        button1.addActionListener { addChar('1') }
        gridBagConstraints.gridx = 0
        gridBagConstraints.gridy = 4
        contentPane.add(button1, gridBagConstraints)

        val button2 = JButton("2")
        button2.addActionListener { addChar('2') }
        gridBagConstraints.gridx = 1
        gridBagConstraints.gridy = 4
        contentPane.add(button2, gridBagConstraints)

        val button3 = JButton("3")
        button3.addActionListener { addChar('3') }
        gridBagConstraints.gridx = 2
        gridBagConstraints.gridy = 4
        contentPane.add(button3, gridBagConstraints)

        val button4 = JButton("4")
        button4.addActionListener { addChar('4') }
        gridBagConstraints.gridx = 0
        gridBagConstraints.gridy = 3
        contentPane.add(button4, gridBagConstraints)

        val button5 = JButton("5")
        button5.addActionListener { addChar('5') }
        gridBagConstraints.gridx = 1
        gridBagConstraints.gridy = 3
        contentPane.add(button5, gridBagConstraints)

        val button6 = JButton("6")
        button6.addActionListener { addChar('6') }
        gridBagConstraints.gridx = 2
        gridBagConstraints.gridy = 3
        contentPane.add(button6, gridBagConstraints)

        val button7 = JButton("7")
        button7.addActionListener { addChar('7') }
        gridBagConstraints.gridx = 0
        gridBagConstraints.gridy = 2
        contentPane.add(button7, gridBagConstraints)

        val button8 = JButton("8")
        button8.addActionListener { addChar('8') }
        gridBagConstraints.gridx = 1
        gridBagConstraints.gridy = 2
        contentPane.add(button8, gridBagConstraints)

        val button9 = JButton("9")
        button9.addActionListener { addChar('9') }
        gridBagConstraints.gridx = 2
        gridBagConstraints.gridy = 2
        contentPane.add(button9, gridBagConstraints)

        val buttonEq = JButton("=")
        buttonEq.addActionListener { evaluateExpression() }
        gridBagConstraints.gridx = 2
        gridBagConstraints.gridy = 1
        contentPane.add(buttonEq, gridBagConstraints)

        val buttonAdd = JButton("+")
        buttonAdd.addActionListener { addChar('+') }
        gridBagConstraints.gridx = 3
        gridBagConstraints.gridy = 1
        contentPane.add(buttonAdd, gridBagConstraints)

        val buttonSub = JButton("-")
        buttonSub.addActionListener { addChar('-') }
        gridBagConstraints.gridx = 3
        gridBagConstraints.gridy = 2
        contentPane.add(buttonSub, gridBagConstraints)

        val buttonVar = JButton("var")
        buttonVar.addActionListener { addVariables() }
        gridBagConstraints.gridx = 0
        gridBagConstraints.gridy = 5
        contentPane.add(buttonVar, gridBagConstraints)

        val buttonMul = JButton("*")
        buttonMul.addActionListener { addChar('*') }
        gridBagConstraints.gridx = 3
        gridBagConstraints.gridy = 3
        contentPane.add(buttonMul, gridBagConstraints)

        val buttonDiv = JButton("/")
        buttonDiv.addActionListener { addChar('/') }
        gridBagConstraints.gridx = 3
        gridBagConstraints.gridy = 4
        contentPane.add(buttonDiv, gridBagConstraints)

        val buttonBrOpen = JButton("(")
        buttonBrOpen.addActionListener { addChar('(') }
        gridBagConstraints.gridx = 0
        gridBagConstraints.gridy = 1
        contentPane.add(buttonBrOpen, gridBagConstraints)

        val buttonBrClose = JButton(")")
        buttonBrClose.addActionListener { addChar(')') }
        gridBagConstraints.gridx = 1
        gridBagConstraints.gridy = 1
        contentPane.add(buttonBrClose, gridBagConstraints)

        val buttonDebug = JButton("debug info")
        buttonDebug.addActionListener { displayDebugInfo() }
        gridBagConstraints.gridx = 2
        gridBagConstraints.gridy = 5
        contentPane.add(buttonDebug, gridBagConstraints)

        val buttonAC = JButton("AC")
        buttonAC.addActionListener { clearInput() }
        gridBagConstraints.gridx = 3
        gridBagConstraints.gridy = 5
        contentPane.add(buttonAC, gridBagConstraints)

        gridBagConstraints.gridx = 4
        gridBagConstraints.gridy = 0
        gridBagConstraints.gridheight = 6
        val bListGui = JList(dataModel)
        contentPane.add(bListGui, gridBagConstraints)
    }

    private fun clearInput() {
        input.text = ""
        result.text = ""
    }

    private fun evaluateExpression() {
        val expression: Expression

        try {
            expression = ParserImpl.INSTANCE.parseExpression(input.text)
        } catch (e: Exception) {
            result.text = "Syntactic error"
            return
        }

        try {
            result.text = "result: " + expression.accept(CalcVisitor(variables)).toString()
        } catch (e: Exception) {
            result.text = "Compilation error"
        }
    }

    private fun addChar(char: Char) {
        val str = StringBuilder(input.text).append(char)
        input.text = str.toString()
    }

    private fun reloadList() {
        dataModel.clear()
        for ((variable, value) in variables) {
            dataModel.addElement("$variable = $value")
        }
    }

    private fun addVariables() {
        val inputStr = JOptionPane.showInputDialog("Please, enter variables(example 'a=42')").split("=")

        if (inputStr.size != 2) {
            JOptionPane.showMessageDialog(this, "Invalid input")
            return
        }

        val name = inputStr[0]
        if (name.length != 1) {
            JOptionPane.showMessageDialog(this, "Variable name must be a letter")
            return
        }

        val nameChar = name[0]
        try {
            variables[nameChar.toString()] = inputStr[1].toDouble()
        } catch (e: Exception) {
            JOptionPane.showMessageDialog(this, "Invalid value")
            return
        }
        reloadList()
    }

    private fun displayDebugInfo() {
        try {
            val expression = ParserImpl.INSTANCE.parseExpression(input.text);
            val debugStr = expression.accept(DebugRepresentationExpressionVisitor.INSTANCE) as String
            val depthStr = expression.accept(DepthTreeVisitor.INSTANCE).toString()
            val resultStr = "Debug representation: $debugStr\n Depth: $depthStr";
            JOptionPane.showMessageDialog(this, resultStr)
        } catch (e: Exception) {
            JOptionPane.showMessageDialog(this, "Invalid expression")
        }
    }
}

private fun initWindow() {
    val window = Calculator()
    window.isVisible = true
}

fun main() {
    EventQueue.invokeLater(::initWindow)
}
