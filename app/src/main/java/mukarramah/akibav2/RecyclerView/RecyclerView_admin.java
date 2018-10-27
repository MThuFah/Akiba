package mukarramah.akibav2.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import mukarramah.akibav2.DetailAdmin;
import mukarramah.akibav2.GetPost.Admin;
import mukarramah.akibav2.R;

public class RecyclerView_admin  extends RecyclerView.Adapter<RecyclerView_admin.ViewHolder> {
    private List<Admin> mArrayList;
    private Context context;

    public RecyclerView_admin (Context context, List<Admin> inputData){
        mArrayList      = inputData;
        this.context    = context;
    }

    @NonNull
    @Override
    public RecyclerView_admin.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_admin,parent, false);
        RecyclerView_admin.ViewHolder vh = new RecyclerView_admin.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView_admin.ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdmin = new Intent(v.getContext(), DetailAdmin.class);
//
//                intentAdmin.putExtra("nama", mArrayList.get(position).getNama());
//                intentAdmin.putExtra("pengurus", mArrayList.get(position).getPengurus());
//                intentAdmin.putExtra("alamat", mArrayList.get(position).getAlamat());
//                intentAdmin.putExtra("kontak", mArrayList.get(position).getKontak());
//                intentAdmin.putExtra("jumlah", mArrayList.get(position).getJumlah());

                v.getContext().startActivity(intentAdmin);

            }
        });
    }

    @Override
    public int getItemCount() { return mArrayList.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView poster;

        public ViewHolder(View itemView) {
            super(itemView);
            poster  = (ImageView) itemView.findViewById(R.id.avatar);
        }
    }
}
