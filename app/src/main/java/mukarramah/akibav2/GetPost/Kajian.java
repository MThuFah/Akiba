package mukarramah.akibav2.GetPost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Kajian {
    @SerializedName("penyedia")
    @Expose
    private String penyedia;

    @SerializedName("judul")
    @Expose
    private String judul;

    @SerializedName("tema")
    @Expose
    private String tema;

    @SerializedName("kategori")
    @Expose
    private String kategori;

    @SerializedName("penceramah")
    @Expose
    private String penceramah;

    @SerializedName("tempat")
    @Expose
    private String tempat;

    @SerializedName("alamat")
    @Expose
    private String alamat;

    @SerializedName("latitude")
    @Expose
    private String latitude;

    @SerializedName("longitude")
    @Expose
    private String longitude;

    @SerializedName("tanggal")
    @Expose
    private String tanggal;

    @SerializedName("waktu")
    @Expose
    private String waktu;

    @SerializedName("catatan")
    @Expose
    private String catatan;

    @SerializedName("video")
    @Expose
    private String video;

    @SerializedName("peserta")
    @Expose
    private String peserta;

    @SerializedName("support")
    @Expose
    private String support;

    public String getPenyedia() {
        return penyedia;
    }

    public void setPenyedia(String penyedia) {
        this.penyedia = penyedia;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getTempat() {
        return tempat;
    }

    public void setTempat(String tempat) {
        this.tempat = tempat;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getPeserta() {
        return peserta;
    }

    public void setPeserta(String peserta) {
        this.peserta = peserta;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public String getPenceramah() {
        return penceramah;
    }

    public void setPenceramah(String penceramah) {
        this.penceramah = penceramah;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = this.catatan;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
}
