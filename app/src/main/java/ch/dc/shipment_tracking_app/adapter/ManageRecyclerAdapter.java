package ch.dc.shipment_tracking_app.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.dc.shipment_tracking_app.R;
import ch.dc.shipment_tracking_app.TrackingStatus;
import ch.dc.shipment_tracking_app.db.entity.Shipment;

public class ManageRecyclerAdapter extends RecyclerView.Adapter<ManageRecyclerAdapter.RecyclerHolder> {

    private List<Shipment> shipments = new ArrayList<>();
    private final Context context;
    private OnDeleteShipmentClickListener onDeleteShipmentClickListener;
    private final List<Shipment> changedShipments = new ArrayList<>();

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

        // Make the date into a string
        Date date = currentShipment.getDate();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        String strDate = formatter.format(date);

        // Make the hour into a string
        formatter = new SimpleDateFormat("HH:mm");
        String strHour = formatter.format(date);

        String completeDate = strHour + " - " + strDate;

        // Get the status string
        TrackingStatus trackingStatus = TrackingStatus.fromStatusListPosition(currentShipment.getStatus());
        String status = trackingStatus.getStringStatus(context);

        // Add text watcher listener
        holder.textInputLayoutStatus.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("----------------------------ON TEXT CHANGED -----------------------------------");
                currentShipment.setStatus(TrackingStatus.fromCharStatus(context, s.charAt(0)).getStatusListPosition());
                if (!changedShipments.contains(currentShipment)) {
                    changedShipments.add(currentShipment);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.textInputLayoutCity.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("----------------------------ON TEXT CHANGED -----------------------------------");
                currentShipment.setCity(s.toString());
                if (!changedShipments.contains(currentShipment)) {
                    changedShipments.add(currentShipment);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.textInputLayoutNpa.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("----------------------------ON TEXT CHANGED -----------------------------------");
                currentShipment.setNpa(s.toString());
                if (!changedShipments.contains(currentShipment)) {
                    changedShipments.add(currentShipment);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Set texts into our views
        holder.textViewDate.setText(completeDate);
        holder.textViewStatus.setText(status);
        holder.statusDropDown.setText(status, false);
        holder.textInputLayoutNpa.getEditText().setText(currentShipment.getNpa());
        holder.textInputLayoutCity.getEditText().setText(currentShipment.getCity());

        if (position == shipments.size() - 1) {
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
        if (shipments == null) {
            System.out.println("shipments NULL");
        } else {
            System.out.println("shipments NOT NULL");
        }
        updateShipments(shipments);
    }

    /**
     * Make the diff between two shipment lists to add/remove only the updated items in the RecyclerView
     * @param newShipments A list of new shipments
     */
    public void updateShipments(List<Shipment> newShipments) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ShipmentDiffCallback(this.shipments, newShipments));
        this.shipments.clear();
        this.shipments.addAll(newShipments);
        diffResult.dispatchUpdatesTo(this);
    }

    public List<Shipment> getChangedShipments() {
        return changedShipments;
    }

    public void setOnDeleteShipmentClickListener(OnDeleteShipmentClickListener onDeleteShipmentClickListener) {
        this.onDeleteShipmentClickListener = onDeleteShipmentClickListener;
    }

    /**
     * Holds our views in the recycler
     */
    public class RecyclerHolder extends RecyclerView.ViewHolder {

        private final ImageView imageViewDelete;
        private final TextView textViewDate;
        private final TextView textViewStatus;
        private final AutoCompleteTextView statusDropDown;
        private final TextInputLayout textInputLayoutStatus;
        private final TextInputLayout textInputLayoutNpa;
        private final TextInputLayout textInputLayoutCity;
        private final ImageView imageViewArrow;

        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);

            imageViewDelete = itemView.findViewById(R.id.post_employee_manage_package_delete_shipment_button);
            textViewDate = itemView.findViewById(R.id.post_employee_manage_package_date);
            textViewStatus = itemView.findViewById(R.id.post_employee_manage_package_status);
            textInputLayoutStatus = itemView.findViewById(R.id.post_employee_manage_package_input_status);
            statusDropDown = itemView.findViewById(R.id.post_employee_manage_package_status_list);
            textInputLayoutNpa = itemView.findViewById(R.id.post_employee_manage_package_input_npa);
            textInputLayoutCity = itemView.findViewById(R.id.post_employee_manage_package_input_city);
            imageViewArrow = itemView.findViewById(R.id.post_employee_manage_package_arrow);

            // Create dropdown adapter
            String[] trackingStatusList = context.getResources().getStringArray(R.array.post_employee_update_tracking_status_list);
            ArrayAdapter<String> trackingStatusAdapter = new ArrayAdapter<>(
                    context, R.layout.dropdown_item, trackingStatusList
            );
            statusDropDown.setAdapter(trackingStatusAdapter);

            imageViewDelete.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (onDeleteShipmentClickListener != null && position != RecyclerView.NO_POSITION) {
                    onDeleteShipmentClickListener.onItemClick(shipments.get(position));
                }
            });
        }
    }

    public interface OnDeleteShipmentClickListener {
        void onItemClick(Shipment shipment);
    }
}
