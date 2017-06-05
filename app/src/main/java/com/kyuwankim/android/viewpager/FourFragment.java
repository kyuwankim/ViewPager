package com.kyuwankim.android.viewpager;


import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class FourFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap map = null;
    LocationManager manager = null;

    public FourFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four, container, false);

        // 프래그먼트에서 map(아이디 호출하기)
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        // 맵이 사용할 준비가 되면 onMapReady 함수가 자동으로 호출된다
        mapFragment.getMapAsync(this);


        // 상위 액티비티의 자원을 사용하기 위해서 Activity를 가져온다
        MainActivity mainActivity = (MainActivity) getActivity();
        manager = mainActivity.getLocationManager();

        return view;

    }

    // 현재 프래그먼트가 러닝 직전

    @Override
    public void onResume() {
        super.onResume();

        // GPS 리스너 등록
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 위치제공자
                1000,   // 변경사항 체크주기
                1,      // 변경사항 체크거리 meter
                locationListener
        );
    }
    // 현재 프래그먼트가 정지


    @Override
    public void onPause() {
        super.onPause();

    }

    // 이 함수에서부터 맵을 그리기 시작
    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        // 좌표만 생성
        LatLng sinsa = new LatLng(37.516066, 127.019361);

        // 좌표를 적용
        // 1. 마커를 화면에 그린다
        MarkerOptions marker = new MarkerOptions();
        marker.position(sinsa);
        marker.title("산사역");

        map.addMarker(marker);
        // 2. 맵의 중심을 해당 좌표로 이동시킨다
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sinsa, 17));

    }

    // GPS 사용을 위해서 좌표 리스너를 실행

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // 경도
            double lng = location.getLongitude();
            // 위도
            double lat = location.getLatitude();
            // 고도
            double alt = location.getAltitude();
            // 정확도
            float acc = location.getAccuracy();
            // 위치제공자
            String provider = location.getProvider();

            // 바뀐 현재좌표
            LatLng current = new LatLng(lat, lng);

            // 바뀐 현재좌표로 이동
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 20));


        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
}
