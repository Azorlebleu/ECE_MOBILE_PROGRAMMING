package com.example.myapplication;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Button;
import android.view.ViewGroup;
import android.os.AsyncTask;

import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    public String a = "";
    public String b = "";
    public int res;
    public char operator = Character.MIN_VALUE; //to emulate an empty character
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public class EqualButton extends AsyncTask<Void, Float, Float> {
        @Override
        public Float doInBackground(Void... voids) {
            //Do the last operation
            System.out.println("haha1");
            if (b != "") {
                int A = Integer.parseInt(a);
                int B = Integer.parseInt(b);
                switch (operator) {
                    case '*':
                        res = A * B;
                        break;
                    case '/':
                        res = A / B;
                        break;
                    case '+':
                        res = A + B;
                        break;
                    case '-':
                        res = A - B;
                        break;
                    default:
                        res = 0;
                }
                TextView result = (TextView) findViewById(R.id.result);
                result.setText(Integer.toString(res));

                a = "";
                b = "";
                operator = Character.MIN_VALUE;
            }
            System.out.println("haha");
            return((float)1); //useless but needed smh
        }
        @Override
        protected void onProgressUpdate(Float... floats) {
        }
        protected void onPostExecute(Float... floats) {

        }
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

                    System.out.println("Removing all...");
                    EqualButton equal = new EqualButton();
                    equal.execute();
                    ((ViewGroup)findViewById(R.id.layout_equal)).removeView(v);
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
}
