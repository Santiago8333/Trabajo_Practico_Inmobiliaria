package com.in.trabajo_practico_inmobiliaria.ui.inicio;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class InicioViewModel extends AndroidViewModel {
    private MutableLiveData<MapaActual> mMapaActual;

    public InicioViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<MapaActual> getMMapaActual(){
        if(mMapaActual==null){
            mMapaActual=new MutableLiveData<>();
        }
        return mMapaActual;
    }
    public void obtenerMapa(){
        MapaActual mapaActual=new MapaActual();
        mMapaActual.setValue(mapaActual);


    }

    public class MapaActual implements OnMapReadyCallback {
        LatLng Inmo =new LatLng(-33.18387020293627,-66.31395358652898);

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            googleMap.addMarker(new MarkerOptions().position(Inmo).title("Inmobiliaria").snippet("La Mejor"));
            CameraPosition cameraPosition =
                    new CameraPosition.Builder()
                            .target(Inmo)
                            .zoom(19)
                            .build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);

            googleMap.animateCamera(cameraUpdate, 5000, null);

        }
    }
    // TODO: Implement the ViewModel
}