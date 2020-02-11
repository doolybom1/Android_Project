package com.biz.memo;

import android.os.Bundle;

import com.biz.memo.adapter.MemoViewAdapter;
import com.biz.memo.domain.MemoVO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    List<MemoVO> memoList = null;
    TextInputEditText m_input_memo = null;
    RecyclerView memo_list_view = null;
    RecyclerView.Adapter view_adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        memoList = new ArrayList<MemoVO>();
        Button btn_save = findViewById(R.id.memo_save);
        btn_save.setOnClickListener(this);

        m_input_memo = findViewById(R.id.m_input_text);

//        for (int i = 0; i<30 ; i++){
//            MemoVO memoVO = new MemoVO();
//            memoVO.setM_date(cd.format(date));
//            memoVO.setM_time(st.format(date));
//            memoVO.setM_text((i+1) + " 번째 메모");
//            memoList.add(memoVO);
//        }

        memo_list_view = findViewById(R.id.memo_list_view);
        view_adapter = new MemoViewAdapter(MainActivity.this, memoList);
        memo_list_view.setAdapter(view_adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        memo_list_view.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(memo_list_view.getContext(),LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(this.getResources().getDrawable(R.drawable.decoration_line, getApplication().getTheme()));

        memo_list_view.addItemDecoration(itemDecoration);


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
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat st = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());

        String m_memo_text = m_input_memo.getText().toString();
        if(m_memo_text.isEmpty()){
            Toast.makeText(MainActivity.this,"메모를 입력하세요",Toast.LENGTH_SHORT).show();
            m_input_memo.setFocusable(true);
            return;
        }

        MemoVO memoVO = MemoVO.builder()
                .m_date(sd.format(date))
                .m_time(st.format(date))
                .m_text(m_memo_text).build();

        memoList.add(memoVO);

        // RecyclerView의 Adapter한테 데이터가 변경되었으니 리스트를 다시 그려라는 통보
        view_adapter.notifyDataSetChanged();
        m_input_memo.setText("");


    }
}
