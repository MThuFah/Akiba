package mukarramah.akibav2.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import mukarramah.akibav2.DetailKajian;
import mukarramah.akibav2.GetPost.Kajian;
import mukarramah.akibav2.R;

public class RecyclerView_kajian extends RecyclerView.Adapter<RecyclerView_kajian.ViewHolder> {

    private List<Kajian> mArrayList;
    private Context context;

    public RecyclerView_kajian (Context context, List<Kajian> inputData){
        mArrayList      = inputData;
        this.context    = context;
    }

    @NonNull
    @Override
    public RecyclerView_kajian.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_kajian,parent, false);


        RecyclerView_kajian.ViewHolder vh = new RecyclerView_kajian.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView_kajian.ViewHolder holder, final int position) {

        String date     = mArrayList.get(position).getTanggal();
        String kat      = mArrayList.get(position).getKategori();

        if (kat.equals("Aqidah")){
            holder.poster.setImageResource(R.drawable.aqidah);
        } else if(kat.equals("Akhlak")){
            holder.poster.setImageResource(R.drawable.akhlak);
        } else if(kat.equals("Ibadah")){
            holder.poster.setImageResource(R.drawable.ibadah);
        } else if(kat.equals("Muamalah")){
            holder.poster.setImageResource(R.drawable.muamalah);
        } else if(kat.equals("Thaharah")){
            holder.poster.setImageResource(R.drawable.tahara);
        } else if(kat.equals("Lainnya")){
            holder.poster.setImageResource(R.drawable.lainnya);
        } else {
            holder.poster.setImageResource(R.drawable.lainnya);
        }

        if(date.length()==15){
           String hari  = date.substring(0, 3);
           String tgl   = date.substring(4, 6);
           String bulan = date.substring(7, 10);

           holder.hari.setText(hari);
           holder.tanggal.setText(tgl);
           holder.bulan.setText(bulan);
        } else if(date.length()==14){
            String hari  = date.substring(0, 3);
            String tgl   = date.substring(4, 5);
            String bulan = date.substring(6, 9);

            holder.hari.setText(hari);
            holder.tanggal.setText(tgl);
            holder.bulan.setText(bulan);
        } else {
            Toast.makeText(context, "Masih ada salah tanggal", Toast.LENGTH_SHORT).show();
        }

        holder.judul.setText(mArrayList.get(position).getJudul());
        holder.pemateri.setText(mArrayList.get(position).getPenceramah());
        holder.tempat.setText(mArrayList.get(position).getTempat());
        holder.alamat.setText(mArrayList.get(position).getAlamat());
        holder.waktu.setText(mArrayList.get(position).getWaktu()+" WITA");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentKajian = new Intent(v.getContext(), DetailKajian.class);

                intentKajian.putExtra("penyedia", mArrayList.get(position).getPenyedia());
                intentKajian.putExtra("judul", mArrayList.get(position).getJudul());
                intentKajian.putExtra("tema", mArrayList.get(position).getTema());
                intentKajian.putExtra("penceramah", mArrayList.get(position).getPenceramah());
                intentKajian.putExtra("tempat", mArrayList.get(position).getTempat());
                intentKajian.putExtra("alamat", mArrayList.get(position).getAlamat());
                intentKajian.putExtra("tanggal", mArrayList.get(position).getTanggal());
                intentKajian.putExtra("waktu", mArrayList.get(position).getWaktu());
                intentKajian.putExtra("catatan", mArrayList.get(position).getCatatan());
                intentKajian.putExtra("peserta", mArrayList.get(position).getPeserta());
                intentKajian.putExtra("kategori", mArrayList.get(position).getKategori());
                intentKajian.putExtra("latitude", mArrayList.get(position).getLatitude());
                intentKajian.putExtra("longitude", mArrayList.get(position).getLongitude());
                intentKajian.putExtra("video",mArrayList.get(position).getVideo());

                v.getContext().startActivity(intentKajian);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView poster;
        TextView hari, tanggal, bulan, tempat, alamat, waktu, judul, pemateri, video;

        public ViewHolder(View itemView) {
            super(itemView);

            judul       = itemView.findViewById(R.id.tv_judulKajian);
            pemateri    = itemView.findViewById(R.id.tv_pemateriKajian);
            hari        = itemView.findViewById(R.id.tv_hari);
            tanggal     = itemView.findViewById(R.id.tv_tgl);
            bulan       = itemView.findViewById(R.id.tv_bulan);
            tempat      = itemView.findViewById(R.id.tv_tempatKajian);
            alamat      = itemView.findViewById(R.id.tv_alamatKajian);
            waktu       = itemView.findViewById(R.id.tv_waktuKajian);
            poster      = itemView.findViewById(R.id.poster);
        }
    }
}
