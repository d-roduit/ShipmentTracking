package ch.dc.shipment_tracking_app;

import android.content.Context;

/**
 * Enumeration of tracking status
 */
public enum TrackingStatus {
    DEPOSITED(0),
    IN_TRANSIT(1),
    DELIVERED(2);

    private final int statusListPosition;

    TrackingStatus (int statusListPosition) {
        this.statusListPosition = statusListPosition;
    }

    public int getStatusListPosition() {
        return statusListPosition;
    }

    public String getStringStatus(Context context) {
        String[] trackingStatusList = context.getResources().getStringArray(R.array.post_employee_update_tracking_status_list);
        return trackingStatusList[statusListPosition];
    }

    public static TrackingStatus fromStatusListPosition(int statusListPosition) throws IllegalArgumentException {
        for (TrackingStatus trackingStatus: TrackingStatus.values()) {
            if (trackingStatus.statusListPosition == statusListPosition) {
                return trackingStatus;
            }
        }
        throw new IllegalArgumentException("No TrackingStatus constant for statusListPosition " + statusListPosition + " found");
    }

    public static TrackingStatus fromCharStatus(Context context, char charStatus) throws IllegalArgumentException {
        String[] trackingStatusList = context.getResources().getStringArray(R.array.post_employee_update_tracking_status_list);

        for (int i = 0; i < trackingStatusList.length; i++) {
            if (trackingStatusList[i].charAt(0) == charStatus) {
                return fromStatusListPosition(i);
            }
        }
        throw new IllegalArgumentException("No TrackingStatus constant for char " + charStatus + " found");
    }
}
