package ch.dc.shipment_tracking_app.db.async;

public interface OnPostAsyncQueryExecuted<T> {
    void onPostExecute(T response);
}
