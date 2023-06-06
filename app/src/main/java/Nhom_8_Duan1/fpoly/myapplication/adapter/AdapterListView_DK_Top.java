package Nhom_8_Duan1.fpoly.myapplication.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import Nhom_8_Duan1.fpoly.myapplication.R;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.DK;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.HV;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.KT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.PT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.Data_base.DatabaseGym;
import Nhom_8_Duan1.fpoly.myapplication.interfaces.InteLoadData;

public class AdapterListView_DK_Top extends BaseAdapter {

    ArrayList<PT> list = new ArrayList<>();
    Context context;
    InteLoadData inteLoadData;
    PT pt;
    PT pt1;
    PT pt2;
    PT pt3;
    TextView tv_info_name, tv_info_Date, tv_info_gia, tv_info_trangThai, before_trangThai_kt;

    public AdapterListView_DK_Top(Context context, InteLoadData inteLoadData) {
        this.context = context;
        this.inteLoadData = inteLoadData;
    }

    public void setdata(ArrayList<PT> list) {
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
        pt = list.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_item_pt_top, null);
            viewHolder.avata = convertView.findViewById(R.id.item_pt_top_avata);
            viewHolder.name = convertView.findViewById(R.id.item_pt_top_name);
            viewHolder.name_pt = convertView.findViewById(R.id.item_pt_top_email);
            viewHolder.soLuong = convertView.findViewById(R.id.item_pt_top_soLuot);
            viewHolder.rlTop = convertView.findViewById(R.id.rlTop);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Bitmap bitmap= BitmapFactory.decodeByteArray(pt.getAvatar_PT(),0,pt.getAvatar_PT().length);
        viewHolder.avata.setImageBitmap(bitmap);
        viewHolder.name.setText(pt.getName_PT());
        viewHolder.name_pt.setText(pt.getEmail_PT());
        int soLuong = DatabaseGym.getInstance(context).dao_pt().getPTTopSoLuong(pt.getId_PT());
        viewHolder.soLuong.setText(soLuong + "");
        if(list.size() >= 3){
            if(pt == list.get(0)){
                viewHolder.rlTop.setBackgroundColor(R.color.mau_nen);
            }
            if(pt == list.get(1)){
                viewHolder.rlTop.setBackgroundColor(R.color.teal_200);
            }
            if(pt == list.get(2)){
                viewHolder.rlTop.setBackgroundColor(android.R.color.holo_green_dark);
            }
        }
        return convertView;
    }

    public class ViewHolder {
        ImageView avata;
        TextView name, name_pt, soLuong;
        RelativeLayout rlTop;
    }
}
