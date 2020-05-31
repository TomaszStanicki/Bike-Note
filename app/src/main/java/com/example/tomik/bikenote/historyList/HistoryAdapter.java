package com.example.tomik.bikenote.historyList;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tomik.bikenote.R;
import com.example.tomik.bikenote.model.TankUpRecord;

import java.util.List;

import static com.example.tomik.bikenote.R.*;

/**
 * Adapter budujący listy historii na podstawie historii auta
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {


    private Context context;
    private List<TankUpRecord> tankList;
    private TankUpRecord tankUpRecord;
    private Drawable drawable;

    public HistoryAdapter(Context context, List<TankUpRecord> tankList) {
        this.context = context;
        this.tankList = tankList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int ViewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout.single_history_item, null);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        tankUpRecord = tankList.get(position);
        drawable = context.getResources().getDrawable(R.drawable.ic_new_tankup);
        viewHolder.activityImageView.setImageDrawable(drawable);
        viewHolder.leftLabelTopTextView.setText(tankUpRecord.getTankUpDate().toString() );
        viewHolder.rightLabelTopTextView.setText(tankUpRecord.getMileage().toString());
        viewHolder.leftLabelBottomTextView.setText(tankUpRecord.getLiters() + " L");
        viewHolder.rightLabelBottomTextView.setText(tankUpRecord.getCostInPln() + " PLN");
    }

    @Override
    public int getItemCount() {

        if (tankList == null) {
            return 0;
        }
        else {
            return tankList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView leftLabelBottomTextView;
        protected TextView leftLabelTopTextView;
        protected TextView rightLabelBottomTextView;
        protected TextView rightLabelTopTextView;
        protected ImageView trashImageView;
        protected ImageView activityImageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.activityImageView = (ImageView) itemView.findViewById(id.activity_image_view);
            this.trashImageView = (ImageView) itemView.findViewById(id.trash_image_view);

            this.leftLabelBottomTextView = (TextView) itemView.findViewById(R.id.left_label_bottom);
            this.leftLabelTopTextView = (TextView) itemView.findViewById(R.id.right_label_bottom);
            this.rightLabelBottomTextView = (TextView) itemView.findViewById(R.id.right_label_bottom);
            this.rightLabelTopTextView = (TextView) itemView.findViewById(R.id.left_label_bottom);

        }
    }
}
