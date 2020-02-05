package com.biz.myapp;

import android.os.Bundle;

import com.biz.myapp.utils.MyViewClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

/*
안드로이드 이벤트핸들러 2번째로 많이 사용하는 바업ㅂ
Activity 자체를 이벤트 핸들러로 선언하기
View.On** 클래스를 implement 해주고 On**() method를 구현해주는 방식

사용할때는 view.setOn**(this) 키워드 방식으로 호출
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView txt11;
    TextView txt22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        txt11 = findViewById(R.id.txt1);
//        txt22 = findViewById(R.id.txt2);
//
//        TextView txt1 = findViewById(R.id.txt1);
//        TextView txt2 = findViewById(R.id.txt2);
//        Button btn1 = findViewById(R.id.btn1);
//
//        txt1.setOnClickListener(this);
//        txt2.setOnClickListener(this);
//        btn1.setOnClickListener(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.txt2){
            txt11.setText(txt22.getText());
        }else{
            txt11.setText("오늘은 수요일");
        }
    }
}
