package mukarramah.akibav2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import mukarramah.akibav2.GetPost.Admin;
import mukarramah.akibav2.GetPost.GetAdmin;
import mukarramah.akibav2.Rest.ApiClient;
import mukarramah.akibav2.Rest.ApiInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    TextInputLayout etUsername, etPassword;
    TextView daftar;
    String username, password, jenis;
    Button btnLogin;
    ProgressDialog Proses;
    Button btnRegister;

    //contoh dari ilham
    //ini yang pull ke dua
    //ini dr mukarammah
    SessionManager session;

    Context mContext;
    ApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername      = (TextInputLayout) findViewById(R.id.et_nama);
        etPassword      = (TextInputLayout) findViewById(R.id.et_pass);
        btnLogin        = (Button) findViewById(R.id.btn_login);
        daftar          = (TextView) findViewById(R.id.tv_register);

        session = new SessionManager(getApplicationContext());

        Proses = new ProgressDialog(this);
        Proses.setCancelable(true);

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intentDaftar = new Intent(Login.this, Register.class);
                startActivity(intentDaftar);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username    = etUsername.getEditText().getText().toString();
                password    = etPassword.getEditText().getText().toString();

                if (username.equals("") && password.equals("")) {
                    Toast.makeText(Login.this, "Silahkan Lengkapi Data Diatas", Toast.LENGTH_SHORT).show();
                } else {
                    Proses.setMessage("Sedang Memproses");
                    Proses.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    Proses.show();
                    try {
                        Login();
                    }catch (Exception e){
                        Log.e("Tes","API Bermasalah");
                        Toast.makeText(Login.this, "API Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void Login() {
        Log.e("login ","masuk1");
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GetAdmin> call = mApiInterface.login(username, password);
        call.enqueue(new Callback<GetAdmin>() {
            @Override
            public void onResponse(Call<GetAdmin> call, Response<GetAdmin> response) {
                Proses.dismiss();
                List<Admin> ambil = response.body().getResult();

                String lembaganya   = ambil.get(0).getLembaga();
                String namanya      = ambil.get(0).getNama();
                String alamatnya    = ambil.get(0).getAlamat();
                String emailnya     = ambil.get(0).getEmail();
                String hpnya        = ambil.get(0).getHp();

                Toast.makeText(Login.this, "Selamat Datang", Toast.LENGTH_SHORT).show();
                session.createLoginSession(lembaganya, namanya, alamatnya, password, username, hpnya);
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<GetAdmin> call, Throwable t) {
                Proses.dismiss();
                Log.e("Status  ",t.toString());
                Toast.makeText(Login.this, "Masukkan nama dan sandi dengan benar!", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
