package mukarramah.akibav2;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.onesignal.OneSignal;

import java.util.Calendar;

import mukarramah.akibav2.GetPost.Admin;
import mukarramah.akibav2.GetPost.GetAdmin;
import mukarramah.akibav2.GetPost.Kajian;
import mukarramah.akibav2.Rest.ApiClient;
import mukarramah.akibav2.Rest.ApiInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailKajian extends AppCompatActivity implements OnMapReadyCallback {

    private TextView judul, penyelenggara, tema, pemateri, tempat, alamat, tanggal, waktu, catatan, catatan2, peserta, video;
    private ImageView gmbr, notif;

    //maps
    private MapView mapView;
    private GoogleMap mMap;

    private ApiInterface mApinya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kajian);


        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        judul           = findViewById(R.id.tv_detailJudul);
        penyelenggara   = findViewById(R.id.tv_pelaksanaDetail);
        tema            = findViewById(R.id.tv_detailTema);
        pemateri        = findViewById(R.id.tv_detailPemateri);
        tempat          = findViewById(R.id.tv_detailTempat);
        alamat          = findViewById(R.id.tv_detailAlamat);
        waktu           = findViewById(R.id.tv_detailWaktu);
        catatan         = findViewById(R.id.tv_catatan);
        catatan2        = findViewById(R.id.catatan);
        tanggal         = findViewById(R.id.tv_detailTanggal);
        peserta         = findViewById(R.id.tv_detailPeserta);
        gmbr            = findViewById(R.id.posterDetail);
        notif           = findViewById(R.id.btn_notif);
        video           = findViewById(R.id.tv_streaming);

        //maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapsDetail);
        mapFragment.getMapAsync(this);

        final Intent intent = getIntent();
        
        String kat  = intent.getStringExtra("kategori");

        if (kat.equals("Aqidah")){
            gmbr.setImageResource(R.drawable.aqidah);
        } else if(kat.equals("Akhlak")){
            gmbr.setImageResource(R.drawable.akhlak);
        } else if(kat.equals("Ibadah")){
            gmbr.setImageResource(R.drawable.ibadah);
        } else if(kat.equals("Muamalah")){
            gmbr.setImageResource(R.drawable.muamalah);
        } else if(kat.equals("Thaharah")){
            gmbr.setImageResource(R.drawable.tahara);
        } else if(kat.equals("Lainnya")){
            gmbr.setImageResource(R.drawable.lainnya);
        } else {
            gmbr.setImageResource(R.drawable.lainnya);
        }

        String cat  = intent.getStringExtra("catatan");
        if (cat.equals("")){
            catatan.setVisibility(View.GONE);
            catatan2.setVisibility(View.GONE);
        } else {
            catatan.setVisibility(View.VISIBLE);
            catatan2.setVisibility(View.VISIBLE);
        }


        judul.setText(intent.getStringExtra("judul"));
        pemateri.setText(intent.getStringExtra("penceramah"));
        penyelenggara.setText(intent.getStringExtra("penyedia"));
        tema.setText(intent.getStringExtra("tema"));
        peserta.setText(intent.getStringExtra("peserta"));
        tanggal.setText(intent.getStringExtra("tanggal"));
        waktu.setText(intent.getStringExtra("waktu"));
        catatan.setText(intent.getStringExtra("catatan"));
        tempat.setText(intent.getStringExtra("tempat"));
        alamat.setText(intent.getStringExtra("alamat"));
        video.setText(intent.getStringExtra("video"));

        penyelenggara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intentPenyelenggara = new Intent(DetailKajian.this, DetailAdmin.class);
                intentPenyelenggara.putExtra("nama", penyelenggara.getText().toString());
                startActivity(intentPenyelenggara);
            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if (video.getText().toString()=="Tidak Tersedia Video Streaming"){
                    Toast.makeText(DetailKajian.this, "Tidak bisa Streaming Video", Toast.LENGTH_SHORT).show();
             }
             else {
                 Intent intentStreaming = new Intent(Intent.ACTION_VIEW,Uri.parse(video.getText().toString()));
                 startActivity(intentStreaming);
             }
            }
        });

        alamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kirimKoordinat = new Intent(DetailKajian.this, LihatLokasi.class);
                String latitude     = intent.getStringExtra("latitude").toString();
                String longitude    = intent.getStringExtra("longitude").toString();
                kirimKoordinat.putExtra("latitude", latitude);
                kirimKoordinat.putExtra("longitude", longitude);
                startActivity(kirimKoordinat);
            }
        });

        notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(DetailKajian.this, "Reminder di aktifkan", Toast.LENGTH_SHORT).show();


                Log.e("notif","turn On");
                OneSignal.sendTag("reminder", "on");
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        String v    = getIntent().getStringExtra("latitude");
        String v1   = getIntent().getStringExtra("longitude");

        // Add a marker in Sydney and move the camera
        LatLng latLng = new LatLng(Double.parseDouble(v), Double.parseDouble(v1));
        mMap.addMarker(new MarkerOptions().position(latLng).title("Lokasi Kajian"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(20));
    }
}
