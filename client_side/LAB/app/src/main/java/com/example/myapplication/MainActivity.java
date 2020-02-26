package com.example.myapplication;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Button;
import android.view.ViewGroup;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;

import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    public String a = "";
    public String b = "";
    public String lastOperation="pas de modif";
    public String lastResult="pas de modif";
    public char operator = Character.MIN_VALUE; //to emulate an empty character
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void compute( String a, String b, char operator) {

        class Computer implements Runnable {
            String a;
            String b;
            char operator;

            Computer(String _a, String _b, char _operator) {
                a = _a;
                b = _b;
                operator = _operator;

            }

            public void run() {
                TextView result =  (TextView)findViewById(R.id.result);
                int res;
                Double A = Double.parseDouble(a);
                Double B = Double.parseDouble(b);

                try
                {
                    //connect to server
                    Socket sock = new Socket("10.0.2.2", 9876);
                    DataInputStream dis = new DataInputStream(sock.getInputStream());
                    DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
                    //send data to server
                    dos.writeDouble(A);
                    dos.writeChar(operator);
                    dos.writeDouble(B);

                    //retrieve data from server
                    Double RES = dis.readDouble();
                    result.setText(Double.toString(RES));

                    System.out.println("voici a:"+a);
                    lastOperation=a+operator+b;
                    System.out.println("voici last ope:"+lastOperation);
                    lastResult=Double.toString(RES);
                    //closing connection to avoid memory leak
                    dis.close();
                    dos.close();
                    sock.close();


                }catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }

        }
        Thread t = new Thread(new Computer(a, b, operator));
        t.start();
    }


    public void numbers(View v)
    {
        LinearLayout layout = (LinearLayout)findViewById(R.id.layout_equal);
        Button btn = new Button(this);
        char pressed = ((String)v.getTag()).charAt(0);
        System.out.println(pressed);
        char todo;
        if (    pressed >= '0' && pressed <= '9')
        {
            todo = 'n'; //number
        }

        else if (pressed == '*'||
                pressed == '/' ||
                pressed == '+' ||
                pressed == '-' )
        {
            todo = 'o'; //operator
        }
        else
        {
            todo = '='; //result
        }
        TextView result =  (TextView)findViewById(R.id.result);
        TextView operation =  (TextView) findViewById(R.id.operation);
        switch(todo){
            case 'n':
                if (this.operator == Character.MIN_VALUE)
                {
                    this.a += pressed;
                }
                else {
                    this.b += pressed;
                }
                break;

            case 'o':
                if(this.operator == Character.MIN_VALUE && !a.equals(""))
                {
                    this.operator = pressed;
                }
                break;


            case '=':
                if (!b.equals(""))
                {
                    //call server
                    compute(this.a, this.b, this.operator);



                    System.out.println("Removing all...");

                    ((ViewGroup)findViewById(R.id.layout_equal)).removeView(v); //tried to erase the button but it doesn't work
                    this.a = "";
                    this.b = "";
                    this.operator = Character.MIN_VALUE;

                }
                break;
        }
        String ope = this.a + this.operator + this.b;
        operation.setText(ope);

        if (!this.b.equals(""))
        {
            btn.setTag("=");
            btn.setText("=");
            btn.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.FILL_PARENT));
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    numbers(view);
                }
            });
            System.out.println("Button added!");
            layout.addView(btn);

        }
    }

    public void SendResult(View view) {
        Intent intent = new Intent(this, Display_Result.class);

        intent.putExtra("last_operation",lastOperation);
        intent.putExtra("last_result", lastResult);
        startActivity(intent);
    }

}