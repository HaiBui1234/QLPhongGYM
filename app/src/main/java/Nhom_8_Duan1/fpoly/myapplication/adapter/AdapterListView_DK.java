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
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Nhom_8_Duan1.fpoly.myapplication.R;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.DK;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.HV;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.KT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.PT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.Data_base.DatabaseGym;
import Nhom_8_Duan1.fpoly.myapplication.interfaces.InteLoadData;

public class AdapterListView_DK extends BaseAdapter {

    ArrayList<KT> list = new ArrayList<>();
    Context context;
    InteLoadData inteLoadData;
    KT kt;
    PT pt;
    TextView tv_info_name, tv_info_Date, tv_info_gia, tv_info_trangThai, before_trangThai_kt;

    public AdapterListView_DK(Context context) {
        this.context = context;
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
        kt = list.get(position);
        pt = DatabaseGym.getInstance(context).dao_pt().getPTtheoID(kt.getId_PT()).get(0);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_item_kt_dki, null);
            viewHolder.avata = convertView.findViewById(R.id.item_kt_avata_dki);
            viewHolder.name = convertView.findViewById(R.id.item_kt_name_dki);
            viewHolder.name_pt = convertView.findViewById(R.id.item_kt_nam_pt_dki);
            viewHolder.gia = convertView.findViewById(R.id.item_kt_gia_pt_dki);
            viewHolder.imged = convertView.findViewById(R.id.item_pt_op_dki_kt);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Bitmap bitmap= BitmapFactory.decodeByteArray(kt.getImg_avatarTD(),0,kt.getImg_avatarTD().length);
        viewHolder.avata.setImageBitmap(bitmap);
        viewHolder.name.setText(kt.getName_KhoaTap());
        viewHolder.name_pt.setText(pt.getName_PT());
        viewHolder.gia.setText(kt.getGiaKhoaTap()+" VND");
        ViewHolder finalViewHolder = viewHolder;
        viewHolder.imged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu=new PopupMenu(context, finalViewHolder.imged);
                menu.getMenuInflater().inflate(R.menu.menu_for_dki_kt,menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.menuChiTiet:
                                Dialog dialog = new Dialog(context);
                                dialog.setContentView(R.layout.dialog_show_info_kt);
                                tv_info_name = dialog.findViewById(R.id.tv_info_nameKT);
                                tv_info_Date = dialog.findViewById(R.id.tv_info_dateKT);
                                tv_info_gia = dialog.findViewById(R.id.tv_info_giaKT);
                                tv_info_trangThai = dialog.findViewById(R.id.tv_info_trangThai_KT);
                                tv_info_trangThai = dialog.findViewById(R.id.tv_info_trangThai_KT);
                                before_trangThai_kt = dialog.findViewById(R.id.before_trangthai_kt);
                                tv_info_name.setText(kt.getName_KhoaTap());
                                tv_info_Date.setText(String.valueOf(kt.getSoNgayTap()));
                                tv_info_gia.setText(String.valueOf(kt.getGiaKhoaTap()));
                                before_trangThai_kt.setText("");
                                tv_info_trangThai.setText("");
                                dialog.show();
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
        TextView name, name_pt,gia;
    }
}
