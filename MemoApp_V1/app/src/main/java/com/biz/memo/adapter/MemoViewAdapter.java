package com.biz.memo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.biz.memo.R;
import com.biz.memo.domain.MemoVO;

import org.w3c.dom.Text;

import java.util.List;

public class MemoViewAdapter extends RecyclerView.Adapter {

    private Context context = null;
    private List<MemoVO> memoList = null;
    private LayoutInflater layoutInflater;


    // context 생성자
    public MemoViewAdapter(Context context){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    /*
       context, list 생성자
       MainActivity에서 MemoViewAdapter를 만들때 Context와 memoList를 주입할 생성자
     */
    public MemoViewAdapter(Context context, List<MemoVO> memoList) {

        // 만약 context, list 생성자로 ViewAdapter를 생성하면 memoList만 여기에서 로컬객체에 등록을 하고
        // context 변수 같은 context 생성자로 토스하여 그곳에서 layoutInflater를 초기화 하도록 코드를 단일화 한다
        // 클래스 자신이 가지고 있느 ㄴ또다른 생성자를 호출하는 코드
        // 이 코드는 생성자 메서드에서 가장먼저 등장해야 한다
        this(context);
        this.memoList = memoList;
    }


    public void setMemoList(List<MemoVO> memoList){
        // 외부에서 list를 주입받고 recycler세팅
        this.memoList = memoList;

        // recyclerview에 알람
        notifyDataSetChanged();
    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // memo_item.xml파일을 가져와서 view객체로 생성(확장)하기
//        View view = LayoutInflater.from(context).inflate(R.layout.memo_item, parent, false);
        View view = layoutInflater.inflate(R.layout.memo_item, parent, false);

        MemoHolder holder = new MemoHolder(view);
        return holder;

    }

    // memo_item.xml에 설정한 여러가지 view들을 사용할 수 있도록 초기화 하는 과정
    class MemoHolder extends RecyclerView.ViewHolder{

        public TextView m_time;
        public TextView m_date;
        public TextView m_text;

        public MemoHolder(@NonNull View itemView) {
            super(itemView);
            m_time = itemView.findViewById(R.id.m_time);
            m_date = itemView.findViewById(R.id.m_date);
            m_text = itemView.findViewById(R.id.m_text);
        }
    }

    /*
        memoList의 개수만큼 반복문으로 호출되는 메서드
        반복문이 호출되면서 몇번째 데이터인가를 position 변수에 주입해 준다
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        // RecyclerView.ViewHolder를 MemoHolder로 형변환하여 MemoHolder에 직접 접근할 수 있도록 한다
        MemoHolder memoHolder = (MemoHolder) holder;
        memoHolder.m_date.setText(memoList.get(position).getM_date());
        memoHolder.m_time.setText(memoList.get(position).getM_time());
        memoHolder.m_text.setText(memoList.get(position).getM_text());
    }

    @Override
    public int getItemCount() {
        return memoList != null ? memoList.size() : 0;
    }

}
