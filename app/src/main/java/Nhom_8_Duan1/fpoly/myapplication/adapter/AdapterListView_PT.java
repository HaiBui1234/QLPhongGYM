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
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.PT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.Data_base.DatabaseGym;
import Nhom_8_Duan1.fpoly.myapplication.interfaces.InteLoadData;

public class AdapterListView_PT extends BaseAdapter {

    ArrayList<PT> list = new ArrayList<>();
    Context context;
    InteLoadData intePT;
    public AdapterListView_PT(Context context, InteLoadData intePT) {
        this.context = context;
        this.intePT=intePT;
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
        PT pt = list.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_item_pt, null);
            viewHolder.avata = convertView.findViewById(R.id.item_pt_avata);
            viewHolder.name = convertView.findViewById(R.id.item_pt_name);
            viewHolder.email = convertView.findViewById(R.id.item_pt_email);
            viewHolder.imged = convertView.findViewById(R.id.item_pt_imgtd);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (pt.getAvatar_PT()!=null){
            Bitmap bitmap= BitmapFactory.decodeByteArray(pt.getAvatar_PT(),0,pt.getAvatar_PT().length);
            viewHolder.avata.setImageBitmap(bitmap);
        }else {
        }
        viewHolder.name.setText(pt.getName_PT());
        viewHolder.email.setText(pt.getEmail_PT());
        ViewHolder finalViewHolder = viewHolder;
        viewHolder.imged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(context, finalViewHolder.imged);
                popupMenu.getMenuInflater().inflate(R.menu.menu_for_icon,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()){
                            case R.id.menusua:
                                Dialog dialogEdit = new Dialog(context);
                                dialogEdit.setContentView(R.layout.dialog_pt_edit);
                                Button btnEditPT = dialogEdit.findViewById(R.id.btnedit_PT);
                                Button btnHuyEditPT = dialogEdit.findViewById(R.id.btnHuyEditPT);
                                EditText edUser_PT_edit = dialogEdit.findViewById(R.id.edUserName_PT_edit);
                                EditText edName_PT_edit = dialogEdit.findViewById(R.id.edName_PT_edit);
                                EditText edPass_PT_edit = dialogEdit.findViewById(R.id.edPass_PT_edit);
                                 edUser_PT_edit.setText(pt.getUsername_PT());
                                 edName_PT_edit.setText(pt.getName_PT());
                                edPass_PT_edit.setText(pt.getPass_PT());
                                btnEditPT.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        pt.setUsername_PT(edUser_PT_edit.getText().toString());
                                        pt.setName_PT(edName_PT_edit.getText().toString());
                                        pt.setPass_PT(edPass_PT_edit.getText().toString());
                                        DatabaseGym.getInstance(context).dao_pt().updataPT(pt);
                                        intePT.loadData();
                                        Toast.makeText(context, "Đã sửa thành công!!!", Toast.LENGTH_SHORT).show();
                                        dialogEdit.dismiss();
                                    }
                                });
                                btnHuyEditPT.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialogEdit.dismiss();
                                    }
                                });
                                dialogEdit.show();
                                Toast.makeText(context, " sửa", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.menuxoa:
                                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                                builder.setTitle("DELETE");
                                builder.setMessage("Do you want delete ?");
                                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        DatabaseGym.getInstance(context).dao_pt().deletePT(pt);
                                        Toast.makeText((context), "Đã xóa", Toast.LENGTH_SHORT).show();
                                        intePT.loadData();
                                        Toast.makeText(context, " xóa", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                builder.setNegativeButton("NO",null);
                                builder.show();
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        return convertView;
    }

    public class ViewHolder {
        ImageView avata,imged;
        TextView name, email;
    }
}
