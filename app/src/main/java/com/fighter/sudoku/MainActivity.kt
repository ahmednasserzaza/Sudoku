package com.fighter.sudoku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    private lateinit var numbersId: Array<Int>
    private lateinit var btnClear: Button
    private lateinit var btnSolve: Button
    private lateinit var sudokuGrid: GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        generateCellsId()
        listeners()

    }

    private fun listeners() {
        btnClear.setOnClickListener {
            clearCells()
        }
        btnSolve.setOnClickListener {
            solve()
        }
    }

    private fun clearCells() {
        for (row in 0 until 9) {
            for (col in 0 until 9) {
                val cellIndex = row * 9 + col
                val tvCell = sudokuGrid.getChildAt(cellIndex) as TextView
                tvCell.text = ""
            }
        }
    }

    private fun initViews() {
        sudokuGrid = findViewById(R.id.gridLayout)
        numbersId = arrayOf(
            R.id.tv_one, R.id.tv_two, R.id.tv_three,
            R.id.tv_four, R.id.tv_five, R.id.tv_six,
            R.id.tv_seven, R.id.tv_eight, R.id.tv_nine
        )

        btnClear = findViewById(R.id.button_clear)
        btnSolve = findViewById(R.id.button_solve)
    }


    private fun generateCellsId() {
        var numCellsEntered = 0
        for (row in 0 until 9) {
            for (col in 0 until 9) {
                val cellIndex = row * 9 + col
                val tvCell = sudokuGrid.getChildAt(cellIndex) as TextView
                tvCell.setOnClickListener {
                    if (numCellsEntered < 17) {
                        for (textViewId in numbersId) {
                            val tvNumber = findViewById<TextView>(textViewId)
                            tvNumber.setOnClickListener {
                                if (isValidPlacement(row, col, tvNumber.text.toString().toInt())) {
                                    tvCell.text = tvNumber.text.toString()
                                    numCellsEntered++
                                }
                                else{
                                    Toast.makeText(
                                        this,
                                        "Wrong number",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                            }
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "You should enter only 17 numbers or lower",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }


    private fun solve(): Boolean {
        for (row in 0 until 9) {
            for (col in 0 until 9) {
                val cellIndex = row * 9 + col
                val tvCell = sudokuGrid.getChildAt(cellIndex) as TextView
                if (tvCell.text.isEmpty()) {
                    for (number in 1..9) {
                        if (isValidPlacement(row, col, number)) {
                            tvCell.text = number.toString()
                            if (solve()) {
                                return true
                            }
                            tvCell.text = ""
                        }
                    }
                    return false
                }
            }
        }
        return true
    }



    private fun getCellValue(row: Int, col: Int): Int? {
        val cellIndex = row * 9 + col
        val tvCell = sudokuGrid.getChildAt(cellIndex) as TextView
        return tvCell.text.toString().toIntOrNull()
    }

    private fun isValidPlacement(row: Int, col: Int, number: Int): Boolean {
        return !usedInRow(row, number) &&
                !usedInCol(col, number) &&
                !usedInBox(row - row % 3, col - col % 3, number)
    }

    private fun usedInRow(row: Int, number: Int): Boolean {
        for (col in 0 until 9) {
            if (getCellValue(row, col) == number) {
                return true
            }
        }
        return false
    }

    private fun usedInCol(col: Int, number: Int): Boolean {
        for (row in 0 until 9) {
            if (getCellValue(row, col) == number) {
                return true
            }
        }
        return false
    }

    private fun usedInBox(boxStartRow: Int, boxStartCol: Int, number: Int): Boolean {
        for (row in 0 until 3) {
            for (col in 0 until 3) {
                if (getCellValue(boxStartRow + row, boxStartCol + col) == number) {
                    return true
                }
            }
        }
        return false
    }

}



