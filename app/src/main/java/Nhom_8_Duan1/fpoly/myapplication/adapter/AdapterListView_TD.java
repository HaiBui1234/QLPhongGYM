package Nhom_8_Duan1.fpoly.myapplication.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Nhom_8_Duan1.fpoly.myapplication.R;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.KT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.TD;
import Nhom_8_Duan1.fpoly.myapplication.csdl.Data_base.DatabaseGym;
import Nhom_8_Duan1.fpoly.myapplication.interfaces.InteLoadData;
import Nhom_8_Duan1.fpoly.myapplication.interfaces.IntelLoadData2;

public class AdapterListView_TD extends BaseAdapter {

    ArrayList<TD> list1;
    Context context;
    InteLoadData inteLoadData;
    IntelLoadData2 intelLoadData2;
    public AdapterListView_TD(Context context, InteLoadData inteLoadData) {
        this.context = context;
        this.inteLoadData = inteLoadData;
    }
    public AdapterListView_TD(Context context, IntelLoadData2 intelLoadData2) {
        this.context = context;
        this.intelLoadData2 = intelLoadData2;
    }

    public void setdata(ArrayList<TD> list) {
        this.list1 = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list1 != null) {
            return list1.size();
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
        TD td = list1.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_item_td, null);
            viewHolder.avata = convertView.findViewById(R.id.item_td_avata);
            viewHolder.name = convertView.findViewById(R.id.item_td_name);
            viewHolder.imged = convertView.findViewById(R.id.item_td_imged);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Bitmap bitmap= BitmapFactory.decodeByteArray(td.getImg_avatarTD(),0,td.getImg_avatarTD().length);
        viewHolder.avata.setImageBitmap(bitmap);
        viewHolder.name.setText(td.getName_TD());
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
                                dialog.setContentView(R.layout.dialog_td_update);
                                Button btnAddTD = dialog.findViewById(R.id.btnupdateTD);
                                Button btnHuyAddTD = dialog.findViewById(R.id.btnHuyupdateTD);
                                EditText edName_TD_update = dialog.findViewById(R.id.edName_td_update);
                                EditText edGhiChu_update = dialog.findViewById(R.id.edGhiChu_update_TD);
                                Spinner spUpadte=dialog.findViewById(R.id.spupdateTD);
                                ArrayList<KT> list= (ArrayList<KT>) DatabaseGym.getInstance(context).dao_kt().getAllKT1();
                                ArrayAdapter<KT> adapter=new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item,list);
                                spUpadte.setAdapter(adapter);
                                edName_TD_update.setText(td.getName_TD());
                                edGhiChu_update.setText(td.getGhi_Chu());
                                btnAddTD.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        td.setName_TD(edName_TD_update.getText().toString());
                                        td.setGhi_Chu(edGhiChu_update.getText().toString());
                                        KT kt= (KT) spUpadte.getSelectedItem();
                                        td.setId_KhoaTap(kt.getId_KhoaTap());
                                        DatabaseGym.getInstance(context).dao_td().updateTD(td);
                                        intelLoadData2.loadData2(td.getId_TD());
                                        dialog.dismiss();
                                        Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                btnHuyAddTD.setOnClickListener(new View.OnClickListener() {
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
                                        DatabaseGym.getInstance(context).dao_td().deleteTD(td);
                                        intelLoadData2.loadData2(td.getId_TD());
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
        TextView name;
    }
}
