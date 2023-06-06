package Nhom_8_Duan1.fpoly.myapplication.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Nhom_8_Duan1.fpoly.myapplication.R;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.KT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.PT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.Data_base.DatabaseGym;
import Nhom_8_Duan1.fpoly.myapplication.interfaces.InteLoadData;
import Nhom_8_Duan1.fpoly.myapplication.interfaces.IntelLoadData2;

public class AdapterListView_KT extends BaseAdapter {

    ArrayList<KT> list = new ArrayList<>();
    Context context;
    EditText edName_KT_update, edDate_KT_update, edGia_KT_update;
    InteLoadData inteLoadData;
    IntelLoadData2 intelLoadData2;
    ListView listView;
    AdapterListView_KT adapterListView_kt;

    public AdapterListView_KT(Context context, InteLoadData inteLoadData) {
        this.context = context;
        this.inteLoadData = inteLoadData;
    }
    public AdapterListView_KT(Context context,IntelLoadData2 intelLoadData2) {
        this.context = context;
        this.intelLoadData2 = intelLoadData2;
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_item_kt, null);
            viewHolder.avata = convertView.findViewById(R.id.item_kt_avata);
            viewHolder.name = convertView.findViewById(R.id.item_kt_name);
            viewHolder.trangThai = convertView.findViewById(R.id.item_kt_trangthai);
            viewHolder.imged = convertView.findViewById(R.id.item_kt_imgtd);
            listView = convertView.findViewById(R.id.lis_KT);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Bitmap bitmap= BitmapFactory.decodeByteArray(kt.getImg_avatarTD(),0,kt.getImg_avatarTD().length);
        viewHolder.avata.setImageBitmap(bitmap);
        viewHolder.name.setText(kt.getName_KhoaTap());
        if(kt.getCapquyen() == 0){
            viewHolder.name.setTextColor(Color.RED);
            viewHolder.trangThai.setText("Chưa cấp quyền");
            viewHolder.trangThai.setTextColor(Color.RED);
        }
        else {
            viewHolder.name.setTextColor(Color.BLUE);
            viewHolder.trangThai.setText("Đã cấp quyền");
            viewHolder.trangThai.setTextColor(Color.BLUE);
        }
        ViewHolder finalViewHolder = viewHolder;
        viewHolder.imged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu=new PopupMenu(context, finalViewHolder.imged);
                menu.getMenuInflater().inflate(R.menu.menu_for_icon,menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
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
                                        intelLoadData2.loadData2(kt.getId_PT());
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
                                        intelLoadData2.loadData2(kt.getId_PT());

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
        TextView name, trangThai;
    }
}
