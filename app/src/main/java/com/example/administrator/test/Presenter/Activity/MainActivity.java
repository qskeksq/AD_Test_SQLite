package com.example.administrator.test.Presenter.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.test.Presenter.DrawerInterface;
import com.example.administrator.test.Presenter.Fragment.Calendar_Fragment;
import com.example.administrator.test.Presenter.Fragment.Decision_Fragment;
import com.example.administrator.test.Presenter.Fragment.ListFragment.List_Fragment;
import com.example.administrator.test.Presenter.Fragment.QT_Fragment;
import com.example.administrator.test.Presenter.Fragment.Words_Fragment;
import com.example.administrator.test.R;
import com.example.administrator.test.View.Main_View;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DrawerInterface, View.OnClickListener {

    Fragment fragment;
    Main_View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_main);

        view = new Main_View(this);
        getSupportFragmentManager().beginTransaction().addToBackStack("back").replace(R.id.fragment_container, new QT_Fragment()).commit();

    }

    // back 키 메소드 TODO 스레드를 이용해서 back 키 설정하기
    boolean back_status = true;
    @Override
    public void onBackPressed() {
        if(back_status) {
            if (view.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                view.drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                back_status = false;
                Toast.makeText(this, "한번 더 누르면 앱이 종료됩니다", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        back_status = true;
                    }
                }, 5000);
            }
        } else {
            finish();
        }
    }


    // 네이게이션 메뉴 선택 메소드
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // 선택할 때마다 새로운 프래그먼트를 띄워준다.
        // TODO 리소스 관리 차원에서 미리 생성된 것으로 교체할 것인지 고민해보자!!
        int id = item.getItemId();

        switch (id){
            case R.id.menu_add_qt:
                fragment = new QT_Fragment();
                getSupportFragmentManager().beginTransaction().addToBackStack("back").replace(R.id.fragment_container, fragment).commit();
                break;
            case R.id.menu_add_words:
                fragment = new Words_Fragment();
                getSupportFragmentManager().beginTransaction().addToBackStack("back").replace(R.id.fragment_container, fragment).commit();
                break;
            case R.id.menu_add_decision:
                fragment = new Decision_Fragment();
                getSupportFragmentManager().beginTransaction().addToBackStack("back").replace(R.id.fragment_container, fragment).commit();
                break;
            case R.id.menu_list:
                fragment = new List_Fragment();
                getSupportFragmentManager().beginTransaction().addToBackStack("back").replace(R.id.fragment_container, fragment).commit();
                break;
            case R.id.menu_calendar:
                fragment = new Calendar_Fragment();
                getSupportFragmentManager().beginTransaction().addToBackStack("back").replace(R.id.fragment_container, fragment).commit();
                break;
            case R.id.menu_settings:
                getSupportFragmentManager().beginTransaction().addToBackStack("back").remove(fragment).commit();
//                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
//                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, new Settings_Fragment()).commit();
//                TODO 스택 처리 안 해주면 계속 쌓인다
//                int index = getSupportFragmentManager().getBackStackEntryCount() -1 ;
//                Log.e("Main", index+"");
//                String tag = getSupportFragmentManager().getBackStackEntryAt(index).getName();
//                Log.e("Main", tag);
//                fragment = getSupportFragmentManager().findFragmentByTag(tag);
//                Log.e("Main", "확인1");
//                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
//                Log.e("Main", "확인2");

//                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, new Settings_Fragment()).commit();
        }
        view.drawerLayout.closeDrawer(GravityCompat.START); // 선택된 후에는 drawer 을 닫는다.
        return true;
    }

    //----------------------------------------------------------------------------------------------
    // TODO 엉망이라는 것을 알 수 있다!!

    // 헤더 이미지, 헤더 메뉴 관리
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_navigation_settings:
                manageHeaderPhoto(v);
                break;
            case R.id.imageView:
                headerPhotoClicked();
        }
    }

    // 헤더 클릭 관리 메소드
    boolean status = true;
    public void manageHeaderPhoto(View v){
        if(status == true) {
            managePopup(v);
            status = false;
        } else {
            // TODO View 영역이지만 복잡해서 놔둠
            view.img_navi_profile.setEnabled(false);
            view.btn_navi_settings.setImageResource(R.drawable.ic_action_settings_for_navigation);
            status = true;
        }
    }

    // 헤더 팝업메뉴 관리 메소드
    public void managePopup(View v){  // TODO 이거 설계를 잘못한 것인가 왜이렇게 얽혀있지
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.popup_edit:
                        view.img_navi_profile.setEnabled(true);
                        view.btn_navi_settings.setImageResource(R.drawable.ic_action_save3);
                }
                return true;
            }
        });
        popupMenu.show();
    }

    // 헤더 프로필 사진 클릭 메소드
    public void headerPhotoClicked(){
        // TODO 정리가 전혀 안된 느낌
        // 일단 한 가지 뺴먹은 것은 manifest 에서 처리를 안 해줬다는 것!
        View view = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null);
        cameraDialog(view);

        // 사진을 눌렀을 때 권한 처리를 해 주자
        ImageView photo = (ImageView) view.findViewById(R.id.img_nav_photo); // TODO View 로 빼줘야 하지만 너무 복잡해서 놔둠
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    customCheckPermission();
                } else  {
                    run();
                }
            }
        });
    }

    // 카메라, 사진 선택 커스텀 다이얼로그
    public void cameraDialog(View view){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setView(view);
        dialog.show();
    }
    //----------------------------------------------------------------------------------------------
    // 인터페이스를 통해서 drawer 을 여는 메소드 오버라이딩
    @Override
    public void openDrawer() {
        view.drawerLayout.openDrawer(GravityCompat.START);
    }

    // 인터페이스를 통해서 drawer 을 닫는 메소드 오버라이딩
    @Override
    public void closeDrawer() {
        view.drawerLayout.closeDrawer(GravityCompat.START);
    }


//--------------------------------------------------------------------------------------------------
//    권한 설정 영역
//--------------------------------------------------------------------------------------------------

    private static final int REQ_CODE = 100; // 요청 코드

    // 갤러리 암시적 인텐트
    public void run(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "선택"), REQ_CODE);
    }

    // 권한 설정을 체크하는 메소드 -- 권한이 없으면 requestPermission 해준다.
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void customCheckPermission(){
        // 기존에 권한 설정이 되어 있다면 바로 실행
        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            run();
        } else {
            // 기존 권한 설정이 안 되어 있으면 권한을 묻는 메소드 호출
            String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(perms , REQ_CODE);
        }
    }

    // 시스템에서 호출해주는 권한 요청 비동기 메소드
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 만약 승인한다면 실행
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            run();
        } else {
            Toast.makeText(this, "권한요청을 승인하셔야 앱을 사용할 수 있습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    // 요청을 했으면 값을 받아와야 한다.
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            if(requestCode == REQ_CODE){
                Uri imageUri = data.getData();
                view.img_navi_profile.setImageURI(imageUri); // TODO View 영역이지만 그냥 둠.
            }
        }
    }



}
