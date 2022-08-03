package com.example.diary_oil_v3;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import event_class.Event;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder>{


    private List<Event> listEvent;

    public EventAdapter(List<Event> listEvent) {
        this.listEvent = listEvent;
        //Log.d("debug","json adapter"+listEvent.toString());
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item,parent,false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = listEvent.get(position);
        Log.d("debug","json item "+event.getType() + " " + event.date + " " + event.odo);
        if (event == null)
        {return;}

        holder.tvname.setText(event.getType());
        holder.tvdue.setText(event.date);
        holder.tvodo.setText(event.odo);
        holder.imageView.setImageResource(event.getResourceid());


    }

    @Override
    public int getItemCount() {
        if (listEvent!=null)
        {return listEvent.size();}
            return 0;
    }

    public class EventViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView tvname,tvdue,tvodo;


        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.event_icon);
            tvname = itemView.findViewById(R.id.event_name);
            tvdue = itemView.findViewById(R.id.event_due);
            tvodo = itemView.findViewById(R.id.event_odo);

        }
    }
}
