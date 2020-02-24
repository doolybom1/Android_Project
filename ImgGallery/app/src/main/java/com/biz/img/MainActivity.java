package com.biz.img;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.os.Bundle;

import com.biz.img.domain.Addresses;
import com.biz.img.utils.InputFieldName;
import com.biz.img.utils.RequestCode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            String[] strReq = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            };
            ActivityCompat.requestPermissions(MainActivity.this, strReq,1000);
        }

        imageView = findViewById(R.id.image_view);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 폰에 저장된 이미지 정보를 보기 위한 시스템 내장 Intent를 호출하기
                Intent imgIntent = new Intent(Intent.ACTION_PICK);

                // 지금부터 이미지 저장 폴더에 접근을 하겠다라는 설정
                imgIntent.setType(MediaStore.Images.Media.CONTENT_TYPE);

                // 이미지 저장하는 공간
                // 내장 메모리, 외장 SD 카드가 있는데 모두 나에게 접근할 수 있게 해달라
                imgIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                // 이미지 보기 열렸을때 이미지를 클릭하면 해당 이미지 정보를 return 받기 위해서 Intent 열기
                startActivityForResult(imgIntent, RequestCode.REQUEST_IMAGE_CODE);
            }
        });
    } // end onCreat()

    // 현재 Activity에서 새로운 Activity를 열었을 때 새로운 Activity가 어떤 값을 되돌려 주면 그 값을 수신할 method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        // 이미지 갤러리가 보내는 데이터를 받아서 ImageView에 이미지 표시
        if(requestCode == RequestCode.REQUEST_IMAGE_CODE) {
            try {
                InputStream is = getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(is);
                is.close();
                imageView.setImageBitmap(img);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }else if(resultCode == RequestCode.RESULT_OK){
                String strName = data.getStringExtra(InputFieldName.TXT_NAME);
                String strTel = data.getStringExtra(InputFieldName.TXT_TEL);
                String strAddr = data.getStringExtra(InputFieldName.TXT_ADDR);

                Addresses addr = (Addresses) data.getSerializableExtra("ADDR");

                strName = addr.getA_name();
                strTel = addr.getA_tel();
                strAddr = addr.getA_addr();

                String msg = String.format("이름 : %s\n 전화번호 : %s\n 주소 :%s\n",strName,strTel,strAddr);

                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
            }else if(resultCode == RequestCode.RESULT_ERROR){
                Toast.makeText(MainActivity.this, "입력 오류 발생!!",Toast.LENGTH_SHORT).show();
            }


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
}
