package tw.frzfox.personaldata;

import static tw.frzfox.personaldata.HomeActivity.NowLoginAccount;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {
    LinearLayout PwdLayout, PwdcheckLayout;
    EditText UpdateAccountEditText, UpdatePwdEditText, UpdatePwdCheckEditText, UpdateZodiacEditText, UpdateBloodTypeEditText;
    Button StartUpdateBtn, CancelUpdateBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        PwdLayout = findViewById(R.id.PwdLayout);
        PwdcheckLayout = findViewById(R.id.PwdcheckLayout);

        UpdateAccountEditText = findViewById(R.id.UpdateAccountEditText);
        UpdatePwdEditText = findViewById(R.id.UpdatePwdEditText);
        UpdatePwdCheckEditText = findViewById(R.id.UpdatePwdCheckEditText);
        UpdateZodiacEditText = findViewById(R.id.UpdateZodiacEditText);
        UpdateBloodTypeEditText = findViewById(R.id.UpdateBloodTypeEditText);

        Bundle extras = getIntent().getExtras();
        UpdateAccountEditText.setText(extras.getString("Account"));
        UpdateZodiacEditText.setText(extras.getString("Zodiac"));
        UpdateBloodTypeEditText.setText(extras.getString("BloodType"));

        if(NowLoginAccount.equals(extras.getString("Account"))){
            PwdLayout.setVisibility(View.VISIBLE);
            PwdcheckLayout.setVisibility(View.VISIBLE);
        }else{
            PwdLayout.setVisibility(View.GONE);
            PwdcheckLayout.setVisibility(View.GONE);
        }


        StartUpdateBtn = findViewById(R.id.StartUpdateBtn);
        CancelUpdateBtn = findViewById(R.id.CancelUpdateBtn);

        StartUpdateBtn.setOnClickListener(view -> {
            String Account = UpdateAccountEditText.getText().toString();
            String Pwd = UpdatePwdEditText.getText().toString();
            String PwdCheck = UpdatePwdCheckEditText.getText().toString();
            String Zodiac = UpdateZodiacEditText.getText().toString();
            String BloodType = UpdateBloodTypeEditText.getText().toString();
            if(Account.equals("") || Zodiac.equals("") || BloodType.equals("")){
                Toast.makeText(getApplicationContext(), "請輸入完整資料", Toast.LENGTH_SHORT).show();
                return;
            }
            if(PwdLayout.getVisibility() == View.VISIBLE){
                if(Pwd.equals("") || PwdCheck.equals("") || !Pwd.equals(PwdCheck)){
                    Toast.makeText(getApplicationContext(), "確認密碼不一致、或密碼未輸入", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if(Pwd.equals(PwdCheck)){
                DBHelper dbHelper = new DBHelper(getApplicationContext(), "PersonalData.db", null, 1);
                if(PwdLayout.getVisibility() == View.VISIBLE){
                    dbHelper.updateData(Account, Pwd, Zodiac, BloodType);
                }else{
                    dbHelper.updateData(Account, "", Zodiac, BloodType);
                }
            }

            finish();
        });

        CancelUpdateBtn.setOnClickListener(view -> {
            finish();
        });
    }
}
