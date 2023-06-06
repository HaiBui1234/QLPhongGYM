package Nhom_8_Duan1.fpoly.myapplication.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import Nhom_8_Duan1.fpoly.myapplication.R;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.HV;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.PT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.Data_base.DatabaseGym;

public class CaiDatFragment extends Fragment {

    TextView doimk, doimp;
    ArrayList<PT> listPT = new ArrayList<>();
    ArrayList<HV> listHV = new ArrayList<>();
    PT pt;
    HV hv;

    public CaiDatFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CaiDatFragment newInstance() {
        CaiDatFragment fragment = new CaiDatFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cai_dat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        savedInstanceState = getActivity().getIntent().getExtras();
        String permission = savedInstanceState.getString("value");
        String userPT = savedInstanceState.getString("userPT");
        String userHV = savedInstanceState.getString("userHV");

        //ánh xạ biến
        doimk = view.findViewById(R.id.tv_doimk);
        doimp = view.findViewById(R.id.tv_doimapin);
        //lấy dữ liệu
        //===================== truy suất đối tượng từ id
        listHV = (ArrayList<HV>) DatabaseGym.getInstance(getActivity()).dao_hv().getAllHV();
        listPT = (ArrayList<PT>) DatabaseGym.getInstance(getActivity()).dao_pt().getAllPT();
        //bắt sự kiện
        doimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_changge_pass);
                TextInputEditText old_pass = dialog.findViewById(R.id.edOldPass_changge);
                TextInputEditText mk_moi = dialog.findViewById(R.id.edNewPass_changge);
                TextInputEditText chek_mkmoi = dialog.findViewById(R.id.edReNewPass_changge);
                Button btndoi = dialog.findViewById(R.id.btnSwapPass_changge);
                Button btnhuy = dialog.findViewById(R.id.btnEndSwapPass_changge);
                btndoi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chek_mkmoi.setTextColor(Color.BLACK);
                        if (permission.equalsIgnoreCase("PT")) {
                            pt = DatabaseGym.getInstance(getActivity()).dao_pt().getPTtheoUser(userPT).get(0);
                            String oldPass = old_pass.getText().toString();
                            String newPass = mk_moi.getText().toString();
                            String ckmk_moi = chek_mkmoi.getText().toString();
                            if (oldPass.equals(pt.getPass_PT())) {
                                if (!newPass.equals(ckmk_moi)) {
                                    chek_mkmoi.setTextColor(Color.RED);
                                    Toast.makeText(getActivity(), "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                pt.setPass_PT(newPass);
                                DatabaseGym.getInstance(getActivity()).dao_pt().updataPT(pt);
                                Toast.makeText(getActivity(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getActivity(), "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                            }
                        } else if (permission.equalsIgnoreCase("Học viên")) {
                            hv = DatabaseGym.getInstance(getActivity()).dao_hv().getHVtheoUser(userHV).get(0);
                            String oldPass = old_pass.getText().toString();
                            String newPass = mk_moi.getText().toString();
                            if (oldPass.equals(hv.getPass_HV())) {
                                hv.setPass_HV(newPass);
                                DatabaseGym.getInstance(getActivity()).dao_hv().updataHV(hv);
                                Toast.makeText(getActivity(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getActivity(), "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                btnhuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        doimp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_doi_mapin);
                EditText edOldMaPin = dialog.findViewById(R.id.edPin_forget);
                EditText edNewMapin = dialog.findViewById(R.id.edRePin_forget);
                Button btnDoi=dialog.findViewById(R.id.btnSwapPass_forget);
                Button btnHuy=dialog.findViewById(R.id.btnEndSwapPass_forget);
                btnDoi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (permission.equalsIgnoreCase("PT")) {
                            pt = DatabaseGym.getInstance(getActivity()).dao_pt().getPTtheoUser(userPT).get(0);
                            String oldMapin=edOldMaPin.getText().toString().trim();
                            int newMaPin= Integer.parseInt(edNewMapin.getText().toString());
                            if (oldMapin.equals(String.valueOf(pt.getMaPin_PT()))) {
                                pt.setMaPin_PT(newMaPin);
                                DatabaseGym.getInstance(getActivity()).dao_pt().updataPT(pt);
                                pt = DatabaseGym.getInstance(getActivity()).dao_pt().getPTtheoUser(userPT).get(0);
                                Log.d("TAG", "MÃ PIN :"+pt.getMaPin_PT());
                                Toast.makeText(getActivity(), "Đổi ma pin thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getActivity(), "Mật ma pin không đúng", Toast.LENGTH_SHORT).show();
                            }
                        } else if (permission.equalsIgnoreCase("Học viên")) {
                            hv = DatabaseGym.getInstance(getActivity()).dao_hv().getHVtheoUser(userHV).get(0);
                            String oldMapin=edOldMaPin.getText().toString().trim();
                            int newMaPin= Integer.parseInt(edNewMapin.getText().toString());
                            if (oldMapin.equals(String.valueOf(hv.getMaPin_HV()))) {
                                hv.setMaPin_HV(newMaPin);
                                DatabaseGym.getInstance(getActivity()).dao_hv().updataHV(hv);
                                hv = DatabaseGym.getInstance(getActivity()).dao_hv().getHVtheoUser(userHV).get(0);
                                Log.d("TAG", "MÃ PIN :"+hv.getMaPin_HV());
                                Toast.makeText(getActivity(), "Đổi ma pin thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getActivity(), "Mật ma pin không đúng", Toast.LENGTH_SHORT).show();
                            }
                        }


                    }
                });
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
}