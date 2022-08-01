package robac.pl.room_euro4;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import robac.pl.room_euro4.placeholder.PlaceholderContent;
import robac.pl.room_euro4.sent.SentholderContent;
import robac.pl.room_euro4.sent.SentholderItem;

/**
 * A fragment representing a list of Items.
 */
public class SentFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public static RecyclerView recyclerView;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SentFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SentFragment newInstance(int columnCount) {
        SentFragment fragment = new SentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            synchronized(this){
                SentholderContent obListaSent = new SentholderContent();
                Observable<SentholderItem> fromIterable = Observable.fromIterable(MainActivity.ListaholderItem);
                Disposable listPrinter = fromIterable.subscribe(item -> obListaSent.ITEMS.add(item));
                compositeDisposable.add(listPrinter);
              recyclerView.setAdapter(new MySentRecyclerViewAdapter(obListaSent.ITEMS));
            }
//            for (int i = 1; i <= obListaSent.ITEMS.size(); i++) {
//                obListaSent.addItem(new SentholderItem(String.valueOf(i),  obListaSent.ITEMS.get(i).sentnumber, obListaSent.makeDetails(i)));
////        }


        }
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        safelyUnsubscribe(compositeDisposable);
        // disposables.clear();
    }
    private void safelyUnsubscribe(Disposable... subscriptions) {
        for (Disposable subscription : subscriptions) {
            if (subscription != null && !subscription.isDisposed()) {
                subscription.dispose();
            }
        }
    }
}