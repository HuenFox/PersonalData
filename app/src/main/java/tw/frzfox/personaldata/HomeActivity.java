package tw.frzfox.personaldata;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeActivity extends AppCompatActivity implements DataAdapter.dataInterface {
    private DBHelper dbHelper;
    RecyclerView recyclerView;
    DataAdapter dataAdapter;
    EditText SelectEditText;
    Button SelectBtn;
    public static String NowLoginAccount = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        dbHelper = new DBHelper(this, "PersonalData.db", null, 1);
        recyclerView = findViewById(R.id.recyclerView);
        SelectEditText = findViewById(R.id.SelectEditText);
        SelectBtn = findViewById(R.id.SelectBtn);
        setRecyclerView();
        NowLoginAccount = getIntent().getStringExtra("Account");

        SelectBtn.setOnClickListener(view -> {
            String selectAccount = SelectEditText.getText().toString();
            if(selectAccount.isEmpty()){
                Toast.makeText(this, "列出全部資料", Toast.LENGTH_SHORT).show();
                setRecyclerView();
                return;
            }
            Toast.makeText(this, "搜尋帳號：" + selectAccount, Toast.LENGTH_SHORT).show();
            setRecyclerView(selectAccount);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRecyclerView();
    }

    public void setRecyclerView() {
        if (dbHelper.getData("") != null && !dbHelper.getData("").isEmpty()) {
            dataAdapter = new DataAdapter(dbHelper.getData(""), this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            //分割線
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            recyclerView.setAdapter(dataAdapter);
        }
    }

    public void setRecyclerView(String selectAccount) {
            dataAdapter = new DataAdapter(dbHelper.getData(selectAccount), this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            //分割線
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            recyclerView.setAdapter(dataAdapter);
    }

    @Override
    public void onItemClick(int id, Personal personal) {
        Intent intent = new Intent(this, UpdateActivity.class);
        intent.putExtra("Account", personal.getAccount());
        intent.putExtra("Password", personal.getPassword());
        intent.putExtra("Zodiac", personal.getZodiac());
        intent.putExtra("BloodType", personal.getBloodType());
        startActivity(intent);
    }

}
