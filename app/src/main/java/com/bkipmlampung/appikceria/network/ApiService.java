package com.bkipmlampung.appikceria.network;

import com.bkipmlampung.appikceria.BuildConfig;
import com.bkipmlampung.appikceria.model.AlamatResponse;
import com.bkipmlampung.appikceria.model.BantuanResponse;
import com.bkipmlampung.appikceria.model.BeritaResponse;
import com.bkipmlampung.appikceria.model.CekNomorTeleponResponse;
import com.bkipmlampung.appikceria.model.DataPegawaiResponse;
import com.bkipmlampung.appikceria.model.EditProfilResponse;
import com.bkipmlampung.appikceria.model.KegiatanKepegawaianResponse;
import com.bkipmlampung.appikceria.model.KeluhanPenyakitResponse;
import com.bkipmlampung.appikceria.model.LaluLintasResponse;
import com.bkipmlampung.appikceria.model.LoginResponse;
import com.bkipmlampung.appikceria.model.LogoutResponse;
import com.bkipmlampung.appikceria.model.MetodePengujianResponse;
import com.bkipmlampung.appikceria.model.NotificationResponce;
import com.bkipmlampung.appikceria.model.NotificationSender;
import com.bkipmlampung.appikceria.model.PegawaiTeladanResponse;
import com.bkipmlampung.appikceria.model.PengaduanResponse;
import com.bkipmlampung.appikceria.model.PengajuanPklResponse;
import com.bkipmlampung.appikceria.model.PenyakitIkanResponse;
import com.bkipmlampung.appikceria.model.RegisterResponse;
import com.bkipmlampung.appikceria.model.SejarahResponse;
import com.bkipmlampung.appikceria.model.SetDataImagesResponse;
import com.bkipmlampung.appikceria.model.SetDataResponse;
import com.bkipmlampung.appikceria.model.StrukturOrganisasiResponse;
import com.bkipmlampung.appikceria.model.SurveiResponse;
import com.bkipmlampung.appikceria.model.UpdateProfileResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    @FormUrlEncoded
    @POST("pelayanan/api_lihat_dataimpor.php")
    Call<LaluLintasResponse> dataImport(
            @Field("id") String id,
            @Field("jwt") String jwt
    );

    @FormUrlEncoded
    @POST("pelayanan/api_lihat_datadomestik.php")
    Call<LaluLintasResponse> dataDomestik(
            @Field("id") String id,
            @Field("jwt") String jwt
    );

    @FormUrlEncoded
    @POST("pelayanan/api_lihat_dataekspor.php")
    Call<LaluLintasResponse> dataEksport(
            @Field("id") String id,
            @Field("jwt") String jwt
    );

    @FormUrlEncoded
    @POST("lab/api_lihat_informasiikan.php")
    Call<PenyakitIkanResponse> dataPenyakitIkan(
            @Field("id") String id_user,
            @Field("jwt") String jwt
    );

    @FormUrlEncoded
    @POST("lab/api_lihat_metodepengujianlab.php")
    Call<MetodePengujianResponse> dataMetodePengujian(
            @Field("id") String id,
            @Field("jwt") String jwt
    );

    @FormUrlEncoded
    @POST("pegawaian/api_lihat_berita.php")
    Call<BeritaResponse> dataBerita(
            @Field("id") String id,
            @Field("jwt") String jwt
    );

    @FormUrlEncoded
    @POST("pegawaian/api_lihat_datapegawai.php")
    Call<DataPegawaiResponse> dataDataPegawai(
            @Field("id") String id,
            @Field("jwt") String jwt
    );

    @FormUrlEncoded
    @POST("pegawaian/api_lihat_kegiatan.php")
    Call<KegiatanKepegawaianResponse> dataKegiatanKepegawaian(
            @Field("id") String id,
            @Field("jwt") String jwt
    );

    @FormUrlEncoded
    @POST("pegawaian/api_lihat_pegawaiteladan.php")
    Call<PegawaiTeladanResponse> dataPegawaiTeladan(
            @Field("id") String id,
            @Field("jwt") String jwt
    );

    @GET("bantuan/api_bantuan_syarat_ketentuan.php")
    Call<BantuanResponse> dataSyaratDanKetentuan();

    @GET("bantuan/api_bantuan_kebijakan.php")
    Call<BantuanResponse> dataKebijakanPrivasi();

    @FormUrlEncoded
    @POST("pegawaian/api_lihat_sejarah.php")
    Call<SejarahResponse> dataSejarah(
            @Field("id") String id,
            @Field("jwt") String jwt
    );

    @FormUrlEncoded
    @POST("pegawaian/api_lihat_strukturorg.php")
    Call<StrukturOrganisasiResponse> dataStrukturOrganisasi(
            @Field("id") String id,
            @Field("jwt") String jwt
    );

    @FormUrlEncoded
    @POST("pegawaian/api_lihat_alamat.php")
    Call<AlamatResponse> dataAlamat(
            @Field("id") String id,
            @Field("jwt") String jwt
    );

    @FormUrlEncoded
    @POST("pelayanan/api_lihat_survey.php")
    Call<SurveiResponse> dataRiwayatSurvei(
            @Field("id") String id,
            @Field("jwt") String jwt
    );

    @FormUrlEncoded
    @POST("pelayanan/api_lihat_pengaduanpl.php")
    Call<PengaduanResponse> dataRiwayatPengaduan(
            @Field("id") String id,
            @Field("jwt") String jwt
    );

    @FormUrlEncoded
    @POST("lab/api_lihat_pengaduanlab.php")
    Call<KeluhanPenyakitResponse> dataRiwayatKeluhan(
            @Field("id") String id,
            @Field("jwt") String jwt
    );

    @FormUrlEncoded
    @POST("auth/api_logout.php")
    Call<LogoutResponse> logout(
            @Field("id") String id,
            @Field("jwt") String jwt,
            @Field("imei") String imei
    );

    @FormUrlEncoded
    @POST("umum/api_lihat_pengajuanpkl.php")
    Call<PengajuanPklResponse> dataRiwayatPengajuanPKL(
            @Field("id") String id,
            @Field("jwt") String jwt
    );

    @FormUrlEncoded
    @POST("pelayanan/api_post_survey.php")
    Call<SetDataResponse> setDataSurvei(
            @Field("id") String id,
            @Field("jwt") String jwt,
            @Field("nama") String nama,
            @Field("usia") String usia,
            @Field("jenis_layanan") String jenisLayanan,
            @Field("waktu_survey") String waktuSurvey,
            @Field("jenis_kelamin") String jenisKelamin,
            @Field("pendidikan") String pendidikan,
            @Field("pekerjaan") String pekerjaan,
            @Field("penilaian_1") String penilaian1,
            @Field("penilaian_2") String penilaian2,
            @Field("penilaian_3") String penilaian3,
            @Field("penilaian_4") String penilaian4,
            @Field("penilaian_5") String penilaian5,
            @Field("penilaian_6") String penilaian6,
            @Field("penilaian_7") String penilaian7,
            @Field("penilaian_8") String penilaian8,
            @Field("penilaian_9") String penilaian9,
            @Field("kritik_saran") String kritik_saran,
            @Field("nilai_1") String nilai1,
            @Field("nilai_2") String nilai2,
            @Field("nilai_3") String nilai3,
            @Field("nilai_4") String nilai4,
            @Field("nilai_5") String nilai5,
            @Field("nilai_6") String nilai6,
            @Field("nilai_7") String nilai7,
            @Field("nilai_8") String nilai8,
            @Field("nilai_9") String nilai9
    );

    @FormUrlEncoded
    @POST("auth/api_ceknomor.php")
    Call<CekNomorTeleponResponse> cekNomorTelepon(
            @Field("no_hp") String no_hp
    );

    @FormUrlEncoded
    @POST("auth/api_login.php")
    Call<LoginResponse> login(
            @Field("no_hp") String no_hp,
            @Field("password") String password,
            @Field("imei") String imei
    );

    @FormUrlEncoded
    @POST("auth/api_lupapassword.php")
    Call<LogoutResponse> lupaPassword(
            @Field("no_hp") String no_hp,
            @Field("password_baru") String password_baru,
            @Field("konfirmasi_password") String konfirmasi_password
    );

    @Multipart
    @POST("auth/api_register.php")
    Call<RegisterResponse> registerUser(
            @Part MultipartBody.Part filegambar,
            @Part("username") RequestBody username,
            @Part("no_hp") RequestBody no_hp,
            @Part("password") RequestBody password,
            @Part("alamat") RequestBody alamat,
            @Part("email") RequestBody email,
            @Part("imei") RequestBody imei
    );

    @Multipart
    @POST("auth/api_register1.php")
    Call<RegisterResponse> registerUser1(
            @Part MultipartBody.Part filegambar
    );

    @Multipart
    @POST("umum/api_post_pengajuanpkl.php")
    Call<SetDataImagesResponse> setDataPengajuanPkl(
            @Part MultipartBody.Part filegambar,
            @Part("id") RequestBody id,
            @Part("jwt") RequestBody jwt,
            @Part("nama") RequestBody nama,
            @Part("tempat_lahir") RequestBody tempat_lahir,
            @Part("tahun_lahir") RequestBody tahun_lahir,
            @Part("nama_univ") RequestBody nama_univ,
            @Part("nama_jur") RequestBody nama_jur,
            @Part("nilai_pkl") RequestBody nilai_pkl,
            @Part("konfirmasi") RequestBody konfirmasi
    );

    @Multipart
    @POST("pelayanan/api_post_pengaduanpl.php")
    Call<SetDataImagesResponse> setDataPengaduanLayanan(
            @Part MultipartBody.Part filegambar,
            @Part("id") RequestBody id,
            @Part("jwt") RequestBody jwt,
            @Part("nama") RequestBody nama,
            @Part("tanggal") RequestBody tanggal,
            @Part("alamat") RequestBody alamat,
            @Part("pekerjaan_i") RequestBody pekerjaan_i,
            @Part("no_hp") RequestBody no_hp,
            @Part("bidang_pelayanan") RequestBody bidang_pelayanan,
            @Part("tujuan_pengaduan") RequestBody tujuan_pengaduan,
            @Part("sumber_informasi") RequestBody sumber_informasi,
            @Part("isi_aduan") RequestBody isi_aduan,
            @Part("konfirmasi") RequestBody konfirmasi
    );

    @FormUrlEncoded
    @POST("pelayanan/api_post_pengaduanpl2.php")
    Call<SetDataImagesResponse> setDataPengaduanLayanan2(
            @Field("id") String id,
            @Field("jwt") String jwt,
            @Field("nama") String nama,
            @Field("tanggal") String tanggal,
            @Field("alamat") String alamat,
            @Field("pekerjaan_i") String pekerjaan_i,
            @Field("no_hp") String no_hp,
            @Field("bidang_pelayanan") String bidang_pelayanan,
            @Field("tujuan_pengaduan") String tujuan_pengaduan,
            @Field("sumber_informasi") String sumber_informasi,
            @Field("isi_aduan") String isi_aduan,
            @Field("konfirmasi") String konfirmasi
    );

    @Multipart
    @POST("lab/api_post_pengaduanlab.php")
    Call<SetDataImagesResponse> setDataKeluhanPenyakit(
            @Part MultipartBody.Part filegambar,
            @Part("id") RequestBody id,
            @Part("jwt") RequestBody jwt,
            @Part("nama") RequestBody nama,
            @Part("no_hp") RequestBody no_hp,
            @Part("alamat_lengkap") RequestBody alamat_lengkap,
            @Part("jenis_komoditas") RequestBody jenis_komoditas,
            @Part("luas_total_la_bu") RequestBody luas_total_la_bu,
            @Part("luas_ter_penyakit") RequestBody luas_ter_penyakit,
            @Part("gejala_k") RequestBody gejala_k,
            @Part("jum_kematian") RequestBody jum_kematian,
            @Part("tanggal_terjangkit") RequestBody tanggal_terjangkit,
            @Part("lokasi_budidaya") RequestBody lokasi_budidaya,
            @Part("konfirmasi") RequestBody konfirmasi
    );

    @FormUrlEncoded
    @POST("auth/api_edit.php")
    Call<EditProfilResponse> editProfilTanpaGambar(
            @Field("id") String id,
            @Field("jwt") String jwt,
            @Field("username") String username,
            @Field("no_hp") String no_hp,
            @Field("photo") String photo,
            @Field("alamat") String alamat,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("auth/api_gantipass.php")
    Call<LogoutResponse> gantiPassword(
            @Field("id") String id,
            @Field("jwt") String jwt,
            @Field("password_lama") String password_lama,
            @Field("password_baru") String password_baru,
            @Field("konfirmasi_password") String konfirmasi_password_baru
    );

    @Multipart
    @POST("auth/api_editimage.php")
    Call<UpdateProfileResponse> editProfilDenganGambar(
            @Part MultipartBody.Part filegambar,
            @Part("id") RequestBody id,
            @Part("jwt") RequestBody jwt,
            @Part("username") RequestBody username,
            @Part("no_hp") RequestBody no_hp,
            @Part("photo") RequestBody photo,
            @Part("alamat") RequestBody alamat,
            @Part("email") RequestBody email
    );
}
