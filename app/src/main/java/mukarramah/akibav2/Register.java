package mukarramah.akibav2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mukarramah.akibav2.Rest.ApiClient;
import mukarramah.akibav2.Rest.ApiInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    EditText lembaga, pengurus, alamat, email, hp;
     private Button register;

     ApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        lembaga         = findViewById(R.id.et_lembaga);
        pengurus        = findViewById(R.id.et_pengurus);
        alamat          = findViewById(R.id.et_alamat);
        email           = findViewById(R.id.et_email);
        hp              = findViewById(R.id.et_hp);
        register        = findViewById(R.id.btn_Register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String lembaganya   = lembaga.getText().toString();
                String pengurusnya   = pengurus.getText().toString();
                String alamatnya     = alamat.getText().toString();
                String emailnya      = email.getText().toString();
                String hpnya         = hp.getText().toString();

                mApiInterface   = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponseBody> daftar = mApiInterface.daftar(lembaganya,pengurusnya,alamatnya,emailnya,"n/a",hpnya);

                daftar.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(Register.this, "Anda Telah Berhasil Mendaftar dan akan segera di verifikasi", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(Register.this, MainActivity.class));
                        } else {
                            Toast.makeText(Register.this, "Kesalahan dalam mengirim data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

            }
        });
    }
}
