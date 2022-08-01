package robac.pl.room_euro4.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import robac.pl.room_euro4.dao.MessageDao;

public class NewMessageViewModel extends ViewModel {
    private final static String TAG = "NewMessage";
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MessageDao messageDao;
    private final MutableLiveData<Boolean> mutableAddingState = new MutableLiveData<>();
    private final MutableLiveData<List<Message>> mutableLoadedMessage = new MutableLiveData<>();
    private final MutableLiveData<Message> mutableLoadedMessageS = new MutableLiveData<>();
    public LiveData<Boolean> addingState = mutableAddingState;
    public LiveData<List<Message>> loadedMessage = mutableLoadedMessage;
    public LiveData<Message> loadedMessageS = mutableLoadedMessageS;
    private Disposable addMessageDisposable;
    private Disposable updateMessageDisposable;


    public NewMessageViewModel(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    public void loadMessages() {
        disposables.add(
                messageDao.getAllRx()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                loadedMessage -> mutableLoadedMessage.setValue(loadedMessage),
                                error -> Log.e(TAG, "Couldn't load professions " + error)
                        )
        );
    }

    public   void getSelectedMessage(long userId) {
        disposables.add(
                messageDao.getSelectedMessage(userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                message -> mutableLoadedMessageS.setValue(message),
                                error -> Log.e(TAG, "Couldn't load wiadomość " + error)
                        )
        );
    }

    public void addMessage(Message ob) {
        if (addMessageDisposable != null && !addMessageDisposable.isDisposed())
            return;


        addMessageDisposable = messageDao.add(ob)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> mutableAddingState.setValue(true),
                        error -> {
                            Log.e(TAG, "Couldn't add user " + error);
                            mutableAddingState.setValue(false);
                        }
                );

        disposables.add(addMessageDisposable);
    }

    public void   updateMessage(Message ob) {
        if (updateMessageDisposable != null && !updateMessageDisposable.isDisposed())
            return;


        updateMessageDisposable = messageDao.update(ob)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            Log.e(TAG, "On update wiadomość id = " + ob.getId());
                            mutableAddingState.setValue(true);
                        },
                        error -> {
                            Log.e(TAG, "Couldn't update wiadomość " + error);
                            mutableAddingState.setValue(false);
                        }
                );
        disposables.add(updateMessageDisposable);
    }

    public  void updateMessage(String qrcode, String content, boolean isBdo, Message ob) {
        if (updateMessageDisposable != null && !updateMessageDisposable.isDisposed())
            return;

        ob.setQrcode(qrcode);
        ob.setContent(content);
        ob.setBdo(isBdo);

        updateMessageDisposable = messageDao.update(ob)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            Log.e(TAG, "On update wiadomość id = " + ob.getId());
                            mutableAddingState.setValue(true);
                        },
                        error -> {
                            Log.e(TAG, "Couldn't update wiadomość " + error);
                            mutableAddingState.setValue(false);
                        }
                );
        disposables.add(updateMessageDisposable);
    }
}
