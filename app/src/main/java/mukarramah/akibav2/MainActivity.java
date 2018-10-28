package mukarramah.akibav2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.onesignal.OneSignal;

import java.util.List;

import mukarramah.akibav2.GetPost.GetKajian;
import mukarramah.akibav2.GetPost.Kajian;
import mukarramah.akibav2.RecyclerView.RecyclerView_kajian;
import mukarramah.akibav2.Rest.ApiClient;
import mukarramah.akibav2.Rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshKajian;
    private RecyclerView recyclerViewKajian;

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private FloatingActionButton fab_tambahKajian;

    SessionManager session;

    private ImageView profil;

    ApiInterface mApiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        swipeRefreshKajian      = (SwipeRefreshLayout) findViewById(R.id.sr_kajian);
        recyclerViewKajian      = findViewById(R.id.rv_kajian);
        layoutManager           = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        mApiInterface           = ApiClient.getClient().create(ApiInterface.class);
        fab_tambahKajian        = findViewById(R.id.fab_tambahKajian);

        swipeRefreshKajian.setEnabled(true);
        swipeRefreshKajian.setRefreshing(true);
        recyclerViewKajian.setLayoutManager(layoutManager);

        final Call<GetKajian> ambilData = mApiInterface.getKajian();
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
            }
        });

        session = new SessionManager(getApplicationContext());
        profil  = findViewById(R.id.profil);

        fab_tambahKajian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(session.isLoggedIn())){
                    session.checkLogin();
                }else{
                    startActivity( new Intent(MainActivity.this, TambahKajian.class));
                }
            }
        });

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(session.isLoggedIn())){
                    session.checkLogin();
                }else{
                    startActivity( new Intent(MainActivity.this, DetailAdmin.class));
                }
            }
        });

    }
}


