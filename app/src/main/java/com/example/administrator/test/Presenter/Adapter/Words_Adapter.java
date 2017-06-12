package com.example.administrator.test.Presenter.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.test.Model.Words;
import com.example.administrator.test.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class Words_Adapter extends RecyclerView.Adapter<Words_Adapter.Words_Holder> {

    List<Words> datas;

    public Words_Adapter(List<Words> datas){
        this.datas = datas;
    }

    @Override
    public Words_Adapter.Words_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_item_words, parent, false);
        return new Words_Holder(view);
    }

    @Override
    public void onBindViewHolder(Words_Adapter.Words_Holder holder, int position) {
        Words words = datas.get(position);
        holder.setNo(position+1);
        holder.setTitle(words.getTitle());
        holder.setDate(sdf(words.getDate()));
        holder.setSummary(words.getSummary());
        holder.setUUID(words.getUuid());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public String sdf(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    class Words_Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // 위젯
        TextView txt_item_no, txt_item_title, txt_item_date, txt_item_summary;
        View convertView;
        // 받아오는 값
        UUID resId;


        // 0. 생성자
        public Words_Holder(View convertView){
            super(convertView);
            this.convertView = convertView;
            convertView.setOnClickListener(this);
            findAddress();
        }

        // 1. findViewById -- 좀 귀찮지만 일단 함수로 빼냈다
        public void findAddress(){
            txt_item_no = (TextView) convertView.findViewById(R.id.txt_word_item_no);
            txt_item_title = (TextView) convertView.findViewById(R.id.txt_word_item_title);
            txt_item_date = (TextView) convertView.findViewById(R.id.txt_word_item_date);
            txt_item_summary = (TextView) convertView.findViewById(R.id.txt_word_item_summary);
        }

        // 2. 리스너
        public void onClick(View view){
            // + 버튼에서 호출하는 메소드 오버로딩, 값을 받을 수 있도록 한다.
            Toast.makeText(convertView.getContext(), "확인", Toast.LENGTH_SHORT).show();
        }

        // 3. set 함수
        public void setNo(int number){
            txt_item_no.setText(number+"");
        }

        public void setTitle(String title){
            txt_item_title.setText(title);
        }

        public void setDate(String date){
            txt_item_date.setText(date);
        }

        public void setSummary(String summary) {
            txt_item_summary.setText(summary);
        }

        public void setUUID(UUID uuid){
            resId = uuid;
        }

    }

}
