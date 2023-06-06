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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Nhom_8_Duan1.fpoly.myapplication.R;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.HV;
import Nhom_8_Duan1.fpoly.myapplication.csdl.Data_base.DatabaseGym;
import Nhom_8_Duan1.fpoly.myapplication.interfaces.InteLoadData;

public class AdapterListView_HV extends BaseAdapter {

    ArrayList<HV> list = new ArrayList<>();
    Context context;
    InteLoadData inteloadData;
    public AdapterListView_HV(Context context,InteLoadData inteloadData) {
        this.context = context;
        this.inteloadData=inteloadData;
    }

    public void setdata(ArrayList<HV> list) {
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
        HV hv = list.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_item_hv, null);
            viewHolder.avata = convertView.findViewById(R.id.item_hv_avata);
            viewHolder.name = convertView.findViewById(R.id.item_hv_name);
            viewHolder.email = convertView.findViewById(R.id.item_hv_email);
            viewHolder.imged = convertView.findViewById(R.id.item_hv_imgtd);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (hv.getAvatar_HV()!=null){
            Bitmap bitmap= BitmapFactory.decodeByteArray(hv.getAvatar_HV(),0,hv.getAvatar_HV().length);
            viewHolder.avata.setImageBitmap(bitmap);
        }else {
        }
//        viewHolder.avata.setImageResource(R.drawable.img_avatar_main);
        viewHolder.name.setText(hv.getName_HV());
        viewHolder.email.setText(hv.getEmail_HV());
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
                                Dialog dialogEdit = new Dialog(context);
                                dialogEdit.setContentView(R.layout.dialog_hv_edit);
                                Button btnEditHV = dialogEdit.findViewById(R.id.btnedit_HV);
                                Button btnHuyEditHV = dialogEdit.findViewById(R.id.btnHuyEditHV);
                                EditText edUser_HV_edit = dialogEdit.findViewById(R.id.edUserName_HV_edit);
                                EditText edName_HV_edit = dialogEdit.findViewById(R.id.edName_HV_edit);
                                EditText edPass_HV_edit = dialogEdit.findViewById(R.id.edPass_HV_edit);
                                edUser_HV_edit.setText(hv.getUsername_HV());
                                edName_HV_edit.setText(hv.getName_HV());
                                edPass_HV_edit.setText(hv.getPass_HV());
                                btnEditHV.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        hv.setUsername_HV(edUser_HV_edit.getText().toString());
                                        hv.setName_HV(edName_HV_edit.getText().toString());
                                        hv.setPass_HV(edPass_HV_edit.getText().toString());
                                        DatabaseGym.getInstance(context).dao_hv().updataHV(hv);
                                        inteloadData.loadData();
                                        Toast.makeText(context, "Đã sửa thành công!!!", Toast.LENGTH_SHORT).show();
                                        dialogEdit.dismiss();
                                    }
                                });
                                btnHuyEditHV.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialogEdit.dismiss();
                                    }
                                });
                                dialogEdit.show();
                                break;
                            case R.id.menuxoa:
                                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                                builder.setTitle("DELETE");
                                builder.setMessage("Do you want delete?");
                                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        DatabaseGym.getInstance(context).dao_hv().deleteHV(hv);
                                        Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show();
                                        inteloadData.loadData();
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
        TextView name, email;
    }
}
