package com.example.administrator.test.Presenter.Fragment;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.test.Model.QT;
import com.example.administrator.test.Model.QT_Lab;
import com.example.administrator.test.R;
import com.example.administrator.test.View.QT_View;

import java.text.SimpleDateFormat;
import java.util.Date;


public class QT_Fragment extends Fragment{

    private final static String TEMP_SAVE_KEY = "temp_save";
    private final static String WEEK = "week";
    private final static String QT = "QT";
    private final static String THANKS = "thanks";
    private final static String PRAYER = "prayer";
    private final static String JOURNAL = "journal";
    SharedPreferences sp;
    QT_View qt_view;
    View view;
    public QT qt;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_qt, container, false);

        init();

        return view;
    }

    public void init(){
        qt = new QT();
        qt_view = new QT_View(view, this);
        setHasOptionsMenu(true);  // 으아아아아아아아아아아아아ㅏ아아아아아아아아아아아악 이거 한 줄 때문에 10 시간 날림
        sp = getActivity().getSharedPreferences(TEMP_SAVE_KEY, Context.MODE_PRIVATE);
        loadTempSaved();
        out();
    }



//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_qt, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.save:
                qt_view.askSave();
                break;
            case R.id.temp_save:
                temp_save();
                qt_view.changeEditableIcon(item);
                break;
            case R.id.copy:
                qt_view.setClipBoardEnabled();
                break;
            case R.id.delete:
                qt_view.askDelete();
                break;
        }
        return true;
    }

    // read
    boolean status = false;
    public void temp_save(){
        if(status){
            qt_view.setEditable();
            status = false;
            Toast.makeText(getContext(), "쓰기", Toast.LENGTH_SHORT).show();
        } else {
            setTempSave();
            qt_view.setEditDisabled();
            status = true;
            Toast.makeText(getContext(), "임시 저장", Toast.LENGTH_SHORT).show();
        }
    }

    // 클립보드 -- copy
    public void copy(){
        String clipped = "";    // TODO 왜 public 으로 해 줘야 하는거냐
        if(qt_view.check_week.isChecked()){
            clipped = clipped + "- 이번 주 말씀 \n" + qt_view.txt_detail_week.getText().toString() + "\n";
        }
        if(qt_view.check_qt.isChecked()){
            clipped = clipped + "- 오늘 말씀 \n" + qt_view.txt_detail_qt.getText().toString() +"\n";
        }
        if(qt_view.check_thanks.isChecked()){
            clipped = clipped + "- 감사 \n" + qt_view.txt_detail_thanks.getText().toString() + "\n";
        }
        if(qt_view.check_prayer.isChecked()){
            clipped = clipped + "- 기도 \n" + qt_view.txt_detail_prayer.getText().toString() + "\n";
        }
        if(qt_view.check_journal.isChecked()){
            clipped = clipped + "- 일기 \n" + qt_view.txt_detail_journal.getText().toString();
        }

        if(!clipped.equals("")){
            ClipData clip = ClipData.newPlainText("text", clipped);
            ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setPrimaryClip(clip);
            Toast.makeText(getContext(), "복사되었습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "클립보드가 비었습니다", Toast.LENGTH_SHORT).show();
        }

    }

    // 저장 -- save
    public void save(){
        Toast.makeText(getContext(), "확인", Toast.LENGTH_SHORT).show();
        QT_Lab.getQTLAB(getContext()).addQT(qt);
        removeTempSaved();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new QT_Fragment()).commit();
    }

    // 삭제
    public void delete(){
        removeTempSaved();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new QT_Fragment()).commit();
    }

    // 임시 저장소 관리 -- 임시로 저장하기
    public void setTempSave(){  // TODO 1. 저장하지 않고 넘어갈 때  2. 임시저장 버튼을 누를 때 에 설정해야 한다.
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(WEEK, qt.getWeek());
        editor.putString(QT, qt.getQt());
        editor.putString(THANKS, qt.getThanks());
        editor.putString(PRAYER, qt.getPrayer());
        editor.putString(JOURNAL, qt.getJournal());
        editor.commit();

    }

    // 임시 저장소 관리 -- 불러오기
    public void loadTempSaved(){
        String week = sp.getString(WEEK, "");
        String temp_qt = sp.getString(QT, "");
        String thanks = sp.getString(THANKS, "");
        String prayer = sp.getString(PRAYER, "");
        String journal = sp.getString(JOURNAL, "");

        qt.setWeek(week);
        qt.setQt(temp_qt);
        qt.setThanks(thanks);
        qt.setPrayer(prayer);
        qt.setJournal(journal);

    }

    // 임시 저장소 관리 -- 삭제
    public void removeTempSaved(){
        // 사실 삭제는 아니고 임시 저장소의 값을 0으로 바꿔주는 것이다. 이게 딜레마인데, edittext 값이 변경되면 바로 qt 객체에
        // 저장되도록 설계를 해 놔서 remove 해도 다시 값이 설정이 되버린다.
        qt_view.txt_detail_week.setText("");
        qt_view.txt_detail_qt.setText("");
        qt_view.txt_detail_thanks.setText("");
        qt_view.txt_detail_prayer.setText("");
        qt_view.txt_detail_journal.setText("");
    }

    // 임시 저장소 관리 -- 자동 저장
    public void onPause(){
        super.onPause();
        setTempSave();
    }

    // 화면에 보여질 데이터 뷰에 뿌려주기
    public void out(){
        qt_view.setWeekView(qt.getWeek());
        qt_view.setQTView(qt.getQt());
        qt_view.setThanksView(qt.getThanks());
        qt_view.setPrayerView(qt.getPrayer());
        qt_view.setJournalView(qt.getJournal());
        qt_view.setDate(sdf(qt.getDate()));
    }

    public String sdf(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String result = sdf.format(date);
        return result;
    }




}
