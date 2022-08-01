package robac.pl.room_euro4.sent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import robac.pl.room_euro4.MainActivity;
import robac.pl.room_euro4.model.InfoSent;

/**
 * Helper class for providing sample sentnumber for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class SentholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    public  List<SentholderItem> ITEMS = new ArrayList<SentholderItem>();
//    public static final List<SentholderItem> ITEMS = new ArrayList<SentholderItem>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public  Map<String, SentholderItem> ITEM_MAP = new HashMap<String, SentholderItem>();

    private  int COUNT = ITEMS.size();

//    static {
//        // Add some sample items.
//        for (int i = 1; i <= COUNT; i++) {
//            addItem(createPlaceholderItem(i));
//        }
//    }

    private  void addItem(SentholderItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public   SentholderItem createPlaceholderItem(int position) {
        return new SentholderItem(String.valueOf(position),  ITEMS.get(position).sentnumber, makeDetails(position));
    }

    public InfoSent makeDetails(int position) {

        return ITEMS.get(position).getDetails();
    }


}