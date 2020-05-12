package com.example.calculatorkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var inputNumber: EditText? = null


    private var buttonNumber1: Button? = null
    private var buttonNumber2: Button? = null
    private var buttonNumber3: Button? = null
    private var buttonNumber4: Button? = null
    private var buttonNumber5: Button? = null

    private var buttonNumber6: Button? = null
    private var buttonNumber7: Button? = null
    private var buttonNumber8: Button? = null
    private var buttonNumber9: Button? = null
    private var buttonNumber0: Button? = null

    private var buttonPlus: Button? = null
    private var buttonMinus: Button? = null
    private var buttonDivide: Button? = null
    private var buttonClear: Button? = null
    private var buttonPoint: Button? = null
    private var buttonEqual: Button? = null
    private var buttonMult: Button? = null
    private var buttonSquare: Button? = null
    private var buttonRoot: Button? = null
    //private Button buttonPercent;
    private var buttonUndo: Button? = null

    private var number1: Double = 0.toDouble()
    private var number2: Double = 0.toDouble()
    private var result: Double = 0.toDouble()

    private var op: String? = null

    private var isFirst: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputNumber = findViewById(R.id.textNumber)

        buttonNumber1 = findViewById(R.id.number1)
        buttonNumber2 = findViewById(R.id.number2)
        buttonNumber3 = findViewById(R.id.number3)
        buttonNumber4 = findViewById(R.id.number4)
        buttonNumber5 = findViewById(R.id.number5)
        buttonNumber6 = findViewById(R.id.number6)
        buttonNumber7 = findViewById(R.id.number7)
        buttonNumber8 = findViewById(R.id.number8)
        buttonNumber9 = findViewById(R.id.number9)
        buttonNumber0 = findViewById(R.id.number0)

        buttonPlus = findViewById(R.id.plus)
        buttonMinus = findViewById(R.id.minus)
        buttonPoint = findViewById(R.id.point)
        buttonDivide = findViewById(R.id.divide)

        buttonSquare = findViewById(R.id.square)
        buttonMult = findViewById(R.id.mult)
        buttonRoot = findViewById(R.id.root)

        buttonEqual = findViewById(R.id.equal)
        buttonUndo = findViewById(R.id.undo)

        buttonClear = findViewById(R.id.clear)

        buttonNumber1!!.setOnClickListener(this)
        buttonNumber2!!.setOnClickListener(this)
        buttonNumber3!!.setOnClickListener(this)
        buttonNumber4!!.setOnClickListener(this)
        buttonNumber5!!.setOnClickListener(this)
        buttonNumber6!!.setOnClickListener(this)
        buttonNumber7!!.setOnClickListener(this)
        buttonNumber8!!.setOnClickListener(this)
        buttonNumber9!!.setOnClickListener(this)
        buttonNumber0!!.setOnClickListener(this)
        buttonDivide!!.setOnClickListener(this)
        buttonPoint!!.setOnClickListener(this)
        buttonClear!!.setOnClickListener(this)
        buttonMinus!!.setOnClickListener(this)
        buttonPlus!!.setOnClickListener(this)
        buttonEqual!!.setOnClickListener(this)
        buttonMult!!.setOnClickListener(this)
        buttonSquare!!.setOnClickListener(this)
        buttonRoot!!.setOnClickListener(this)
        buttonUndo!!.setOnClickListener(this)





    }

    override fun onClick(v: View) {
        if (inputNumber!!.getText().toString() == "You cannot divide to 0" == true) {
            inputNumber!!.setText("")
        }
        if (inputNumber!!.getText().toString() == "NaN" == true) {
            inputNumber!!.setText("")
        }
        when (v.id) {
            R.id.number1 -> {
                if(inputNumber!!.getText().toString().equals("0") == false){
                    inputNumber!!.append("1")
                }
            }

            R.id.number2 -> {
                if(inputNumber!!.getText().toString().equals("0") == false){
                    inputNumber!!.append("2")
                }
            }

            R.id.number3 -> {
                if(inputNumber!!.getText().toString().equals("0") == false){
                    inputNumber!!.append("3")
                }
            }
            R.id.number4 -> {
                if(inputNumber!!.getText().toString().equals("0") == false){
                    inputNumber!!.append("4")
                }
            }
            R.id.number5 -> {
                if(inputNumber!!.getText().toString().equals("0") == false){
                    inputNumber!!.append("5")
                }
            }
            R.id.number6 -> {
                if(inputNumber!!.getText().toString().equals("0") == false){
                    inputNumber!!.append("6")
                }
            }
            R.id.number7 -> {
                if(inputNumber!!.getText().toString().equals("0") == false){
                    inputNumber!!.append("7")
                }
            }
            R.id.number8 -> {
                if(inputNumber!!.getText().toString().equals("0") == false){
                    inputNumber!!.append("8")
                }
            }
            R.id.number9 -> {
                if(inputNumber!!.getText().toString().equals("0") == false){
                    inputNumber!!.append("9")
                }
            }



            R.id.number0 -> {
                if(inputNumber!!.getText().toString().equals("") == true){
                    inputNumber!!.append("0")
                }
                else if(inputNumber!!.getText().toString()[0] == '-'){
                    return
                }
                else if(inputNumber!!.getText().toString().equals("0") == true){
                    return
                }
                else{
                    number1 = inputNumber!!.text.toString().toDouble()
                    if(number1.toInt() != 0 || (number1.toInt() == 0 && inputNumber!!.getText().toString()[1] =='.')){
                        inputNumber!!.append("0")
                    }
                }
            }

            R.id.clear -> {
                number1 = 0.0
                number2 = 0.0
                inputNumber!!.setText("")
            }
            R.id.undo -> {
                if(inputNumber!!.getText().toString().equals("") == false){
                    var str = inputNumber!!.getText().toString()
                    str = str.substring(0, str.length - 1)
                    inputNumber!!.setText(str)
                }
            }
             R.id.point -> {
                if(inputNumber!!.getText().toString().equals("") == true){
                    return
                }
                else if(inputNumber!!.getText().toString()[0] == '-' && inputNumber!!.getText().toString().length == 1){
                    return
                }
                if(inputNumber!!.getText().toString().equals("") == false ) {
                    if (inputNumber!!.getText().toString().contains(".") == false) {
                        inputNumber!!.append(".")
                    }
                }

            }
            R.id.plus -> {
                if(inputNumber!!.getText().toString().equals("") == true){
                    return
                }
                else if(inputNumber!!.getText().toString()[0] == '-' && inputNumber!!.getText().toString().length == 1){
                    return
                }
                else if(inputNumber!!.getText().toString().equals("") == false){
                    number1 = inputNumber!!.getText().toString().toDouble()
                    inputNumber!!.setText("")
                    op = "+"
                    isFirst = false
                }

            }
            R.id.minus ->{



                if(inputNumber!!.getText().toString().equals("") == false && inputNumber!!.getText().toString()[0] != '-'){
                    number1 = inputNumber!!.getText().toString().toDouble()
                    inputNumber!!.setText("");
                    op = "-";
                    isFirst = false
                }
                else if(inputNumber!!.getText().toString().equals("") == true){
                    inputNumber!!.setText("-");
                }

            }

            R.id.mult ->{
                if(inputNumber!!.getText().toString().equals("") == true){
                    return
                }
                else if(inputNumber!!.getText().toString()[0] == '-' && inputNumber!!.getText().toString().length == 1){
                    return
                }
                else if(inputNumber!!.getText().toString().equals("") == false){
                    number1 = inputNumber!!.getText().toString().toDouble()
                    inputNumber!!.setText("")
                    op = "x"
                    isFirst = false
                }

            }

               R.id.divide -> {
                if(inputNumber!!.getText().toString().equals("") == true){
                    return
                }
                else if(inputNumber!!.getText().toString()[0] == '-' && inputNumber!!.getText().toString().length == 1){
                    return
                }

                else if(inputNumber!!.getText().toString().equals("") == false){
                    number1 = inputNumber!!.getText().toString().toDouble()
                    inputNumber!!.setText("")
                    op = "/"
                    isFirst = false
                }


            }
            R.id.root -> {
                if(inputNumber!!.getText().toString().equals("") == true){
                    return
                }
                else if(inputNumber!!.getText().toString()[0] == '-' && inputNumber!!.getText().toString().length == 1){
                    return
                }
                else  if(inputNumber!!.getText().toString().equals("") == false){

                    if(isFirst == true){
                        number1 = inputNumber!!.getText().toString().toDouble()
                        number1 = Math.sqrt(number1)

                        var toCheck: String = number1.toString()
                        toCheck = toCheck[toCheck.length-2] + "" + toCheck[toCheck.length-1] + ""
                        if(toCheck == ".0") {
                            var intNumber1: Int = number1.toInt()
                            inputNumber!!.setText(intNumber1.toString())
                        }
                        else{
                            inputNumber!!.setText(number1.toString())
                        }
                    }
                    else{
                        number2 = inputNumber!!.getText().toString().toDouble()
                        number2 = Math.sqrt(number2)


                        var toCheck: String = number2.toString()
                        toCheck = toCheck[toCheck.length-2] + "" + toCheck[toCheck.length-1] + ""
                        if(toCheck == ".0") {
                            var intNumber2: Int = number2.toInt()
                            inputNumber!!.setText(intNumber2.toString())
                        }
                        else{
                            inputNumber!!.setText(number2.toString())
                        }
                    }

                }


            }

            R.id.square -> {
                if(inputNumber!!.getText().toString().equals("") == true){
                    return
                }
                else if(inputNumber!!.getText().toString()[0] == '-' && inputNumber!!.getText().toString().length == 1){
                    return
                }
                if(isFirst == true){
                    number1 = inputNumber!!.getText().toString().toDouble()
                    number1 = Math.pow(number1, 2.0)

                    var toCheck: String = number1.toString()
                    toCheck = toCheck[toCheck.length-2] + "" + toCheck[toCheck.length-1] + ""
                    if(toCheck == ".0") {
                        var intNumber1: Int = number1.toInt()
                        inputNumber!!.setText(intNumber1.toString())
                    }
                    else{
                        inputNumber!!.setText(number1.toString())
                    }
                }
                else {
                    number2 = inputNumber!!.getText().toString().toDouble()
                    number2 = Math.pow(number2, 2.0)


                    var toCheck: String = number2.toString()
                    toCheck = toCheck[toCheck.length-2] + "" + toCheck[toCheck.length-1] + ""
                    if(toCheck == ".0") {
                        var intNumber2: Int = number2.toInt()
                        inputNumber!!.setText(intNumber2.toString())
                    }
                    else{
                        inputNumber!!.setText(number2.toString())
                    }
                }
            }

             R.id.equal -> {
                if(inputNumber!!.getText().toString().equals("") == true){
                    return
                }
                else if(inputNumber!!.getText().toString()[0] == '-' && inputNumber!!.getText().toString().length == 1){
                    return
                }

                else if(inputNumber!!.getText().toString().equals("") == false){
                    var flag: Boolean? = false
                    isFirst == true
                    number2 = inputNumber!!.getText().toString().toDouble()
                    if(op.equals("+") == true){
                        result = number1 + number2;
                    }
                    if(op.equals("-") == true){
                        result = number1 - number2;
                    }
                    if(op.equals("x") == true){
                        result = number1  *  number2;
                    }
                    if(op.equals("/") == true){
                        if(number2 == 0.0){
                            inputNumber!!.setText("You cannot divide to 0");
                            flag = true
                        }
                        result = number1  / number2;
                    }

                    if(flag == false) {
                        var toCheck: String = result.toString()
                        toCheck = toCheck[toCheck.length-2] + "" + toCheck[toCheck.length-1] + ""
                        if(toCheck == ".0") {
                            var intNumber1: Int = result.toInt()
                            inputNumber!!.setText(intNumber1.toString())
                        }
                        else{
                            inputNumber!!.setText(result.toString())
                        }

                    }
                }

            }
        }
    }


}




