package vn.poly.quanlybanhang.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import vn.poly.quanlybanhang.Database.DonViTinhDAO;
import vn.poly.quanlybanhang.Model.DonViTinh;

import com.example.duan1android.R;

import java.util.List;

public
class DonViTinhAdapter extends BaseAdapter {
    final Context context;
    final List<DonViTinh> list;
    DonViTinhDAO donViTinhDAO;
    Dialog dialog;
    EditText edSua;

    public DonViTinhAdapter(Context context, List<DonViTinh> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
    ViewHolder viewHolder;
        if(view==null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_loai_san_pham, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.imgDelete = view.findViewById(R.id.imgDeleteLSP);
            viewHolder.imgUpdate = view.findViewById(R.id.imgUpdateLSP);
            viewHolder.tvTen = view.findViewById(R.id.tvTenLSP);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final DonViTinh donViTinh = list.get(i);
        donViTinhDAO = new DonViTinhDAO(context);
        viewHolder.tvTen.setText(""+donViTinh.getDonViTinh());
        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder b = new AlertDialog.Builder(context);
                b.setTitle("X??c nh???n");
                b.setMessage("B???n c?? ?????ng ?? x??a h??a ????n n??y kh??ng?");
                b.setPositiveButton("?????ng ??", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        long chk = donViTinhDAO.deleteDonVi(donViTinh.getDonViTinh());
                        if(chk>0){
                            Toast.makeText(context,"X??a th??nh c??ng",Toast.LENGTH_SHORT).show();
                            list.remove(i);
                            notifyDataSetChanged();
                        }else {
                            Toast.makeText(context,"X??a kh??ng th??nh c??ng",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                b.setNegativeButton("Kh??ng ?????ng ??", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog al = b.create();
                al.show();

            }
        });
        viewHolder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                dialog.setContentView(R.layout.activity_sua_don_vi_tinh);
                dialog.show();
                edSua = dialog.findViewById(R.id.edSuaDonViTinh);
                edSua.setText(""+list.get(i).getDonViTinh());
                ImageView imgLuu = dialog.findViewById(R.id.imgLuuThayDoiDV);
                imgLuu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try{
                            donViTinhDAO.updateDonVi(edSua.getText().toString(),list.get(i).getDonViTinh());
                            Toast.makeText(context,"L??u th??nh c??ng",Toast.LENGTH_SHORT).show();
                            list.set(i,new DonViTinh(edSua.getText().toString()));
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }catch (Exception e){
                            Toast.makeText(context,"L??u th???t b???i, ????n V??? ???? t???n t???i",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        return view;
    }
    private static class ViewHolder{
        TextView tvTen;
        ImageView imgDelete,imgUpdate;
    }
}
