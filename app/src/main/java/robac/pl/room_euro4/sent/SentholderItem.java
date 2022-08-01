package robac.pl.room_euro4.sent;

import robac.pl.room_euro4.model.InfoSent;

 public  class SentholderItem {
    public final String id;
    public final String sentnumber;
    public final InfoSent detailsSent;

    public SentholderItem(String id, String sentnumber, InfoSent detailsSent) {
        this.id = id;
        this.sentnumber = sentnumber;
        this.detailsSent = detailsSent;
    }

    public InfoSent getDetails() {
        return detailsSent;
    }

    @Override
    public String toString() {
        return sentnumber;
    }
}
