package ch.dc.shipment_tracking_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.dc.shipment_tracking_app.R;
import ch.dc.shipment_tracking_app.TrackingStatus;
import ch.dc.shipment_tracking_app.db.entity.Shipment;

public class ManageRecyclerAdapter extends RecyclerView.Adapter<ManageRecyclerAdapter.RecyclerHolder>{


    private List<Shipment> shipments = new ArrayList<>();
    Context context;

    public ManageRecyclerAdapter(Context context) {
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
    public ManageRecyclerAdapter.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shipment_item_manage, parent, false);

        return new ManageRecyclerAdapter.RecyclerHolder(itemView);
    }

    /**
     * Takes care of taking the data from the single Shipment into the views of our RecyclerHolder.
     *
     * @param holder the holder
     * @param position the position in the list
     */
    @Override
    public void onBindViewHolder(@NonNull ManageRecyclerAdapter.RecyclerHolder holder, int position) {
        Shipment currentShipment = shipments.get(position);

        //make the date into a string
        Date date = currentShipment.getDate();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        String strDate = formatter.format(date);

        //make the hour into a string
        formatter = new SimpleDateFormat("HH:mm");
        String strHour = formatter.format(date);

        String completeDate = strHour + " - " + strDate;

        //get the status string
        TrackingStatus trackingStatus = TrackingStatus.fromStatusListPosition(currentShipment.getStatus());
        String status = trackingStatus.getStringStatus(context);

        //set texts into our views
        holder.textViewDate.setText(completeDate);
        holder.textViewStatus.setText(status);
        holder.textInputLayoutStatus.getEditText().setText(status);
        holder.textInputLayoutNpa.getEditText().setText(currentShipment.getNpa());
        holder.textInputLayoutCity.getEditText().setText(currentShipment.getCity());

        if(position == shipments.size()-1) {
            holder.imageViewArrow.setVisibility(View.GONE);
        }

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
        private TextView textViewStatus;
        private TextInputLayout textInputLayoutStatus;
        private TextInputLayout textInputLayoutNpa;
        private TextInputLayout textInputLayoutCity;
        private ImageView imageViewArrow;


        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);

            textViewDate = itemView.findViewById(R.id.post_employee_manage_package_date);
            textViewStatus = itemView.findViewById(R.id.post_employee_manage_package_status);
            textInputLayoutStatus = itemView.findViewById(R.id.post_employee_manage_package_input_status);
            textInputLayoutNpa = itemView.findViewById(R.id.post_employee_manage_package_input_npa);
            textInputLayoutCity = itemView.findViewById(R.id.post_employee_manage_package_input_city);
            imageViewArrow = itemView.findViewById(R.id.post_employee_manage_package_arrow);
        }
    }

}
