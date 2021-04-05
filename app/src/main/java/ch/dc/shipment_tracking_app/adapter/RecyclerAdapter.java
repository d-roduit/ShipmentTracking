package ch.dc.shipment_tracking_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.dc.shipment_tracking_app.R;
import ch.dc.shipment_tracking_app.db.entity.Shipment;

/**
 * Recycler Adapter
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {

    private List<Shipment> shipments = new ArrayList<>();
    Context context;

    public RecyclerAdapter(Context context) {
        this.context = context;
    }

    /**
     * Creates the Recycler Holder, which is the layout that represents an item of our list.
     *
     * @param parent the parent
     * @param viewType the viewType
     * @return a RecyclerHolder
     */
    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shipment_item, parent, false);

        return new RecyclerHolder(itemView);
    }

    /**
     * Takes care of taking the data from the single Shipment into the views of our RecyclerHolder.
     *
     * @param holder the holder
     * @param position the position in the list
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        Shipment currentShipment = shipments.get(position);

        //make the date into a string
        Date date = currentShipment.getDate();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        String strDate = formatter.format(date);

        //make the hour into a string
        formatter = new SimpleDateFormat("HH:mm");
        String strHour = formatter.format(date);

        //get the status string
        String status = context.getString(currentShipment.getStatus());

        //set texts into our views
        holder.textViewDate.setText(strDate);
        holder.textViewHour.setText(strHour);
        holder.textViewStatus.setText(status);
        holder.textViewNpa.setText(currentShipment.getNpa());
        holder.textViewCity.setText(currentShipment.getCity());

    }

    /**
     * How many items we want to display in our recycler view.
     *
     * @return the number of shipments we have
     */
    @Override
    public int getItemCount() {
        return shipments.size();
    }

    /**
     * Setter for the list of Shipments
     *
     * @param shipments the list of shipments
     */
    public void setShipments(List<Shipment> shipments) {
        this.shipments = shipments;
        notifyDataSetChanged();
    }

    /**
     * Holds our views in the recycler
     */
    class RecyclerHolder extends RecyclerView.ViewHolder {

        private TextView textViewDate;
        private TextView textViewHour;
        private TextView textViewStatus;
        private TextView textViewNpa;
        private TextView textViewCity;


        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);

            textViewDate = itemView.findViewById(R.id.status_date);
            textViewHour = itemView.findViewById(R.id.status_hour);
            textViewStatus = itemView.findViewById(R.id.status_status);
            textViewNpa = itemView.findViewById(R.id.status_npa);
            textViewCity = itemView.findViewById(R.id.status_city);
        }
    }

}
