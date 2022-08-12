package com.example.diary_oil_v3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import event_class.Event;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder>{


    private List<Event> listEvent;
    private Context mContext;

    public void release()
    {
        mContext=null;
    }

    public EventAdapter(Context mContext, List<Event> listEvent) {
        this.mContext = mContext;
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
        final Event event = listEvent.get(position);
        //Log.d("debug","json item "+event.getType() + " " + event.date + " " + event.odo);
        if (event == null)
        {return;}
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGoToDetail(event);
            }
        });
        holder.tvname.setText(event.getType());
        holder.tvdue.setText(event.date);
        holder.tvodo.setText(event.odo);
        holder.imageicon.setImageResource(event.getResourceid());
        holder.imageView.setImageResource(event.getStatus());
        // holder.imageView.setColorFilter(ContextCompat.getColor(holder.imageView.getContext(), R.color.gray), android.graphics.PorterDuff.Mode.MULTIPLY);


    }

    private void onClickGoToDetail(Event event) {
        Intent intent = new Intent(mContext,ItemDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Object_item",event);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (listEvent!=null)
        {return listEvent.size();}
            return 0;
    }

    public class EventViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView,imageicon;
        private TextView tvname,tvdue,tvodo;
        public RelativeLayout relativeLayout;


        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.layout_item);
            imageView = itemView.findViewById(R.id.event_icon);
            imageicon = itemView.findViewById(R.id.event_typeicon);
            tvname = itemView.findViewById(R.id.event_name);
            tvdue = itemView.findViewById(R.id.event_due);
            tvodo = itemView.findViewById(R.id.event_odo);

        }
    }
}
