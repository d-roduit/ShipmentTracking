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
        System.out.println("oldShipments.size()" + oldShipments.size());
        return oldShipments.size();
    }

    @Override
    public int getNewListSize() {
        System.out.println("newShipments.size()" + newShipments.size());
        return newShipments.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        System.out.println("oldShipments.get(oldItemPosition) : " + oldShipments.get(oldItemPosition));
        System.out.println("newShipments.get(newItemPosition) : " + newShipments.get(newItemPosition));
        System.out.println("areItemsTheSame : " + (oldShipments.get(oldItemPosition).getId() == newShipments.get(newItemPosition).getId()));
        return oldShipments.get(oldItemPosition).getId() == newShipments.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        System.out.println("oldShipments.get(oldItemPosition) : " + oldShipments.get(oldItemPosition));
        System.out.println("newShipments.get(newItemPosition) : " + newShipments.get(newItemPosition));
        System.out.println("areContentesTheSame : " + oldShipments.get(oldItemPosition).equals(newShipments.get(newItemPosition)));
        return oldShipments.get(oldItemPosition).equals(newShipments.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}