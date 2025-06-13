package tw.frzfox.personaldata;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyHolder> {
    ArrayList<Personal> personalArray;//宣告資料
    dataInterface Interface;

    public DataAdapter(ArrayList<Personal> personalArray, dataInterface Interface) {
        //取得List內容
        this.personalArray = personalArray;
        this.Interface = Interface;
    }

    //將原本RecyclerView.ViewHolder的部分皆改為MyHolder
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
        return new MyHolder(view);//連接布局，新增一個view給viewholder綁定元件
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.itemAccount.setText(personalArray.get(position).getAccount());
        holder.itemZodiac.setText(personalArray.get(position).getZodiac());
        holder.itemBloodType.setText(personalArray.get(position).getBloodType());
        holder.itemCreateTime.setText(MillisStringToDate(personalArray.get(position).getCreateTime()));
        holder.itemUpdateTime.setText(MillisStringToDate(personalArray.get(position).getUpdateTime()));

    }

    public String MillisStringToDate(String millis){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(Long.parseLong(millis));
    }

    @Override
    public int getItemCount() {
        return personalArray.size();    //回傳List大小
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView itemAccount, itemZodiac, itemBloodType, itemCreateTime, itemUpdateTime;

        public MyHolder(View Holder) {
            super(Holder);
            Holder.setOnClickListener(view -> {
                Interface.onItemClick(getAdapterPosition(), personalArray.get(getAdapterPosition()));
                Log.d("AdapterPosition", "" + getAdapterPosition());
            });

            Holder.setOnLongClickListener(view -> {
                Interface.onItemLongClick(getAdapterPosition(), personalArray.get(getAdapterPosition()));
                return false;
            });

            itemAccount = Holder.findViewById(R.id.itemAccount);
            itemZodiac = Holder.findViewById(R.id.itemZodiac);
            itemBloodType = Holder.findViewById(R.id.itemBloodType);
            itemCreateTime = Holder.findViewById(R.id.itemCreateTime);
            itemUpdateTime = Holder.findViewById(R.id.itemUpdateTime);


        }
    }

    public interface dataInterface {
        void onItemClick(int id, Personal personal);
        boolean onItemLongClick(int id, Personal personal);

    }

}