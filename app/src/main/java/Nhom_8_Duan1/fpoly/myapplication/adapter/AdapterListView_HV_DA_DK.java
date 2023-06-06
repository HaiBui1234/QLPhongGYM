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
import android.widget.Toast;

import java.util.ArrayList;

import Nhom_8_Duan1.fpoly.myapplication.R;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.DK;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.HV;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.KT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.Data_base.DatabaseGym;


public class AdapterListView_HV_DA_DK extends BaseAdapter {

    ArrayList<DK> list = new ArrayList<>();
    Context context;
    public AdapterListView_HV_DA_DK(Context context) {
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
        DK dk = list.get(position);
        HV hv =  DatabaseGym.getInstance(context).dao_hv().getHVtheoId(dk.getId_HV()).get(0);
        KT kt = DatabaseGym.getInstance(context).dao_kt().getKTTheoID(dk.getId_KhoaTap()).get(0);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_hv_da_dki,null);
            viewHolder.avata = convertView.findViewById(R.id.item_dadk_avata);
            viewHolder.name = convertView.findViewById(R.id.item_tenhv_dk);
            viewHolder.kt = convertView.findViewById(R.id.item_tenkt_dk);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (hv.getAvatar_HV()!=null){
            Bitmap bitmap= BitmapFactory.decodeByteArray(hv.getAvatar_HV(),0,hv.getAvatar_HV().length);
            viewHolder.avata.setImageBitmap(bitmap);
        }
        viewHolder.avata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, kt.getName_KhoaTap()+"", Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.name.setText("tên học viên: "+hv.getName_HV());
        viewHolder.kt.setText("tên khóa tập: "+kt.getName_KhoaTap());
        return convertView;
    }
    public class ViewHolder {
        ImageView avata;
        TextView name, kt;
    }
}
