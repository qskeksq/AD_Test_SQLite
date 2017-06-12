package com.example.administrator.test.Presenter.Fragment;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.test.Model.Words;
import com.example.administrator.test.Model.Words_Lab;
import com.example.administrator.test.R;
import com.example.administrator.test.Schema;
import com.example.administrator.test.View.Words_View;


public class Words_Fragment extends Fragment {

    Words_View words_view;
    View view;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Words words;
    // TODO date 설정 해줘야 함
    // TODO 요약 좀 실용적이게 만들던가, 두 줄로 만들던가 해라

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_words, container, false);

        words_view = new Words_View(view, this);
        init();

        return view;
    }

    public void init(){
        sp = getActivity().getSharedPreferences("words_key", Context.MODE_PRIVATE);
        words = new Words();

    }

    // 옵션 인플레이션
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_words, menu);
    }

    // 옵션 관리
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.words_save:
                askSave();
                break;
            case R.id.words_temp_save:
                temp_save();
                Toast.makeText(getContext(), "임시로 저장되었습니다", Toast.LENGTH_SHORT).show();
                break;
            case R.id.words_delete:
                askDelete();
                break;
            case R.id.words_copy:
                copy();
                break;
        }
        return true;
    }

    // 저장 여부 대화상자
    public void askSave(){
        AlertDialog.Builder askSave = new AlertDialog.Builder(getContext());
        askSave.setTitle("저장하시겠습니까?");
        askSave.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                save();
                Toast.makeText(getContext(), "저장되었습니다", Toast.LENGTH_SHORT).show();
            }
        });
        askSave.setNegativeButton("아니오", null);
        askSave.show();
    }

    // 저장
    public void save(){

        words.setTitle(words_view.txt_words_title.getText().toString());
        words.setBook(words_view.txt_words_book.getText().toString());
        words.setWords(words_view.txt_words_text.getText().toString());
        words.setSummary(words_view.txt_words_summary.getText().toString());

        Words_Lab.getWordsLAB(getContext()).addWords(words);

        // 이렇게 해 주는 이유는 임시 저장소를 초기화 하기 위해서이다.
        words_view.txt_words_title.setText("");
        words_view.txt_words_book.setText("");
        words_view.txt_words_text.setText("");
        words_view.txt_words_summary.setText("");

        // 또한 새로운 프래그먼트를 띄우주는 이유는 새 QT 객체를 만들어 주는 것, 날짜 갱신
        // TODO 원래는 인터페이스를 통한 메소드 통신을 해 줘야 함
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Words_Fragment()).commit();
    }

    // 임시 저장
    public void temp_save(){
        editor = sp.edit();
        editor.putString(Schema.Words_Fragment_S.KEY_TITLE, words_view.txt_words_title.getText().toString());
        editor.putString(Schema.Words_Fragment_S.KEY_BOOK, words_view.txt_words_book.getText().toString());
        editor.putString(Schema.Words_Fragment_S.KEY_TEXT, words_view.txt_words_text.getText().toString());
        editor.putString(Schema.Words_Fragment_S.KEY_SUMMARY, words_view.txt_words_summary.getText().toString());
        editor.commit();
    }

    // 삭제 여부 묻기
    public void askDelete(){
        AlertDialog.Builder askSave = new AlertDialog.Builder(getContext());
        askSave.setTitle("삭제하시겠습니까?");
        askSave.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete();
                Toast.makeText(getContext(), "삭제되었습니다", Toast.LENGTH_SHORT).show();
            }
        });
        askSave.setNegativeButton("아니오", null);
        askSave.show();
    }

    // 삭제
    public void delete(){
        // 어차피 저장된 것이 없기 때문에 임시 저장된 것만 지워주면 된다.
        words_view.txt_words_title.setText("");
        words_view.txt_words_book.setText("");
        words_view.txt_words_text.setText("");
        words_view.txt_words_summary.setText("");
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Words_Fragment()).commit();
    }

    // 공유 TODO 카카오톡이나 메일로 보낼 수 있도록 암시적 인텐트 만든다.
    public void copy(){
        String clipped =
                "- Title" + "\n" + words_view.txt_words_title.getText().toString() + "\n"
                + "- Book" + "\n" + words_view.txt_words_book.getText().toString() + "\n"
                + "- Words" + "\n" + words_view.txt_words_text.getText().toString() + "\n"
                + "- Summary" + "\n" + words_view.txt_words_summary.getText().toString();
        if(!clipped.equals("")) {
            ClipData clip = ClipData.newPlainText(Schema.Words_Fragment_S.KEY_COPY, clipped);
            ClipboardManager manager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            manager.setPrimaryClip(clip);
            Toast.makeText(getContext(), "복사되었습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "클립보드가 비었습니다.", Toast.LENGTH_SHORT).show();
        }


    }

    // 로딩되기
    public void load(){

        String title = sp.getString(Schema.Words_Fragment_S.KEY_TITLE, "");
        String book = sp.getString(Schema.Words_Fragment_S.KEY_BOOK, "");
        String text = sp.getString(Schema.Words_Fragment_S.KEY_TEXT, "");
        String summary = sp.getString(Schema.Words_Fragment_S.KEY_SUMMARY, "");

        words_view.txt_words_title.setText(title);
        words_view.txt_words_book.setText(book);
        words_view.txt_words_text.setText(text);
        words_view.txt_words_summary.setText(summary);

    }

    // 프래그먼트가 멈출 때 자동으로 임시 저장된다.
    public void onPause(){
        super.onPause();
        temp_save();
    }

    // 프래그먼트가 resume 될 때 자동으로 로딩된다.
    public void onResume(){
        super.onResume();
        load();
    }

}
