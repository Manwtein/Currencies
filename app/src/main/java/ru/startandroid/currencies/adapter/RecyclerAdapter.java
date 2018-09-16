package ru.startandroid.currencies.adapter;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.startandroid.currencies.R;
import ru.startandroid.currencies.model.Valute;

public class RecyclerAdapter
        extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>{

    final int positive = R.mipmap.ic_find_previous_holo_dark;
    final int negative = R.mipmap.ic_find_next_holo_dark;

    private List<Valute> listValutesToday = new ArrayList<>();

    private List<Valute> listValutesYest = new ArrayList<>();

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_list, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return listValutesToday.size();
    }

    public void setListValutesToday(List<Valute> listValutesToday) {
        this.listValutesToday = listValutesToday;
    }

    public void setListValutesYest(List<Valute> listValutesYest) {
        this.listValutesYest = listValutesYest;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView value;
        private TextView date;
        private ImageView image;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            value = itemView.findViewById(R.id.tvValue);
            date = itemView.findViewById(R.id.tvDate);
            image = itemView.findViewById(R.id.imgArrow);
        }

        public void bind(int position){
            name.setText(listValutesToday.get(position).getCharCode());
            value.setText(listValutesToday.get(position).getValue() + "  RUB");
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Date myDate = new Date();
            long myDateLong = myDate.getTime() + 45_000_000L;
            if (myDate.getTime() > 41_400_000L){
                myDate.setTime(myDateLong); // Прибавляем 12,5 часов к текущему времени т.к. данные в ЦБ обновляются в 11:30 по МСК
            }
            date.setText("Данные на: " + sdf.format(myDate));
            setImages(position);
        }

        public void setImages(int position){

            if(listValutesToday.size()!=0
                    && listValutesYest.size()!=0) {
                String valueYestString = listValutesYest.get(position)
                        .getValue()
                        .replace(",",
                                ".");
                Double valueYest = Double.parseDouble(valueYestString);

                String valueTodayString = listValutesToday.get(position)
                        .getValue()
                        .replace(",",
                                ".");
                Double valueToday = Double.parseDouble(valueTodayString);

                if (valueYest > valueToday) {
                    image.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    image.setImageResource(negative);
                } else {
                    image.setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                    image.setImageResource(positive);
                }
            }
        }

    }
}
