package Nhom_8_Duan1.fpoly.myapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import Nhom_8_Duan1.fpoly.myapplication.R;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.HV;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.TT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.Data_base.DatabaseGym;

public class AdapterListView_TT extends BaseAdapter {

    ArrayList<TT> list = new ArrayList<>();
    Context context;
    TT tt;
    HV hv;
    public AdapterListView_TT(Context context) {
        this.context = context;
    }

    public void setdata(ArrayList<TT> list) {
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
        tt = list.get(position);
        hv = DatabaseGym.getInstance(context).dao_hv().getHVtheoId(tt.getId_HV()).get(0);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_item_tt, null);
            viewHolder.avata = convertView.findViewById(R.id.item_tt_avata);
            viewHolder.name = convertView.findViewById(R.id.item_tt_name);
            viewHolder.tien = convertView.findViewById(R.id.item_tt_tientt);
            viewHolder.time = convertView.findViewById(R.id.item_tt_timett);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Bitmap bitmap= BitmapFactory.decodeByteArray(tt.getImg_Naptien(),0,tt.getImg_Naptien().length);
        viewHolder.avata.setImageBitmap(bitmap);
        viewHolder.name.setText(hv.getName_HV());
        viewHolder.tien.setText(tt.getTien_TT()+" VNĐ");
        viewHolder.time.setText(tt.getTime() + "");
        return convertView;
    }

    public class ViewHolder {
        ImageView avata, imgtt;
        TextView name, tien, time;
    }
}
