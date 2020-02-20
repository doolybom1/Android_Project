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

import java.util.List;

public class MemoViewAdapter extends RecyclerView.Adapter {

    // 삭제버튼에 사용할 이벤트 인터페이스를 하나 생성하고 내용이 없는 이벤트 메서드도 선언
    public interface OnDeleteButtonClickListener{
        void onDeleteButtonClick(MemoVO memoVO);
    }

    private Context context = null;
    private List<MemoVO> memoList = null;
    private LayoutInflater layoutInflater;

    // 삭제버튼 이벤트를 젖아할 객체 변수를 선언하고
    private OnDeleteButtonClickListener deleteBtnClick;

    // 삭제버튼 이벤트 본체를 외부로부터 주입받을수 있는 setter 주입
    public void setDeleteBtnClick(OnDeleteButtonClickListener event){
        this.deleteBtnClick = event;
    }

    // context 생성자
    public MemoViewAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    /*
        context, list 생성자
        MainActivity에서 MemoViewAdapter를 만들 때, Context와 memoList를 주입할 생성자
     */
    public MemoViewAdapter(Context context, List<MemoVO> memoList) {

        // 만약 context, list 생성자로 ViewAdapter를 생성하면
        // memoList만 여기에서 로컬객체에 등록을 하고
        // context 변수 값은 context 생성자로 토스하여
        // 그곳에서 layoutInflater를 초기화하도록 코드를 단일화한다.

        // 클래스 자신이 가지고 있는 또 다른 생성자를 호출하는 코드
        // 이 코드는 생성자 메서드에서 가장 먼저 등장해야 한다.
        this(context);
        this.memoList = memoList;

    }

    public void setMemoList(List<MemoVO> memoList) {
        // 외부에서 list를 주입받고
        // recyclerview에 세팅
        this.memoList = memoList;

        // recyclerview에게 알람
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        memo_item.xml파일을 가져와서 view객체로 생성(확장)하기
//        View view = LayoutInflater.from(context).inflate(R.layout.memo_item,parent,false);

        View view = layoutInflater.inflate(R.layout.memo_item, parent, false);

        MemoHolder holder = new MemoHolder(view);
        return holder;
    }

    /*
        memo_item.xml에 설정한 여러가지 view들을 사용할 수 있도록 초기화 하는 과정
     */
    class MemoHolder extends RecyclerView.ViewHolder{

        // VO와 이름이 같게
        public TextView item_time;
        public TextView item_date;
        public TextView item_text;
        public TextView item_btn_delete;

        public MemoHolder(@NonNull View itemView) {
            super(itemView);
            item_time = itemView.findViewById(R.id.item_time);
            item_date = itemView.findViewById(R.id.item_date);
            item_text = itemView.findViewById(R.id.item_text);
            item_btn_delete = itemView.findViewById(R.id.item_delete);
        }
    }

    /*
        memoList의 개수만큼 생성되어서 화면에 표시될 때, 반복문으로 호출되는 메서드
        반복문이 호출되면서 몇번째 데이터인가를 position 변수에 주입해준다.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        /*
            다형성(상위 클래스 가져와서 실제 사용할 때는 형변환해서 사용)
            RecyclerView.ViewHolder를 MemoHolder로 형변환하여
            MemoHolder에 직접 접근할 수 있도록 한다.
         */
        MemoHolder memoHolder = (MemoHolder)holder;

        /*
        memoList의 각 아이템 요소를 한개씩 읽어서
        TextView setText() method를 이용해서 문자열을 채워 넣어준다.
         */
        memoHolder.item_date.setText(memoList.get(position).getM_date());
        memoHolder.item_time.setText(memoList.get(position).getM_time());
        memoHolder.item_text.setText(memoList.get(position).getM_text());

        /*
        memoHolder.item_btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        */

//        memoHolder.item_btn_delete.setOnClickListener(
//                (view)-> {
//                    memoList.remove(position);
//                    notifyDataSetChanged();
//                }
//        );

        memoHolder.item_btn_delete.setOnClickListener(
                v -> deleteBtnClick.onDeleteButtonClick(memoList.get(position)));
    }

    @Override
    public int getItemCount() {
        // 3항연산자
        return memoList != null ? memoList.size() : 0;
    }



}
