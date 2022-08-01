package robac.pl.room_euro4;


import static androidx.recyclerview.widget.RecyclerView.ViewHolder;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import robac.pl.room_euro4.databinding.FragmentKomentarzBinding;
import robac.pl.room_euro4.model.Message;
import robac.pl.room_euro4.model.PojemnikStatusInfo;
import robac.pl.room_euro4.model.PojemnikiInfo;


public class KomentarzFragment extends Fragment implements IOnBackPressed {
    private static final String TAG = "KomentarzFragment";
    public Fragment obFrag;
    public robac.pl.room_euro4.model.Message obMessage;
    public robac.pl.room_euro4.model.Message obMessageKopia;
    private boolean myCondition = false;
    private FragmentKomentarzBinding binding;
    private robac.pl.room_euro4.KomentarzFragmentViewModel mViewModel;
//    private Disposable dzialaj;
    private GridLayoutManager mLayoutManager;
    //    private RecyclerView recyclerView;
//    private List<PojemnikStatusInfo> messages;
    private StanPojemnikaInfo stanPojemnikaInfo;
    private final boolean inicjalizacja = false;


//    private IMainActivity mIMainActivity;
//    private String mIncomingMessage = "";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart Dupa" + obMessage.getPojemnikiInfo().getPojemnikiInfos()[1]);
//        obMessage.getPojemnikiInfo().setPojemnikiInfos(0, true);
//        obMessage.getPojemnikiInfo().setPojemnikiInfos(2, true);
        PojemnikiInfo obInfo = obMessage.getPojemnikiInfo();


        binding.statusPojemnikListView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mLayoutManager = new GridLayoutManager(getContext(), 6, GridLayoutManager.HORIZONTAL, false);
        binding.statusPojemnikListView.setLayoutManager(mLayoutManager);
        binding.statusPojemnikListView.setHasFixedSize(true);
        binding.statusPojemnikListView.setItemAnimator(new DefaultItemAnimator());
        binding.komentarz.setText(obMessage.getContent());
//        binding.statusPojemnikListView.setAdapter(stanPojemnikaInfo);

        obFrag = getParentFragment();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(robac.pl.room_euro4.KomentarzFragmentViewModel.class);

        Log.d(TAG, "onViewCreated");
//        obMessage.getPojemnikiInfo().setPojemnikiInfos(0, true);
//        obMessage.getPojemnikiInfo().setPojemnikiInfos(2, true);
        PojemnikiInfo obInfo = obMessage.getPojemnikiInfo();
//        recyclerView = binding.statusPojemnikListView;
//        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        binding.statusPojemnikListView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//       lp.setMargins(300,300,300,300);
//        mLayoutManager = new GridLayoutManager(getContext(), 6);
        mLayoutManager = new GridLayoutManager(getContext(), 6, GridLayoutManager.HORIZONTAL, false);


        binding.statusPojemnikListView.setLayoutManager(mLayoutManager);
        binding.statusPojemnikListView.setHasFixedSize(true);
        binding.statusPojemnikListView.setItemAnimator(new DefaultItemAnimator());
        binding.statusPojemnikListView.setAdapter(stanPojemnikaInfo);
        // Set data adapter to list view.

//        if(savedInstanceState != null){
//            // scroll to existing position which exist before rotation.
//            binding.statusPojemnikListView.scrollToPosition(savedInstanceState.getInt("position"));
//        }

        binding.buttonAnuluj.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                NavDirections action = (NavDirections) KomentarzFragmentDirections.actionCommentFragmentToSecondFragment(obMessageKopia);
                navController.navigate(action);
            }
        });
        binding.buttonOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                myCondition = true;

                Editable editable = new SpannableStringBuilder(binding.komentarz.getText());
                obMessage.setContent(editable.toString());

                NavController navController = Navigation.findNavController(v);
                NavDirections action = (NavDirections) KomentarzFragmentDirections.actionCommentFragmentToSecondFragment(obMessage);
                navController.navigate(action);
//                Log.d(TAG, "pojemnikiInfo=" + new SQLTypeConverters().toMessageString(obMessage));


//                Toast.makeText(getContext(), "pojemnikiInfo="+new SQLTypeConverters().toMessageString(obMessage), Toast.LENGTH_SHORT).show();
//                NavHostFragment.findNavController(KomentarzFragment.this).navigate(R.id.action_CommentFragment_to_SecondFragment);

            }



        });

    }


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Log.d(TAG, "onCreateView");
        mViewModel =
                new ViewModelProvider(this).get(robac.pl.room_euro4.KomentarzFragmentViewModel.class);
        binding = FragmentKomentarzBinding.inflate(inflater, container, false);

        View root = binding.getRoot();
//        PojemnikiInfo statusModel = new ViewModelProvider(requireActivity()).get(PojemnikiInfo.class);


           obMessage = KomentarzFragmentArgs.fromBundle(getArguments()).getMessage();
        obMessageKopia=new Message();
        obMessageKopia.set(obMessage);

//        obMessage.getPojemnikiInfo().setPojemnikiInfos(0, true);
//        obMessage.getPojemnikiInfo().setPojemnikiInfos(2, true);
        Log.d(TAG, "onCreateView--X--" + obMessage.getPojemnikiInfo().getPojemnikiInfos()[0]);
        PojemnikiInfo obInfo = obMessage.getPojemnikiInfo();

        if (stanPojemnikaInfo == null)
            stanPojemnikaInfo = new StanPojemnikaInfo(obInfo.getpojemnikStatusInfoList(), getContext());

//        PojemnikiInfo obPojemniki = obMessage.getPojemnikiInfo();

        binding.infoKomentarz.setText(obMessage.getBdo()?("Dokument BDO-id"+ obMessage.getId()):"Pojemnik-baza-nr=" + obMessage.getQrcode()+"id="+ obMessage.getId());
        binding.komentarz.setText(obMessage.getContent());

//        recyclerView = binding.statusPojemnikListView;


        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        binding.statusPojemnikListView.setLayoutParams(lp);
//       lp.setMargins(300,300,300,300);
//        mLayoutManager = new GridLayoutManager(getContext(), 6);
        mLayoutManager = new GridLayoutManager(getContext(), 6, GridLayoutManager.HORIZONTAL, false);


        binding.statusPojemnikListView.setLayoutManager(mLayoutManager);
        binding.statusPojemnikListView.setHasFixedSize(true);
        binding.statusPojemnikListView.setItemAnimator(new DefaultItemAnimator());
        binding.statusPojemnikListView.setAdapter(stanPojemnikaInfo);

//        statusModel.getStany(obInfo.getpojemnikStatusInfoList()).
//                observe((LifecycleOwner) binding.statusPojemnikListView.getAdapter(), new Observer<List<PojemnikStatusInfo>>() {
//                    @Override
//                    public void onChanged(List<PojemnikStatusInfo> pojemnikStatusInfos) {
//                        binding.statusPojemnikListView.setAdapter((RecyclerView.Adapter<KomentarzFragment.StanPojemnikaInfo.MessageViewHolder>) pojemnikStatusInfos);
//                    }
//                });


//        if(savedInstanceState != null){
//            // scroll to existing position which exist before rotation.
//            binding.statusPojemnikListView.scrollToPosition(savedInstanceState.getInt("position"));
//        }

        return binding.getRoot();

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
//        mIMainActivity = (MainActivity) getActivity();
    }

    /**
     * If you return true the back press will not be taken into account, otherwise the activity will act naturally
     *
     * @return true if your processing has priority if not false
     */

    // Return an initialize list of ListViewItemDTO.
    @Override
    public boolean onBackPressed() {

        //action not popBackStack
        return myCondition;

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public class StanPojemnikaInfo extends RecyclerView.Adapter<KomentarzFragment.StanPojemnikaInfo.MessageViewHolder> {
        private static final String TAG = "KomentarzFragment.StanPojemnikaInfo";
        private final CompositeDisposable disposables = new CompositeDisposable();
        private final Context mcontext;
        HashMap<Integer, MessageViewHolder> holderlist;
        private List<PojemnikStatusInfo> pojemnikStatusInfoList;

        public StanPojemnikaInfo(List<PojemnikStatusInfo> pojemnikStatusInfoList, Context mcontext) {

            this.pojemnikStatusInfoList = pojemnikStatusInfoList;
            this.mcontext = mcontext;
            holderlist = new HashMap<>();
        }

        public PojemnikStatusInfo getPojemnikStatusInfoPOZ(@NotNull int poz) {

            return pojemnikStatusInfoList.get(poz);
        }

        public MessageViewHolder getViewByPosition(int position) {

            return holderlist.get(position);
        }


        @NonNull
        @Override
        public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_view_with_checkbox, parent, false);
//        View v =  LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
            KomentarzFragment.StanPojemnikaInfo.MessageViewHolder viewHolder = new KomentarzFragment.StanPojemnikaInfo.MessageViewHolder(v);
            viewHolder.wyb = v.findViewById(R.id.list_view_item_checkbox);

            viewHolder.opisPojemniki = v.findViewById(R.id.list_view_item_text);

            return viewHolder;


        }


        //        public KomentarzFragment.StanPojemnikaInfo.MessageViewHolder getViewByPosition(int position) {
//            return holderlist.get(position);
//        }
        @Override
        public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

            PojemnikStatusInfo infoStatus = pojemnikStatusInfoList.get(position);
            if (!holderlist.containsKey(position)) {
                holderlist.put(position, holder);
            }

//

            holder.getWyb().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    boolean cos = ((CheckBox) v.findViewById(R.id.list_view_item_checkbox)).isChecked();
                    PojemnikStatusInfo ob = pojemnikStatusInfoList.get(position);
                    ob.setWybor(cos);
                    obMessage.getPojemnikiInfo();
                    pojemnikStatusInfoList.get(position).setWybor(cos);
                    setPojemnikStatusInfoList(pojemnikStatusInfoList);
                    obMessage.getPojemnikiInfo().setPojemnikStatus(ob, position);
                    //  recyclerView.getAdapter().notifyDataSetChanged();
                    //  obMessage.setPojemnikiInfo(ob);
                    Log.d(TAG, "holder.getWyb(). = " + obMessage.getPojemnikiInfo().getPojemnikiInfos()[position] + "poz=" + position + "cos=" + cos);
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {

                final int position = holder.getAdapterPosition();
                final PojemnikStatusInfo ob = pojemnikStatusInfoList.get(position);

                public void onClick(View v) {
                    Log.d(TAG, "holder.itemView. = " + ob.getPojemniki() + ob.getWybor() + "poz=" + position);
                }
            });
            holder.wyb.setChecked(pojemnikStatusInfoList.get(holder.getAdapterPosition()).getWybor());
            holder.wyb.setText(pojemnikStatusInfoList.get(holder.getAdapterPosition()).getPojemniki());
        }

        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        @Override
        public int getItemCount() {
            return pojemnikStatusInfoList.size();
        }

        public List<PojemnikStatusInfo> getPojemnikStatusInfoList() {
            return pojemnikStatusInfoList;
        }

        public void setPojemnikStatusInfoList(List<PojemnikStatusInfo> pojemnikStatusInfoList) {
            this.pojemnikStatusInfoList = pojemnikStatusInfoList;
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        /**
         * Returns the Lifecycle of the provider.
         *
         * @return The lifecycle of the provider.
         */

        public class MessageViewHolder extends ViewHolder {

            public TextView opisPojemniki;
            public CheckBox wyb;

            public MessageViewHolder(@NonNull View itemView) {
                super(itemView);
                wyb = itemView.findViewById(R.id.list_view_item_checkbox);

                opisPojemniki = itemView.findViewById(R.id.list_view_item_text);
                wyb.setText(opisPojemniki.getText());
            }

            public TextView getOpisPojemniki() {
                return opisPojemniki;
            }

            public void setOpisPojemniki(TextView opisPojemniki) {
                this.opisPojemniki = opisPojemniki;
            }

            public CheckBox getWyb() {
                return wyb;
            }

            //
            public void setWyb(CheckBox wyb) {
                this.wyb = wyb;

            }
//
//            public boolean isChecked() {
//                return wyb.isChecked();
//            }

//            public void setText() {
//                wyb.setText(opisPojemniki.getText());
//            }
        }
    }
    private void safelyUnsubscribe(Disposable... subscriptions) {
        for (Disposable subscription : subscriptions) {
            if (subscription != null && !subscription.isDisposed()) {
                subscription.dispose();
            }
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        safelyUnsubscribe();
    }
}