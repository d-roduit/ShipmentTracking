package ch.dc.shipment_tracking_app.adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import ch.dc.shipment_tracking_app.db.entity.Shipment;

public class ShipmentDiffCallback extends DiffUtil.Callback {

    List<Shipment> oldShipments;
    List<Shipment> newShipments;

    public ShipmentDiffCallback(List<Shipment> oldShipments, List<Shipment> newShipments) {
        this.oldShipments = oldShipments;
        this.newShipments = newShipments;
    }

    @Override
    public int getOldListSize() {
        return oldShipments.size();
    }

    @Override
    public int getNewListSize() {
        return newShipments.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldShipments.get(oldItemPosition).getId() == newShipments.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Shipment oldShipment = oldShipments.get(oldItemPosition);
        Shipment newShipment = newShipments.get(newItemPosition);

        return oldShipment.getId() == newShipment.getId() &&
                oldShipment.getShippingNumber() == newShipment.getShippingNumber() &&
                oldShipment.getDate().equals(newShipment.getDate());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}