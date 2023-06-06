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

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

import Nhom_8_Duan1.fpoly.myapplication.R;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.BT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.KT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.Data_base.DatabaseGym;
import Nhom_8_Duan1.fpoly.myapplication.interfaces.InteLoadData;
import Nhom_8_Duan1.fpoly.myapplication.interfaces.IntelLoadData2;

public class AdapterListView_BT extends BaseAdapter {

    ArrayList<BT> list1;
    Context context;
    IntelLoadData2 intelLoadData2;
    InteLoadData inteLoadData;

    public AdapterListView_BT(Context context, IntelLoadData2 intelLoadData2, InteLoadData inteLoadData) {
        this.context = context;
        this.intelLoadData2 = intelLoadData2;
        this.inteLoadData = inteLoadData;
    }

    public AdapterListView_BT(Context context, IntelLoadData2 loadData2) {
        this.context = context;
        this.intelLoadData2 = loadData2;
    }

    public void setdata(ArrayList<BT> list) {
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
        BT bt = list1.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_item_bt, null);
            viewHolder.avata = convertView.findViewById(R.id.item_bt_avata);
            viewHolder.name = convertView.findViewById(R.id.item_bt_name);
            viewHolder.imged = convertView.findViewById(R.id.item_bt_imgtd);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Bitmap bitmap= BitmapFactory.decodeByteArray(bt.getImg_Avartar(),0,bt.getImg_Avartar().length);
        viewHolder.avata.setImageBitmap(bitmap);
        viewHolder.name.setText(bt.getName_BT());
        ViewHolder finalViewHolder = viewHolder;
        viewHolder.imged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(context, finalViewHolder.imged);
                menu.getMenuInflater().inflate(R.menu.menu_for_icon, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.menusua:
                                Dialog dialog = new Dialog(context);
                                dialog.setContentView(R.layout.dialog_bt_update);
                                Button btnAddBT = dialog.findViewById(R.id.btnupdateBT);
                                Button btnHuyAddBT = dialog.findViewById(R.id.btnHuyupdateBT);
                                EditText edName_BT_update = dialog.findViewById(R.id.edName_bt_update);
                                EditText edGhiChu_update = dialog.findViewById(R.id.edGhiChu_update);
                                Spinner spUpdateKT=dialog.findViewById(R.id.spUpdateKTBT);
                                ArrayList<KT> list;
                                list= (ArrayList<KT>) DatabaseGym.getInstance(context).dao_kt().getAllKT1();
                                ArrayAdapter<KT> adapter=new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item,list);
                                spUpdateKT.setAdapter(adapter);
                                edName_BT_update.setText(bt.getName_BT());
                                edGhiChu_update.setText(bt.getGhi_Chu());
                                btnAddBT.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        bt.setName_BT(edName_BT_update.getText().toString());
                                        bt.setGhi_Chu(edGhiChu_update.getText().toString());
                                        KT kt= (KT) spUpdateKT.getSelectedItem();
                                        bt.setId_KhoaTap(kt.getId_KhoaTap());
                                        DatabaseGym.getInstance(context).dao_bt().updateBT(bt);
                                        intelLoadData2.loadData2(bt.getId_BT());
                                        dialog.dismiss();
                                        Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                btnHuyAddBT.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                                break;
                            case R.id.menuxoa:
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("DELETE");
                                builder.setMessage("Bn muốn xóa ?");
                                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int a) {
                                        DatabaseGym.getInstance(context).dao_bt().deleteBT(bt);
                                        intelLoadData2.loadData2(bt.getId_BT());
                                        Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                builder.setNegativeButton("NO", null);
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
        ImageView avata, imged;
        TextView name, email;
    }
}
