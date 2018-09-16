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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import ru.startandroid.currencies.R;
import ru.startandroid.currencies.model.Valute;

public class RecyclerAdapter
        extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>{

    private final int positive = R.mipmap.ic_find_previous_holo_dark;
    private final int negative = R.mipmap.ic_find_next_holo_dark;

    private final String FORMAT_DATE = "dd.MM.yyyy";
    private final String RUB = "  RUB";
    private final String DATA_TODAY = "Данные на: ";

    private String dateTodayString = "";

    private List<Valute> listValutesToday = new ArrayList<>();

    private List<Valute> listValutesYest = new ArrayList<>();

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_list, parent, false);
        return new RecyclerViewHolder(view);
    }

    public RecyclerAdapter() {
        setDate();
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


    private void setDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
        Calendar calendar = new GregorianCalendar();

        if (calendar.get(Calendar.HOUR_OF_DAY) > 11) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        else if (calendar.get(Calendar.HOUR_OF_DAY) == 11)
        {
            if (calendar.get(Calendar.MINUTE) > 30)
                calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        dateTodayString = DATA_TODAY + sdf.format(calendar.getTime());
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
            String valueString = listValutesToday.get(position).getValue() + RUB;

            name.setText(listValutesToday.get(position).getCharCode());
            value.setText(valueString);
            date.setText(dateTodayString);
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
