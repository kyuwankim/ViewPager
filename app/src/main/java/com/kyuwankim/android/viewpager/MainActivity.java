package com.kyuwankim.android.viewpager;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    Fragment one;
    Fragment two;
    Fragment three;
    Fragment four;
    LocationManager manager;

    public LocationManager getLocationManager(){

        return manager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        // 뷰페이저 위젯 연결
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);

        // 탭레이아웃 위젯 연결
        tabLayout.addTab(tabLayout.newTab().setText("One"));
        tabLayout.addTab(tabLayout.newTab().setText("Two"));
        tabLayout.addTab(tabLayout.newTab().setText("Three"));
        tabLayout.addTab(tabLayout.newTab().setText("Four"));

        // 프래그먼트들 생성
        one = new OneFragment();
        two = new TwoFragment();
        three = new ThreeFragment();
        four = new FourFragment();

        // 프래그먼트를 저장소에 담음
        List<Fragment> datas = new ArrayList<>();
        datas.add(one);
        datas.add(two);
        datas.add(three);
        datas.add(four);

        // 프래그먼트 매니저와 함께 어댑터 전달
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), datas);

        // 어댑터를 페이저 위젯에 연결
        viewPager.setAdapter(adapter);

        // 페이저가 변경되었을때 탭을 변경해주는 리스너
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // 탭이 변경되었을때 페이저를 변경해주는 리스너
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    // 위치제공자 사용을 위한 권한처리
    private final int REQ_PERMISSION = 100;
    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission(){
        //1 권한체크 - 특정권한이 있는지 시스템에 물어본다
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

        }else{
            // 2. 권한이 없으면 사용자에 권한을 달라고 요청
            String permissions[] = {Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissions ,REQ_PERMISSION); // -> 권한을 요구하는 팝업이 사용자 화면에 노출된다
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQ_PERMISSION){
            // 3.1 사용자가 승인을 했음
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }else{
                cancel();
            }
        }
    }
    public void cancel(){
        Toast.makeText(this, "권한요청을 승인하셔야 GPS를 사용할 수 있습니다.", Toast.LENGTH_SHORT).show();
        finish();
    }




    class PagerAdapter extends FragmentStatePagerAdapter {
        List<Fragment> datas;

        public PagerAdapter(FragmentManager fm, List<Fragment> datas) {
            super(fm);

            this.datas = datas;
        }

        @Override
        public Fragment getItem(int position) {
            return datas.get(position);
        }

        @Override
        public int getCount() {
            return datas.size();
        }
    }
}
