package ch.dc.shipment_tracking_app.adapter;

import ch.dc.shipment_tracking_app.db.entity.Shipment;

public interface onDeleteShipmentClickListener {
    void onItemClick(Shipment shipment);
}