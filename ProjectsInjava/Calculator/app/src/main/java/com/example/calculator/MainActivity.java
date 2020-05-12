package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputNumber;



    private Button buttonNumber1;
    private Button buttonNumber2;
    private Button buttonNumber3;
    private Button buttonNumber4;
    private Button buttonNumber5;

    private Button buttonNumber6;
    private Button buttonNumber7;
    private Button buttonNumber8;
    private Button buttonNumber9;
    private Button buttonNumber0;

    private Button buttonPlus;
    private Button buttonMinus;
    private Button buttonDivide;
    private Button buttonClear;
    private Button buttonPoint;
    private Button buttonEqual;
    private Button buttonMult;
    private Button buttonSquare;
    private Button buttonRoot;
    //private Button buttonPercent;
    private Button buttonUndo;

    private double number1;
    private double number2;
    private double result;

    private String op;

//    enum Sign{
//        PLUS, MINUS, MUL, DIV
//    }
//    private Sign sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputNumber = findViewById(R.id.textNumber);
        inputNumber.setKeyListener(null);
        //inputNumber.append("0");

        buttonNumber1 = findViewById(R.id.number1);
        buttonNumber2 = findViewById(R.id.number2);
        buttonNumber3 = findViewById(R.id.number3);
        buttonNumber4 = findViewById(R.id.number4);
        buttonNumber5 = findViewById(R.id.number5);
        buttonNumber6 = findViewById(R.id.number6);
        buttonNumber7 = findViewById(R.id.number7);
        buttonNumber8 = findViewById(R.id.number8);
        buttonNumber9 = findViewById(R.id.number9);
        buttonNumber0 = findViewById(R.id.number0);

        buttonPlus = findViewById(R.id.plus);
        buttonMinus = findViewById(R.id.minus);
        buttonPoint = findViewById(R.id.point);
        buttonDivide = findViewById(R.id.divide);

        buttonSquare = findViewById(R.id.square);
        //buttonPercent = findViewById(R.id.percent);
        buttonMult = findViewById(R.id.mult);
        buttonRoot = findViewById(R.id.root);

        buttonEqual = findViewById(R.id.equal);
        buttonUndo = findViewById(R.id.undo);

        buttonClear = findViewById(R.id.clear);

        buttonNumber1.setOnClickListener(this);
        buttonNumber2.setOnClickListener(this);
        buttonNumber3.setOnClickListener(this);
        buttonNumber4.setOnClickListener(this);
        buttonNumber5.setOnClickListener(this);
        buttonNumber6.setOnClickListener(this);
        buttonNumber7.setOnClickListener(this);
        buttonNumber8.setOnClickListener(this);
        buttonNumber9.setOnClickListener(this);
        buttonNumber0.setOnClickListener(this);
        buttonDivide.setOnClickListener(this);
        buttonPoint.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
        buttonMinus.setOnClickListener(this);
        buttonPlus.setOnClickListener(this);
        buttonEqual.setOnClickListener(this);
        //buttonPercent.setOnClickListener(this);
        buttonMult.setOnClickListener(this);
        buttonSquare.setOnClickListener(this);
        buttonRoot.setOnClickListener(this);
        buttonUndo.setOnClickListener(this);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        inputNumber.setText(savedInstanceState.getString("inputNumber"));
        number1 = savedInstanceState.getDouble("number1");
        number2=savedInstanceState.getDouble("number2");
        result=savedInstanceState.getDouble("result");
        op = savedInstanceState.getString("op");

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("inputNumber",inputNumber.getText().toString());
        outState.putDouble("number1",number1);
        outState.putDouble("number2",number2);
        outState.putDouble("result",result);
        outState.putString("op",op);

    }



    @Override
    public void onClick(View v) {
        if(inputNumber.getText().toString().equals("You cannot divide to 0") == true){
            inputNumber.setText("");
        }
        if(inputNumber.getText().toString().equals("NaN") == true){
            inputNumber.setText("");
        }
        switch (v.getId()){
            case R.id.number1:{
                if(inputNumber.getText().toString().equals("0") == true){
                    break;
                }

                inputNumber.append("1");
                break;
            }
            case R.id.number2:{
                if(inputNumber.getText().toString().equals("0") == true){
                    break;
                }
                inputNumber.append("2");
                break;
            }
            case R.id.number3:{
                if(inputNumber.getText().toString().equals("0") == true){
                    break;
                }
                inputNumber.append("3");
                break;
            }
            case R.id.number4:{
                if(inputNumber.getText().toString().equals("0") == true){
                    break;
                }
                inputNumber.append("4");
                break;
            }
            case R.id.number5:{
                if(inputNumber.getText().toString().equals("0") == true){
                    break;
                }
                inputNumber.append("5");
                break;
            }
            case R.id.number6:{
                if(inputNumber.getText().toString().equals("0") == true){
                    break;
                }
                inputNumber.append("6");
                break;
            }
            case R.id.number7:{
                if(inputNumber.getText().toString().equals("0") == true){
                    break;
                }
                inputNumber.append("7");
                break;
            }
            case R.id.number8:{
                if(inputNumber.getText().toString().equals("0") == true){
                    break;
                }
                inputNumber.append("8");
                break;
            }
            case R.id.number9:{
                if(inputNumber.getText().toString().equals("0") == true){
                    break;
                }
                inputNumber.append("9");
                break;

            }
            case R.id.number0:{
                if(inputNumber.getText().toString().equals("") == true){
                    inputNumber.append("0");
                }
                else if(inputNumber.getText().toString().charAt(0) == '-'){
                    break;
                }
                else if(inputNumber.getText().toString().equals("0") == true){
                    break;
                }
                else{
                    number1 = Double.parseDouble(inputNumber.getText().toString());
                    if(number1 != 0 || (number1==0 && inputNumber.getText().toString().charAt(1)=='.')){
                        inputNumber.append("0");
                    }
                }
                break;
            }

            case R.id.clear:{
                number1 = 0;
                number2 = 0;
                inputNumber.setText("");
                break;
            }
            case R.id.undo:{
                if(inputNumber.getText().toString().equals("") == false){
                    String str = inputNumber.getText().toString();
                    str = str.substring(0, str.length() - 1);
                    inputNumber.setText(str);
                }
                break;
            }
            case R.id.point:{
                if(inputNumber.getText().toString().equals("") == true){
                    break;
                }
                else if(inputNumber.getText().toString().charAt(0) == '-' && inputNumber.getText().toString().length() == 1){
                    break;
                }
                if(inputNumber.getText().toString().equals("") == false ) {
                    if (inputNumber.getText().toString().contains(".") == false) {
                        inputNumber.append(".");
                    }
                }


                break;
            }
            case R.id.plus:{
                if(inputNumber.getText().toString().equals("") == true){
                    break;
                }
                else if(inputNumber.getText().toString().charAt(0) == '-' && inputNumber.getText().toString().length() == 1){
                    break;
                }
                else if(inputNumber.getText().toString().equals("") == false){
                    number1 = Double.parseDouble(inputNumber.getText().toString());
                    inputNumber.setText("");
                   op = "+";
                }

                break;
            }
            case R.id.minus:{



                if(inputNumber.getText().toString().equals("") == false && inputNumber.getText().toString().charAt(0) != '-'){
                    number1 = Double.parseDouble(inputNumber.getText().toString());
                    inputNumber.setText("");
                    op = "-";
                }
                else if(inputNumber.getText().toString().equals("") == true){
                    inputNumber.setText("-");
                }

                break;
            }

            case R.id.mult:{
                if(inputNumber.getText().toString().equals("") == true){
                    break;
                }
                else if(inputNumber.getText().toString().charAt(0) == '-' && inputNumber.getText().toString().length() == 1){
                    break;
                }
                else if(inputNumber.getText().toString().equals("") == false){
                    number1 = Double.parseDouble(inputNumber.getText().toString());
                    inputNumber.setText("");
                    op = "x";
                }

                break;
            }

            case R.id.divide:{
                if(inputNumber.getText().toString().equals("") == true){
                    break;
                }
                else if(inputNumber.getText().toString().charAt(0) == '-' && inputNumber.getText().toString().length() == 1){
                    break;
                }
                else if(inputNumber.getText().toString().equals("") == false){
                    number1 = Double.parseDouble(inputNumber.getText().toString());
                    inputNumber.setText("");
                    op = "/";
                }

                break;
            }
//            case R.id.percent:{
//                if(inputNumber.getText().toString().equals("") == true){
//                    break;
//                }
//                else if(inputNumber.getText().toString().charAt(0) == '-' && inputNumber.getText().toString().length() == 1){
//                    break;
//                }
//
//                else if(inputNumber.getText().toString().equals("") == false){
//                    number1 = Double.parseDouble(inputNumber.getText().toString());
//                    double my_res = Double.valueOf(number1);
//                    my_res = my_res / 100;
//                    inputNumber.setText(Double.toString(my_res));
//                }
//
//
//                break;
//            }

            case R.id.root:{
                if(inputNumber.getText().toString().equals("") == true){
                    break;
                }
                else if(inputNumber.getText().toString().charAt(0) == '-' && inputNumber.getText().toString().length() == 1){
                    break;
                }
               else  if(inputNumber.getText().toString().equals("") == false){
                    number1 = Double.parseDouble(inputNumber.getText().toString());
                    number1 = Math.sqrt(number1);
                    if(number1  - (int)number1 == 0){
                        inputNumber.setText(String.valueOf((int)number1));
                    }
                    else {
                        inputNumber.setText(String.valueOf(number1));
                    }
                }


                break;
            }

            case R.id.square:{
                if(inputNumber.getText().toString().equals("") == true){
                    break;
                }
                else if(inputNumber.getText().toString().charAt(0) == '-' && inputNumber.getText().toString().length() == 1){
                    break;
                }

                else if(inputNumber.getText().toString().equals("") == false){
                    number1 = Double.parseDouble(inputNumber.getText().toString());
                    number1 = Math.pow(number1, 2);
                    if(number1  - (int)number1 == 0){
                        inputNumber.setText(String.valueOf((int)number1));
                    }
                    else {
                        inputNumber.setText(String.valueOf(number1));
                    }
                }
                break;
            }

            case R.id.equal:{
                if(inputNumber.getText().toString().equals("") == true){
                    break;
                }
                else if(inputNumber.getText().toString().charAt(0) == '-' && inputNumber.getText().toString().length() == 1){
                    break;
                }

                else if(inputNumber.getText().toString().equals("") == false){
                    boolean flag = false;
                    number2 = Double.parseDouble(inputNumber.getText().toString());
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
                        if(number2 == 0){
                            inputNumber.setText("You cannot divide to 0");
                            flag = true;
                        }
                        result = number1  / number2;
                    }

                    if(flag == false) {
                        if (result - (int) result == 0) {
                            inputNumber.setText(String.valueOf((int) result));
                        } else {
                            inputNumber.setText(String.valueOf(result));
                        }
                    }
                }

                break;
            }
        }
    }
}
