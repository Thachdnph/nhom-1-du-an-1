package vn.poly.quanlybanhang.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.duan1android.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import vn.poly.quanlybanhang.Adapter.HoaDonChiTietAdaper;
import vn.poly.quanlybanhang.Database.HoaDonChiTietDAO;
import vn.poly.quanlybanhang.Database.HoaDonDAO;
import vn.poly.quanlybanhang.Model.HoaDon;
import vn.poly.quanlybanhang.Model.HoaDonChiTiet;

public class HoaDonChiTietActivity extends AppCompatActivity {
    Button btnSuaHoaDonButton;
    HoaDonChiTietDAO hoaDonChiTietDAO;
    HoaDonDAO hoaDonDAO;
    List<HoaDonChiTiet> hoaDonChiTiets;
    String mahoadon;
    ListView listView;
    TextView tvDonHang, tvThoiGian, tvKhachHang, tvChietKhau, tvKhachTra, tvTraLai, tvTongTien;
    @SuppressLint("SimpleDateFormat")
    final
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_chi_tiet);
        anhXaView();
        Intent intent = getIntent();
        mahoadon = intent.getStringExtra("maHD");
        hoaDonChiTiets = hoaDonChiTietDAO.getAllHDCT(mahoadon);
        try {
            HoaDon hoaDon = hoaDonDAO.getHoaDonTheoMa(mahoadon);
            dienThongTinHoaDon(hoaDon);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        doDuLieu(hoaDonChiTiets);


    }


    public void anhXaView() {
        tvDonHang = findViewById(R.id.tvDonHangHDCT);
        tvThoiGian = findViewById(R.id.tvThoiGianHDCT);
        tvKhachHang = findViewById(R.id.tvTenKhachHangHDCT);
        tvChietKhau = findViewById(R.id.tvChietKhau);
        tvKhachTra = findViewById(R.id.tvKhachTra);
        tvTraLai = findViewById(R.id.tvTraLaiHDCT);
        tvTongTien = findViewById(R.id.tvTongTienHDCT);
        listView = findViewById(R.id.lvListHDCT);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(this);
        hoaDonDAO = new HoaDonDAO(this);
        btnSuaHoaDonButton=findViewById(R.id.btnSuaHoaDon);
    }
    public void dienThongTinHoaDon(HoaDon hoaDon){
        tvDonHang.setText(""+hoaDon.getMaHD());
        tvThoiGian.setText(""+simpleDateFormat.format(hoaDon.getNgayBan()));
        tvKhachHang.setText(""+hoaDon.getTenKhachHang());
        tvChietKhau.setText(""+hoaDon.getChietKhau());
        tvTraLai.setText(""+hoaDon.getTraLai());
        tvKhachTra.setText(""+hoaDon.getKhachTra());
        tvTongTien.setText(""+hoaDon.getTongTien());
    }
    public void doDuLieu(List<HoaDonChiTiet> hoaDonChiTiets){
        HoaDonChiTietAdaper  hoaDonChiTietAdaper = new HoaDonChiTietAdaper(this,hoaDonChiTiets);
        listView.setAdapter(hoaDonChiTietAdaper);
    }
    public void suaHoaDon(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(HoaDonChiTietActivity.this);
        builder.setTitle("C???nh b??o");
        builder.setMessage("B???n c?? ch???c mu???n x??a h??a ????n n??y ?");
        builder.setPositiveButton("?????ng ??", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                hoaDonDAO =new HoaDonDAO(HoaDonChiTietActivity.this);
                long chk = hoaDonDAO.deleteHoaDon(mahoadon);
                if(chk>0) {
                    Toast.makeText(getApplicationContext(), "X??a Th??nh C??ng", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
        builder.setNegativeButton("Kh??ng ?????ng ??", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}