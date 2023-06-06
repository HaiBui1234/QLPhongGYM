package Nhom_8_Duan1.fpoly.myapplication.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

import Nhom_8_Duan1.fpoly.myapplication.R;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.DK;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.HV;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.KT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.PT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.Data_base.DatabaseGym;
import Nhom_8_Duan1.fpoly.myapplication.interfaces.InteLoadData;

public class AdapterListView_KT_Da_DK extends BaseAdapter {

    ArrayList<DK> list = new ArrayList<>();
    Context context;
    KT kt;
    DK dk;
    PT pt;

    public AdapterListView_KT_Da_DK(Context context) {
        this.context = context;
    }

    public void setdata(ArrayList<DK> list) {
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
        dk = list.get(position);
        kt = DatabaseGym.getInstance(context).dao_kt().getKTTheoID(dk.getId_KhoaTap()).get(0);
        pt = DatabaseGym.getInstance(context).dao_pt().getPTtheoID(dk.getId_PT()).get(0);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_item_kt_da_dki, null);
            viewHolder.avata = convertView.findViewById(R.id.item_kt_avata_da_dki);
            viewHolder.name = convertView.findViewById(R.id.item_kt_name_da_dki);
            viewHolder.name_pt = convertView.findViewById(R.id.item_kt_nam_pt_da_dki);
            viewHolder.trangThai = convertView.findViewById(R.id.item_kt_trangthai_dki);
            viewHolder.gia = convertView.findViewById(R.id.item_kt_gia_dki);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Bitmap bitmap= BitmapFactory.decodeByteArray(kt.getImg_avatarTD(),0,kt.getImg_avatarTD().length);
        viewHolder.avata.setImageBitmap(bitmap);
        viewHolder.name.setText(kt.getName_KhoaTap());
        viewHolder.name_pt.setText(pt.getName_PT());
        viewHolder.gia.setText(kt.getGiaKhoaTap()+" VND");
        if(dk.getThanhToan() == 0){
            viewHolder.trangThai.setTextColor(Color.RED);
            viewHolder.trangThai.setText("Chưa thanh toán");
        }
        else if(dk.getThanhToan() == 1){
            viewHolder.trangThai.setTextColor(Color.BLUE);
            viewHolder.trangThai.setText("Đã thanh toán");
        }
        return convertView;
    }

    public class ViewHolder {
        ImageView avata;
        TextView name, name_pt, trangThai,gia;
    }
}
