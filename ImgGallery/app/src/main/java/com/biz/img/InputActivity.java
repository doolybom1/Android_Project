package com.biz.img;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.biz.img.domain.Addresses;
import com.biz.img.utils.InputFieldName;
import com.biz.img.utils.RequestCode;
import com.google.android.material.textfield.TextInputEditText;

public class InputActivity extends AppCompatActivity {

    TextInputEditText txt_name;
    TextInputEditText txt_tel;
    TextInputEditText txt_addr;

    Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        txt_name = findViewById(R.id.txt_name);
        txt_tel = findViewById(R.id.txt_tel);
        txt_addr = findViewById(R.id.txt_addr);

        btn_save = findViewById(R.id.btn_save);

        // btn_save를 클릭하면 현재화면을 닫고 MainActivity로 txt_name ,tel ,addr을 전달하기
        /*
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        */

        btn_save.setOnClickListener((view)->{

            String strName = txt_name.getText().toString();
            String strTel = txt_tel.getText().toString();
            String strAddr = txt_addr.getText().toString();

            Addresses addr = Addresses.builder()
                    .a_name(strName)
                    .a_tel(strTel)
                    .a_addr(strAddr)
                    .build();


            // 나를 호출한 Activity의 context 가져와서 값을 return하기 위한 준비
            Intent myIntent = getIntent();

            // model에 "변수", 값 형식응로 Attribute를 설정한 것 같은 원리로 되돌리는 변수(객체, 클래스) 등을 설정
            myIntent.putExtra(InputFieldName.TXT_NAME, strName);
            myIntent.putExtra(InputFieldName.TXT_TEL, strTel);
            myIntent.putExtra(InputFieldName.TXT_ADDR, strAddr);

            myIntent.putExtra("ADDR", addr);

            // 현재 Activity를 닫고 위에 putExtra로 설정한 값들을 MainActivity로 되돌려 보내라
//            startActivityForResult(myIntent, RequestCode.REQUEST_INPUT_CODE);
            setResult(RequestCode.RESULT_OK,myIntent);
            finish();
        });
    }
}
