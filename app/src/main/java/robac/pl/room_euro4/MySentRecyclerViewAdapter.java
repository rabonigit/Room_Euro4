package robac.pl.room_euro4;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import robac.pl.room_euro4.databinding.FragmentSentBinding;
import robac.pl.room_euro4.placeholder.PlaceholderContent.PlaceholderItem;
import robac.pl.room_euro4.sent.SentholderItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MySentRecyclerViewAdapter extends RecyclerView.Adapter<MySentRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "MySentRecyclerViewAdapter";
    private final List<SentholderItem> mValues;

    public MySentRecyclerViewAdapter(List<SentholderItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentSentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).sentnumber);

        holder.mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getBindingAdapterPosition();
                try {
                    Log.d(TAG, "" + mValues.get(pos) + "-" + mValues.get(pos).detailsSent);
                    NavController navController = Navigation.findNavController(view);
                    NavDirections action = SentFragmentDirections.actionSentFragmentToSentInfoFragment(mValues.get(pos).detailsSent);
                    navController.navigate(action);
                } catch (java.lang.IllegalArgumentException ex) {
                    Log.d(TAG, "Problem model");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public SentholderItem mItem;

        public ViewHolder(FragmentSentBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}