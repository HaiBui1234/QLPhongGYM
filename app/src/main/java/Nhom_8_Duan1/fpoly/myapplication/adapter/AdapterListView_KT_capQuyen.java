package Nhom_8_Duan1.fpoly.myapplication.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import Nhom_8_Duan1.fpoly.myapplication.R;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.KT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.PT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.Data_base.DatabaseGym;
import Nhom_8_Duan1.fpoly.myapplication.fragment.QuanLyKhoaTapFragment;
import Nhom_8_Duan1.fpoly.myapplication.interfaces.InteLoadData;

public class AdapterListView_KT_capQuyen extends BaseAdapter {

    ArrayList<KT> list = new ArrayList<>();
    Context context;
    InteLoadData inteLoadData;
    EditText edName_KT_update, edDate_KT_update, edGia_KT_update;
    PopupMenu menu;
    PT pt;
    public AdapterListView_KT_capQuyen(Context context, InteLoadData inteLoadData) {
        this.context = context;
        this.inteLoadData = inteLoadData;
    }

    public void setdata(ArrayList<KT> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        KT kt = list.get(position);
        pt = DatabaseGym.getInstance(context).dao_pt().getPTtheoID(kt.getId_PT()).get(0);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_item_kt_capquyen, null);
            viewHolder.avata = convertView.findViewById(R.id.item_kt_avata_cq);
            viewHolder.name = convertView.findViewById(R.id.item_kt_name_cq);
            viewHolder.trangThai = convertView.findViewById(R.id.item_kt_trangthai_cq);
            viewHolder.name_pt = convertView.findViewById(R.id.item_kt_nam_pt_cq);
            viewHolder.imged = convertView.findViewById(R.id.item_kt_imgtd_cq);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Bitmap bitmap= BitmapFactory.decodeByteArray(kt.getImg_avatarTD(),0,kt.getImg_avatarTD().length);
        viewHolder.avata.setImageBitmap(bitmap);
        viewHolder.name.setText(kt.getName_KhoaTap());
        viewHolder.name_pt.setText(pt.getName_PT());
        ViewHolder finalViewHolder = viewHolder;
        menu = new PopupMenu(context, finalViewHolder.imged);
        menu.getMenuInflater().inflate(R.menu.menu_for_capquyen,menu.getMenu());
        if(kt.getCapquyen() == 0){
            viewHolder.name.setTextColor(Color.RED);
            viewHolder.trangThai.setText("Chưa cấp quyền");
            viewHolder.trangThai.setTextColor(Color.RED);
            viewHolder.name_pt.setTextColor(Color.RED);
            menu.getMenu().findItem(R.id.menuHuyCapQuyen).setVisible(false);
        }
        else {
            viewHolder.name.setTextColor(Color.BLUE);
            viewHolder.trangThai.setText("Đã cấp quyền");
            viewHolder.trangThai.setTextColor(Color.BLUE);
            viewHolder.name_pt.setTextColor(Color.BLUE);
            menu.getMenu().findItem(R.id.menuCapQuyen).setVisible(false);
        }
        viewHolder.imged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.menuCapQuyen:
                                AlertDialog.Builder builder_capQuyen = new AlertDialog.Builder(context);
                                builder_capQuyen.setTitle("Cấp quyền");
                                builder_capQuyen.setMessage("Bạn muốn cấp quyền cho khóa tập này ?");
                                builder_capQuyen.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int a) {
                                        kt.setCapquyen(1);
                                        DatabaseGym.getInstance(builder_capQuyen.getContext()).dao_kt().updateKT(kt);
                                        inteLoadData.loadData();
                                        Toast.makeText(context, "Thành công.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                builder_capQuyen.setNegativeButton("NO",null);
                                builder_capQuyen.show();
                                break;
                            case R.id.menuHuyCapQuyen:
                                AlertDialog.Builder builder_HuycapQuyen = new AlertDialog.Builder(context);
                                builder_HuycapQuyen.setTitle("Hủy quyền");
                                builder_HuycapQuyen.setMessage("Bạn muốn hủy quyền cho khóa tập này ?");
                                builder_HuycapQuyen.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int a) {
                                        kt.setCapquyen(0);
                                        DatabaseGym.getInstance(builder_HuycapQuyen.getContext()).dao_kt().updateKT(kt);
                                        inteLoadData.loadData();
                                        Toast.makeText(context, "Thành công.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                builder_HuycapQuyen.setNegativeButton("NO",null);
                                builder_HuycapQuyen.show();
                                break;
                            case R.id.menusua:
                                Dialog dialog=new Dialog(context);
                                dialog.setContentView(R.layout.dialog_kt_update);
                                Button btnAddKT = dialog.findViewById(R.id.btnupdateKT);
                                Button btnHuyAddKT = dialog.findViewById(R.id.btnHuyupdateKT);
                                edName_KT_update = dialog.findViewById(R.id.edName_kt_update);
                                edDate_KT_update = dialog.findViewById(R.id.edDate_update_KT);
                                edGia_KT_update = dialog.findViewById(R.id.edGia_update_KT);
                                edName_KT_update.setText(kt.getName_KhoaTap());
                                edDate_KT_update.setText(String.valueOf(kt.getSoNgayTap()));
                                edGia_KT_update.setText(String.valueOf(kt.getGiaKhoaTap()));
                                btnAddKT.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        kt.setName_KhoaTap(edName_KT_update.getText().toString());
                                        kt.setSoNgayTap(Integer.parseInt(edDate_KT_update.getText().toString()));
                                        kt.setGiaKhoaTap(Integer.parseInt(edGia_KT_update.getText().toString()));
                                        DatabaseGym.getInstance(context).dao_kt().updateKT(kt);
                                        inteLoadData.loadData();
                                        dialog.dismiss();
                                        Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                btnHuyAddKT.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                                break;
                            case R.id.menuxoa:
                                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                                builder.setTitle("DELETE");
                                builder.setMessage("Bn muốn xóa ?");
                                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int a) {
                                        DatabaseGym.getInstance(context).dao_kt().deleteKT(kt);
                                        inteLoadData.loadData();
                                        Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                builder.setNegativeButton("NO",null);
                                builder.show();
                                break;
                        }
                        return true;
                    }
                });
                menu.show();
            }
        });
        return convertView;
    }

    public class ViewHolder {
        ImageView avata,imged;
        TextView name, trangThai, name_pt;
    }
}
