package mukarramah.akibav2;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import mukarramah.akibav2.GetPost.Admin;
import mukarramah.akibav2.GetPost.GetAdmin;
import mukarramah.akibav2.GetPost.GetKajian;
import mukarramah.akibav2.GetPost.Kajian;
import mukarramah.akibav2.RecyclerView.RecyclerView_kajian;
import mukarramah.akibav2.Rest.ApiClient;
import mukarramah.akibav2.Rest.ApiInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailAdmin extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshKajian;
    private RecyclerView recyclerViewKajian;

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private ApiInterface mApinya;
    SessionManager session;

    TextView nama, lembaga, alamat, email, hp;
    ImageView kluar;

    RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_admin);

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();

        String Snama = user.get(SessionManager.KEY_NAME);
        String Slembaga = user.get(SessionManager.KEY_LEMBAGA);
        String Salamat = user.get(SessionManager.KEY_ALAMAT);
        String Semail = user.get(SessionManager.KEY_EMAIL);
        String Shp = user.get(SessionManager.KEY_HP);

        nama = findViewById(R.id.tv_nama);
        lembaga = findViewById(R.id.tv_lembaga);
        alamat = findViewById(R.id.tv_alamat);
        email = findViewById(R.id.tv_email);
        hp = findViewById(R.id.tv_hp);
        kluar = findViewById(R.id.btn_kluar);

        nama.setText(Html.fromHtml(Snama));
        lembaga.setText(Html.fromHtml(Slembaga));
        alamat.setText(Html.fromHtml(Salamat));
        email.setText(Html.fromHtml(Semail));
        hp.setText(Html.fromHtml(Shp));

        kluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.logoutUser();

            }
        });

       // rv
        swipeRefreshKajian      = (SwipeRefreshLayout) findViewById(R.id.sr_kajian);
        recyclerViewKajian      = findViewById(R.id.rv_kajian);
//        rl                      = findViewById(R.id.rl);
        layoutManager           = new LinearLayoutManager(DetailAdmin.this, LinearLayoutManager.VERTICAL, false);
        mApinya                 = ApiClient.getClient().create(ApiInterface.class);

        swipeRefreshKajian.setEnabled(true);
        swipeRefreshKajian.setRefreshing(true);
        recyclerViewKajian.setLayoutManager(layoutManager);

        final Call<GetKajian> ambilData = mApinya.postLembaga(nama.getText().toString());
        ambilData.enqueue(new Callback<GetKajian>() {
            @Override
            public void onResponse(Call<GetKajian> call, Response<GetKajian> response) {
                List<Kajian> ambilData = response.body().getResult();
                    adapter = new RecyclerView_kajian(getApplicationContext(), ambilData);
                    recyclerViewKajian.setAdapter(adapter);
                    swipeRefreshKajian.setEnabled(false);
            }

            @Override
            public void onFailure(Call<GetKajian> call, Throwable t) {
                swipeRefreshKajian.setEnabled(false);
            }
        });
    }
    }

