package tw.frzfox.personaldata;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText AccountEditText;
    EditText PwdEditText;
    Button LoginBtn, RegisterBtn;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(getApplicationContext(), "PersonalData.db", null, 1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AccountEditText = findViewById(R.id.AccountEditText);
        PwdEditText = findViewById(R.id.PwdEditText);

        LoginBtn = findViewById(R.id.LoginBtn);
        LoginBtn.setOnClickListener(view -> {
            String Account = AccountEditText.getText().toString();
            String Pwd = PwdEditText.getText().toString();
            Login(Account, Pwd);
        });

        RegisterBtn = findViewById(R.id.RegisterBtn);
        RegisterBtn.setOnClickListener(view -> {
            ResisterPopupWindow popupWindow = new ResisterPopupWindow(MainActivity.this);
            View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.register_popupwindows, null);
            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        });

    }

    private Boolean Login(String Account, String Pwd) {
        ArrayList<String> selectArray = dbHelper.getPwd(Account);

        //沒有找到帳號
        if(selectArray == null || selectArray.isEmpty()){
            Toast.makeText(this, "帳號或密碼錯誤", Toast.LENGTH_SHORT).show();
            return false;
        }

        String selectPwd = selectArray.get(0).toString();
        if(selectPwd.equals(Pwd)) {
            //密碼通過，登入
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.putExtra("Account", Account);
            startActivity(intent);
            return true;
        }

        Toast.makeText(this, "帳號或密碼錯誤", Toast.LENGTH_SHORT).show();
        return false;
    }

    public class ResisterPopupWindow extends PopupWindow implements View.OnClickListener {
        View view;
        EditText RegAccountEditText, RegPwdEditText, RegPwdCheckEditText, RegZodiacEditText, RegBloodTypeEditText;
        Button startResisterBtn, CancelResisterBtn;

        public ResisterPopupWindow(Context mContext) {
            this.view = LayoutInflater.from(mContext).inflate(R.layout.register_popupwindows, null);

            RegAccountEditText = view.findViewById(R.id.RegAccountEditText);
            RegPwdEditText = view.findViewById(R.id.RegPwdEditText);
            RegPwdCheckEditText = view.findViewById(R.id.RegPwdCheckEditText);
            RegZodiacEditText = view.findViewById(R.id.RegZodiacEditText);
            RegBloodTypeEditText = view.findViewById(R.id.RegBloodTypeEditText);

            startResisterBtn = view.findViewById(R.id.StartResisterBtn);
            CancelResisterBtn = view.findViewById(R.id.CancelResisterBtn);

            this.setOutsideTouchable(true);
            this.view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int height = view.findViewById(R.id.popLayout).getTop();
                    int y = (int) event.getY();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (y < height) {
                            dismiss();
                        }
                    }
                    return true;
                }
            });

            this.setContentView(this.view);
            this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
            this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            this.setFocusable(true);
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            this.setBackgroundDrawable(dw);
            startResisterBtn.setOnClickListener(this);
            CancelResisterBtn.setOnClickListener(this);
        }

        public void onClick(View V) {
            int id = V.getId();
            if (id == R.id.StartResisterBtn) {
                //確認密碼不一樣
                if(!RegPwdEditText.getText().toString().equals(RegPwdCheckEditText.getText().toString())){
                    Toast.makeText(getApplicationContext(), "確認輸入密碼不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                DBHelper dbHelper = new DBHelper(getApplicationContext(), "PersonalData.db", null, 1);
                dbHelper.addData(RegAccountEditText.getText().toString(), RegPwdEditText.getText().toString(), RegZodiacEditText.getText().toString(),RegBloodTypeEditText.getText().toString());
                Toast.makeText(getApplicationContext(), "註冊成功", Toast.LENGTH_SHORT).show();
                this.dismiss();
            }
            if (id == R.id.CancelResisterBtn) {
                this.dismiss();
            }
        }
    }
}
