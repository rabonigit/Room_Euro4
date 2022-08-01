package robac.pl.room_euro4.model;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
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
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import robac.pl.room_euro4.R;
import robac.pl.room_euro4.dao.MessageDao;
import robac.pl.room_euro4.databinding.FragmentSecondBinding;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private static final String TAG = "MessageAdapter";
    private final CompositeDisposable disposables = new CompositeDisposable();
    HashMap<Integer, MessageViewHolder> holderlist;
    HashMap<Message, Integer> mapView;
    MessageDao messageDao;
    private List<Message> messageList;
    private Context mcontext;
   private FragmentSecondBinding binding;
//   private Fragment wsk;
//    final String[] animals = {"Horse", "Cow", "Camel", "Sheep", "Goat"};
//
//    final boolean[] checkedInfos = new boolean[]{false, false, false, false, false};

    public MessageAdapter(List<Message> messages, MessageDao messageDao,Context mcontext,FragmentSecondBinding binding) {
        this.messageList = messages;
        this.messageDao = messageDao;
        holderlist = new HashMap<>();
        mapView = new HashMap<>();
        this.mcontext = mcontext;
        this.binding=binding;

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

//        holder.animalName.setText(animal.getContent());
//        holder.animalName.setText(animal.getContent()+"-"+position+":"+animal.getId());
        holder.infoName.setText("" + wiadomosc.getId());
      //  holder.infoDeleteButton.setImageResource(wiadomosc.);
//        mapView.put(animal.getId(),position);
        Log.d(TAG, "mapView= " + wiadomosc.getId() + " " + position + "wynik=" + mapView.get(wiadomosc.getId()));

//        public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (!holderlist.containsKey(position)) {
            holderlist.put(position, holder);
        }

        int color = Color.GREEN;
        if (wiadomosc.getBdo()) {
            color = Color.CYAN;
        }

//        int color = Color.GREEN;
//        if(position % 2 == 0) {
//            color = Color.CYAN;
//        }
        holder.infoName.setBackgroundColor(color);


        holder.infoDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Message ob = messageList.get(position);

//                Toast.makeText( view.getContext(),"Usunąłem -"+ ob.getContent()+":"+ob.getId(), Toast.LENGTH_SHORT).show();
                messageList.remove(position);
//                mapView.remove(ob.getId());

                disposables.add(
                        messageDao.deleteid(ob.getId())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
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
                int position = holder.getAdapterPosition();
//FragmentTransaction obtran=wsk.getActivity().getSupportFragmentManager().beginTransaction();
//Fragment fragment = new KomentarzFragment();
//                obtran.replace(binding.messagesListView.getId(),fragment,"cos");

//                NavHostFragment.findNavController(QrCodeFragment.obFrag).navigate(R.id.action_QrCodeFragment_to_CommentFragment);


//                        FragmentTransaction obtran
//
//            Message ob=    showUserOptions(messageList.get(position),v);
//                NewMessageViewModel obzm=new NewMessageViewModel(messageDao);
//                obzm.updateMessage(ob);
//
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



    private Message showUserOptions(Message data,View v) {
PojemnikiInfo obContent=data.getPojemnikiInfo();
        String[] options = {"Komentarz", "Powrót "};
        new AlertDialog.Builder(mcontext)
                .setTitle("Wybrałeś "+data.getId())
                .setCancelable(false)
                .setNeutralButton(R.string.dialog_button, (dialog, which) -> dialog.dismiss())
                .setMultiChoiceItems(obContent.getPojemniki(), obContent.getPojemnikiInfos(), new DialogInterface.OnMultiChoiceClickListener()  {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        obContent.getPojemnikiInfos()[which] = isChecked;
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Close Dialog
                dialog.dismiss();

                String s= null;
                for(int i=0; i< obContent.getPojemniki().length;i++)  {
                    if(obContent.getPojemnikiInfos()[i]) {
                        if(s == null)  {
                            s = obContent.getPojemniki()[i];
                        } else {
                            s+= ", " + obContent.getPojemniki()[i];
                        }
                    }
                }
                s = s == null? "":s;

                // Do something, for example: Call a method of Activity...
//                Toast.makeText(v.getContext(),"Base= " +data,Toast.LENGTH_SHORT).show();
//                Toast.makeText(v.getContext(),"You select " + MainActivity.Licznik+"-"+s+obContent,Toast.LENGTH_SHORT).show();
//                MainActivity.Licznik++;
            }
        })
                .show();
        data.setPojemnikiInfo(obContent);
        return data;
    }
    private void goToUser(Message ob) {
        Intent intent = new Intent(mcontext, NewMessageActivity.class);
        // TODO Show this was changed to ' "userId" '
    intent.putExtra("id",ob.getId());
        mcontext.startActivity(intent);
    }
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView infoName;

        public ImageButton infoDeleteButton;

        @RequiresApi(api = Build.VERSION_CODES.P)
        public MessageViewHolder(View v) {
            super(v);
            // Log.d("Info","OpPackageName="+ v.getContext().getOpPackageName());
            Log.d("Info", "PackageResourcePath()=" + v.getParent());

        }


    }
}
