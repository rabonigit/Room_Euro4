package robac.pl.room_euro4;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import robac.pl.room_euro4.model.Message;

public class KomentarzFragmentViewModel extends ViewModel {
    private MutableLiveData<Message> mchemessageOb;

    public KomentarzFragmentViewModel() {
        mchemessageOb =new MutableLiveData<Message>();

    }


    public MutableLiveData<Message> getMchemessageOb() {
        Log.d("model","onStart Dupax getMchemessageOb");
        return mchemessageOb;
    }

    public void setMchemessageOb(MutableLiveData<Message> mchemessageOb) {
        Log.d("model","onStart Dupax setMchemessageOb");
        this.mchemessageOb = mchemessageOb;
    }
}
