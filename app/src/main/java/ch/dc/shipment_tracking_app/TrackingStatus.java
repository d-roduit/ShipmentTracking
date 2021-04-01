package ch.dc.shipment_tracking_app;

import androidx.annotation.StringRes;

/**
 * Enumeration of tracking status
 */
public enum TrackingStatus {
    DEPOSITED(R.string.tracking_status_deposited),
    IN_TRANSIT(R.string.tracking_status_in_transit),
    DELIVERED(R.string.tracking_status_delivered);

    private final int stringId;

    TrackingStatus (int stringId) {
        this.stringId = stringId;
    }

    @StringRes
    public int getStringId() {
        return stringId;
    }

    public static TrackingStatus fromStringId(int stringId) throws IllegalArgumentException {
        for (TrackingStatus trackingStatus: TrackingStatus.values()) {
            if (trackingStatus.stringId == stringId) {
                return trackingStatus;
            }
        }
        throw new IllegalArgumentException("No TrackingStatus constant for stringId " + stringId + " found");
    }
}
