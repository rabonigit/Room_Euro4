package robac.pl.room_euro4;

import static robac.pl.room_euro4.MainActivity.messageDao;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import robac.pl.room_euro4.dao.MessageDao;
import robac.pl.room_euro4.databinding.FragmentSecondBinding;
import robac.pl.room_euro4.model.Message;
import robac.pl.room_euro4.model.NewMessageActivity;
import robac.pl.room_euro4.model.PojemnikiInfo;


public class QrCodeFragment extends Fragment {

    //    private IMainActivity mIMainActivity;
//    private String mIncomingMessage = "";
    private static final String TAG = "QrCodeFragment";
    private static final SimpleDateFormat formatDaty = new SimpleDateFormat("dd-MM-yy HH:mm");
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final CompositeDisposable disposables = new CompositeDisposable();
    public FragmentSecondBinding binding;
    //    public Fragment obFrag;
    // private static Message ob;
    private boolean domyslnyArgument = false;
    private RecyclerView recyclerView;
    private List<Message> messages;
    private Message obMessage;
    private GridLayoutManager mLayoutManager;
//    private ScannerLiveView camera;
//    private TextView scannedTV;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        recyclerView = binding.messagesListView;
        mLayoutManager = new GridLayoutManager(container.getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

//        obFrag = getParentFragment();
//        obMessage = KomentarzFragmentArgs.fromBundle(getArguments()).getMessage();
        domyslnyArgument = QrCodeFragmentArgs.fromBundle(getArguments()).getDomyslna();
      if(!domyslnyArgument) {
          obMessage = QrCodeFragmentArgs.fromBundle(getArguments()).getMessageUpdate();


          Log.e(TAG, "getArguments()= " + getArguments() + "\n" + obMessage.getContent() + "id=" + obMessage.getId());

      }
//        Completable completable = Completable.fromRunnable(
        // method is called when camera scans the qr code and the data from qr code is stored in data in string format.
        Observable.just(messageDao).subscribeOn(Schedulers.io()).map(db -> {
            if (!domyslnyArgument) {


                if (!db.isExist(obMessage.getId())) {
                    db.insert(obMessage);
                } else {
                    disposables.add(
                            messageDao.update(obMessage)
                                    .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                                    .subscribe(
                                            () -> {
                                            },
                                            error -> Log.e(TAG, "Couldn't Update  Wiadomość = " + error.toString())
                                    )
                    );
                }
            }

//            messages = db.getAll();
            messages = db.getAllnotSendRabbit(false);


            return messages;
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(stringArrayAdapter -> {
                    recyclerView.setAdapter(new QrCodeMessageAdapter2(stringArrayAdapter, messageDao, getContext()));
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                },
                error -> {
                    Log.e(TAG, "Couldn't load Messages  QrCodeFragmentArgsY= " + error.getMessage());

                }


        );


        //compositeDisposable.add(userPrinter);
        // stop baza
        {
            return binding.getRoot();
        }

    }

//    public Fragment getObFrag() {
//        return obFrag;
//    }

    public void onStart(LayoutInflater inflater, ViewGroup container,
                        Bundle savedInstanceState) {
        super.onStart();
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        recyclerView = binding.messagesListView;
        mLayoutManager = new GridLayoutManager(container.getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        obFrag = getParentFragment();

        Observable.just(messageDao).subscribeOn(Schedulers.io()).map(db -> {
//            messages = db.getAll();
            messages = db.getAllnotSendRabbit(false);
            return messages;
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(stringArrayAdapter -> {
                    recyclerView.setAdapter(new QrCodeMessageAdapter2(stringArrayAdapter, messageDao, getContext()));
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                },
                error -> Log.e(TAG, "Couldn't load Messages  QrCodeFragment= " + error.getMessage()));

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = binding.messagesListView;
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavHostFragment.findNavController(QrCodeFragment.this)
                        .navigate(R.id.action_QrCodeFragment_to_FirstFragment);
            }
        });


        Observable.just(messageDao).subscribeOn(Schedulers.io()).map(db -> {
//            messages = db.getAll();
            messages = db.getAllnotSendRabbit(false);
            return messages;
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(stringArrayAdapter -> {
                    recyclerView.setAdapter(new QrCodeMessageAdapter2(stringArrayAdapter, messageDao, getContext()));
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                },
                error -> Log.e(TAG, "Couldn't load Messages  QrCodeFragmentOnClick= " + error.getMessage()));


        //stop

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        // disposables.clear();
    }

    /**
     * Called when the Fragment is no longer resumed.  This is generally
     * <p>
     * Activity's lifecycle.
     */
    @Override
    public void onPause() {

        super.onPause();
        safelyUnsubscribe(disposables);
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * <p>
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();

        recyclerView = binding.messagesListView;
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Observable.just(messageDao).subscribeOn(Schedulers.io()).map(db -> {
//            messages = db.getAll();
            messages = db.getAllnotSendRabbit(false);
            return messages;
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(stringArrayAdapter -> {
                    recyclerView.setAdapter(new QrCodeMessageAdapter2(stringArrayAdapter, messageDao, getContext()));
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                },
                error -> Log.e(TAG, "Couldn't load Messages  QrCodeFragmentOnResume= " + error.getMessage()));
    }

    //    private void setIncomingMessage(){
//        if(!mIncomingMessage.equals("")){
//            binding.textviewSecond.setText(mIncomingMessage);
//        }
//    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        mIMainActivity = (MainActivity) getActivity();
    }

    //koniec wstaawiania
    public Message getQrCode_Info(@NonNull String data, String phoneNumber) {
//        boolean pojemnik=false;
        String data_out = data;
//        if(data.contains("www.robac.pl"))
//        {
//            pojemnik=true;
//        }
        Log.d("Util", "getQrCode_Info=" + data);
        Message out = new Message("Brak", "Brak", false, false, phoneNumber, false);

        try {
            String[] arrOfStr = data_out.split(",", 2);

            String cos = arrOfStr[0];
            if (cos.contains("CardId")) {
                arrOfStr = cos.split(":", 2);
                out.setQrcode(arrOfStr[1]);
                out.setBdo(true);
                out.setGps(MainActivity.obgps);
            }
//        if(cos.contains("qrID")&&pojemnik){
//            arrOfStr = cos.split(":", 2);
//            out.setQrcode(arrOfStr[0]);
//            out.setBdo(false);
//            out.setGps(MainActivity.obgps);
//        }
            else out.setQrcode("Brak");
        } catch (ArrayIndexOutOfBoundsException xx) {
            out.setQrcode("Brak");

        }
        return out;
    }

    private void safelyUnsubscribe(Disposable... subscriptions) {
        for (Disposable subscription : subscriptions) {
            if (subscription != null && !subscription.isDisposed()) {
                subscription.dispose();
            }
        }
    }

    //tu wstawiam
    public class QrCodeMessageAdapter2 extends RecyclerView.Adapter<QrCodeMessageAdapter2.MessageViewHolder> {
        private static final String TAG = "QrCodeMessageAdapter2";
        private final CompositeDisposable disposables = new CompositeDisposable();
        private final Context mcontext;
        HashMap<Integer, MessageViewHolder> holderlist;
        //        HashMap<Message, Integer> mapView;
        MessageDao messageDao;
        private List<Message> messageList;


        public QrCodeMessageAdapter2(List<Message> messages, MessageDao messageDao, Context mcontext) {
            this.messageList = messages;
            this.messageDao = messageDao;
            holderlist = new HashMap<>();
//            mapView = new HashMap<>();
            this.mcontext = mcontext;


        }

        public int getPosition(@NotNull long id) {
            return 0;
        }


        public Message getMeesageID(@NotNull long id) {
            Message wynik = messageList.size() > 0 ? messageList.get(0) : new Message("brak", "brak");
            for (Message xx : messageList) {
                if (xx.getId() == id) {
                    return xx;
                }
            }
            return wynik;
        }

        public void removePositin(int id) {
            messageList.remove(id);

        }

        public void removeMeesage(Message ob) {
            messageList.remove(ob);
//        mapView.remove(ob.getId());
        }

        public MessageViewHolder getViewByPosition(int position) {

            return holderlist.get(position);
        }

        @RequiresApi(api = Build.VERSION_CODES.P)
        @NonNull
        @Override
        public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_item_recycle_wiadomosc, parent, false);
//        View v =  LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
            MessageViewHolder viewHolder = new MessageViewHolder(v);
            viewHolder.infoName = v.findViewById(R.id.info_name);
            viewHolder.infoDeleteButton = v.findViewById(R.id.delete_info_button);


            return viewHolder;
        }


        @Override
        public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
            Message wiadomosc = messageList.get(position);
            if (wiadomosc == null) return;
//        holder.animalName.setText(animal.getContent());
//        holder.animalName.setText(animal.getContent()+"-"+position+":"+animal.getId());

            //  holder.infoDeleteButton.setImageResource(wiadomosc.);
//        mapView.put(animal.getId(),position);
//            Log.d(TAG, "mapView= " + wiadomosc.getId() + " " + position + "wynik=" + mapView.get(wiadomosc.getId()));

//        public void onBindViewHolder(final ViewHolder holder, final int position) {
            if (!holderlist.containsKey(position)) {
                holderlist.put(position, holder);
            }


            int color = Color.GREEN;
            if (wiadomosc.getBdo()) {
                color = Color.CYAN;

                holder.infoName.setText("" + wiadomosc.getId() + ":" + formatDaty.format(wiadomosc.getTimestamp()));
            } else {
                holder.infoName.setText(wiadomosc.getQrcode() + "-" + wiadomosc.getId() + ":" + formatDaty.format(wiadomosc.getTimestamp()));

            }

//        int color = Color.GREEN;
//        if(position % 2 == 0) {
//            color = Color.CYAN;
//        }
            holder.infoName.setBackgroundColor(color);


            holder.infoDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getBindingAdapterPosition();
//                            getAdapterPosition();
                    Message ob = messageList.get(position);

//                Toast.makeText( view.getContext(),"Usunąłem -"+ ob.getContent()+":"+ob.getId(), Toast.LENGTH_SHORT).show();
                    messageList.remove(position);
//                mapView.remove(ob.getId());

                    disposables.add(
                            messageDao.deleteid(ob.getId())
                                    .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                                    .subscribe(
                                            () -> {
                                            },
                                            error -> Log.e(TAG, "Brak możliwosci skasowania Wiadomości = " + error.toString())
                                    )
                    );
//                messageDao.deleteid(ob.getId());

                    notifyItemRemoved(position);
//                getApplicationContext()

                }
            });


            holder.infoName.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getAbsoluteAdapterPosition();
//                            holder.getAdapterPosition();

                    Log.e(TAG, "Brak OnLongClickListener = ");


                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    NavController navController = Navigation.findNavController(v);
                    NavDirections action = robac.pl.room_euro4.QrCodeFragmentDirections.actionQrCodeFragmentToCommentFragment(messageList.get(position));
                    navController.navigate(action);
//

//                    NavHostFragment.findNavController(obFrag).navigate(R.id.action_QrCodeFragment_to_CommentFragment,ob);


//             Toast.makeText(v.getContext(), "onLongClick -" +ob.getPojemnikiInfo()
//
//                     , Toast.LENGTH_SHORT).show();
//                Log.d("Info", "setOnLongClickListener" + v.getPivotX());
                    return true;
                }
            });
            holder.infoName.setOnGenericMotionListener(new View.OnGenericMotionListener() {
                @Override
                public boolean onGenericMotion(View v, MotionEvent event) {
                    Log.d("Info", "onGenericMotion=" + event.getSource());
                    return true;
                }
            });
        }


        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return The total number of items in this adapter.
         */

        @NonNull
        @Override
        public int getItemCount() {
            return messageList.size();
        }

        public List<Message> getMessageList() {
            return messageList;
        }

        public void setMessageList(List<Message> messageList) {
            this.messageList = messageList;
        }

        private Message showUserOptions(Message data, View v) {
            PojemnikiInfo obContent = data.getPojemnikiInfo();
            String[] options = {"Komentarz", "Powrót "};
            new AlertDialog.Builder(mcontext)
                    .setTitle("Wybrałeś " + data.getId())
                    .setCancelable(false)
                    .setNeutralButton(R.string.dialog_button, (dialog, which) -> dialog.dismiss())
                    .setMultiChoiceItems(obContent.getPojemniki(), obContent.getPojemnikiInfos(), new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            obContent.getPojemnikiInfos()[which] = isChecked;
                        }
                    }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Close Dialog

                    data.setPojemnikiInfo(obContent);
                    dialog.dismiss();

                    String s = null;
                    for (int i = 0; i < obContent.getPojemniki().length; i++) {
                        if (obContent.getPojemnikiInfos()[i]) {
                            if (s == null) {
                                s = obContent.getPojemniki()[i];
                            } else {
                                s += ", " + obContent.getPojemniki()[i];
                            }
                        }
                    }
                    s = s == null ? "" : s;

                    // Do something, for example: Call a method of Activity...
//                Toast.makeText(v.getContext(),"Base= " +data,Toast.LENGTH_SHORT).show();
//                Toast.makeText(v.getContext(),"You select " + MainActivity.Licznik+"-"+s+obContent,Toast.LENGTH_SHORT).show();
//                MainActivity.Licznik++;
                }

            })
                    .show();

            return data;
        }

        private void goToUser(Message ob) {
            Intent intent = new Intent(mcontext, NewMessageActivity.class);
            // TODO Show this was changed to ' "userId" '
            intent.putExtra("id", ob.getId());
            mcontext.startActivity(intent);
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        public class MessageViewHolder extends RecyclerView.ViewHolder {
            public TextView infoName;

            public ImageButton infoDeleteButton;

            public MessageViewHolder(@NonNull View v) {
                super(v);
                // Log.d("Info","OpPackageName="+ v.getContext().getOpPackageName());
//                Log.d("Info", "PackageResourcePath()=" + v.getContext());
                infoName = (TextView) v.findViewById(R.id.info_name);

            }

            public TextView getInfoName() {
                return infoName;
            }

            public void setInfoName(TextView infoName) {
                this.infoName = infoName;
            }

            public ImageButton getInfoDeleteButton() {
                return infoDeleteButton;
            }

            public void setInfoDeleteButton(ImageButton infoDeleteButton) {
                this.infoDeleteButton = infoDeleteButton;
            }
        }
    }
}