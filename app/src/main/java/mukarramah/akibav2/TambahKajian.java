package mukarramah.akibav2;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.onesignal.OneSignal;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import mukarramah.akibav2.Rest.ApiClient;
import mukarramah.akibav2.Rest.ApiInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahKajian extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    private static final int REQUEST_CODE = 1;
    EditText judul, penyelenggara, tema, pemateri, tempat, alamat, streaming,catatan;
    TextView waktu, tanggal, lokasi, support;
    Spinner kategori, peserta;
    ImageView ceklis;
    RadioButton iya, tidak;
    RadioGroup rg;
    private Button tambah, maps;
    String latitude, longitude, result;
    Intent latLong;

    //waktu dan tanggal
    private int mYear, mMonth, mDay, mHour, mMinute;

    private SessionManager session;
    ApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kajian);


        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        judul           = findViewById(R.id.et_judul);
        penyelenggara   = findViewById(R.id.et_penyelenggara);
        tema            = findViewById(R.id.et_tema);
        kategori        = findViewById(R.id.sp_kategori);
        pemateri        = findViewById(R.id.et_pemateri);
        tanggal         = findViewById(R.id.et_tanggal);
        tempat          = findViewById(R.id.et_tempat);
        alamat          = findViewById(R.id.et_alamat);
        support         = findViewById(R.id.et_reminder);
        waktu           = findViewById(R.id.et_waktu);
        catatan         = findViewById(R.id.et_catatan);
        peserta         = findViewById(R.id.sp_peserta);
        tambah          = findViewById(R.id.btn_tambahKajian);
        lokasi          = findViewById(R.id.tv_lokasi);
        ceklis          = findViewById(R.id.ceklis);
        streaming       = findViewById(R.id.et_streaming);
        iya             = findViewById(R.id.rd_iya);
        tidak           = findViewById(R.id.rd_tidak);
        rg              = findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(this);

        streaming.setEnabled(false);
        ceklis.setVisibility(View.INVISIBLE);

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();

        String Snama     = user.get(SessionManager.KEY_NAME);
        penyelenggara.setText(Html.fromHtml(Snama));

        lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent maps = new Intent(TambahKajian.this, PilihLokasi.class);
                startActivityForResult(maps, REQUEST_CODE);
            }
        });

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(TambahKajian.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                SimpleDateFormat hari   = new SimpleDateFormat("E");
                                SimpleDateFormat bulan  = new SimpleDateFormat("MMM");
                                SimpleDateFormat bulann = new SimpleDateFormat("MM");
                                SimpleDateFormat harii  = new SimpleDateFormat("dd");

                                Date date = new Date(year, monthOfYear, dayOfMonth-1);
                                Date date2 = new Date(year,monthOfYear,dayOfMonth);

                                String harinya  = hari.format(date);
                                String bulannya = bulan.format(date);
                                String bulannnya = bulann.format(date);
                                String hariinya = harii.format(date2);

                                tanggal.setText(harinya+" "+dayOfMonth + " "+bulannya+ " " + year);
                                support.setText(year+"-"+bulannnya+"-"+hariinya);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        waktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(TambahKajian.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                waktu.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });



        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String judulnya             = judul.getText().toString();
                String penyelenggaranya     = penyelenggara.getText().toString();
                String temanya              = tema.getText().toString();
                String pematerinya          = pemateri.getText().toString();
                String tempatnya            = tempat.getText().toString();
                String alamatnya            = alamat.getText().toString();
                String tanggalnya           = tanggal.getText().toString();
                String waktunya             = waktu.getText().toString();
                String catatannya           = catatan.getText().toString();
                String kategorinya          = kategori.getSelectedItem().toString();
                String pesertanya           = peserta.getSelectedItem().toString();
                String videonya             = streaming.getText().toString();
                String supportnya           = support.getText().toString();

                mApiInterface   = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponseBody> simpan = mApiInterface.tambahKajian(penyelenggaranya, judulnya,temanya, kategorinya, pematerinya,tempatnya, alamatnya,
                        latitude, longitude, tanggalnya, waktunya, catatannya, videonya, pesertanya, supportnya);

                simpan.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(TambahKajian.this, "Kajian Telah ditambahkan", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(TambahKajian.this, MainActivity.class));
                        } else {
                            Toast.makeText(TambahKajian.this, "Kesalahan dalam mengirim data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });

        Log.e("notif","turn off");
        OneSignal.sendTag("reminder", "off");

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rd_iya){
            streaming.setText("Alamat Website Lengkap");
            streaming.setEnabled(true);
        }
        else {
            streaming.setEnabled(false);
            streaming.setText("Tidak Tersedia Video Streaming");
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                latitude   = data.getStringExtra("latitude");
                longitude  = data.getStringExtra("longitude");
                if (latitude!=null){
                    ceklis.setVisibility(View.VISIBLE);
                }
                // do something with the result

            } else if (resultCode == Activity.RESULT_CANCELED) {
                // some stuff that will happen if there's no result
            }
        }
    }


}