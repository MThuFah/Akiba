package mukarramah.akibav2.Rest;

import java.util.List;

import mukarramah.akibav2.GetPost.Admin;
import mukarramah.akibav2.GetPost.GetAdmin;
import mukarramah.akibav2.GetPost.GetKajian;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @GET("wsKajian.php")
    Call<GetKajian> getKajian();

    @GET("wsAdmin.php")
    Call<GetAdmin> getAdmin();

    @FormUrlEncoded
    @POST("InsertKajian.php")
    Call<ResponseBody> tambahKajian (@Field("penyedia")     String penyedia,
                                     @Field("judul")        String judul,
                                     @Field("tema")         String tema,
                                     @Field("kategori")     String kategori,
                                     @Field("penceramah")   String penceramah,
                                     @Field("tempat")       String tempat,
                                     @Field("alamat")       String alamat,
                                     @Field("latitude")     String latitude,
                                     @Field("longitude")    String longitude,
                                     @Field("tanggal")      String tanggal,
                                     @Field("waktu")        String waktu,
                                     @Field("catatan")      String catatan,
                                     @Field("video")        String video,
                                     @Field("peserta")      String peserta,
                                     @Field("support")      String support);
    @FormUrlEncoded
    @POST("tampilPenyelenggara.php")
    Call<List<Admin>> tampilPenyelenggara (@Field("nama") String nama);

    @FormUrlEncoded
    @POST("login.php")
    Call<GetAdmin> login (@Field("email")    String email,
                          @Field("sandi")    String sandi);

    @FormUrlEncoded
    @POST("Register.php")
    Call<ResponseBody> daftar       (@Field("lembaga")    String lembaga,
                                     @Field("pengurus")   String pengurus,
                                     @Field("alamat")     String alamat,
                                     @Field("email")      String email,
                                     @Field("sandi")      String sandi,
                                     @Field("hp")         String hp);

    @FormUrlEncoded
    @POST("wsPostLembaga.php")
    Call<GetKajian> postLembaga (@Field("penyedia")      String penyedia);

}
