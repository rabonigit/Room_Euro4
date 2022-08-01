package robac.pl.room_euro4.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class PojemnikiInfo  extends ViewModel {

    private MutableLiveData<List<PojemnikStatusInfo>> stany;

    public LiveData<List<PojemnikStatusInfo>> getStany(List<PojemnikStatusInfo> xx) {
        if (stany == null) {
            stany = new MutableLiveData<List<PojemnikStatusInfo>>();
            getpojemnikStatusInfoList(xx);
        }
        return stany;
    }
    String[] Pojemniki = {"Prawidłowy", "Uszkodzony", "Demolka", "Uszkodzona Plandeka", "Uszkodzone kółka", "Wymiana tylko poj"};
    boolean[] PojemnikiInfos = new boolean[]{false, false, false, false, false, false};

    public PojemnikiInfo() {
    }

    public PojemnikiInfo(boolean[] pojemnikiInfos) {
        this.PojemnikiInfos = pojemnikiInfos;
        this.Pojemniki = new String[]{"Prawidłowy", "Uszkodzony", "Demolka", "Uszkodzona Plandeka", "Uszkodzone kółka", "Wymina tylko poj"};
    }

    public PojemnikiInfo(String[] Pojemniki, boolean[] PojemnikiInfos) {
        this.Pojemniki = Pojemniki;
        this.PojemnikiInfos = PojemnikiInfos;
    }

    public String[] getPojemniki() {
        return Pojemniki;
    }

    public void setPojemniki(String[] pojemniki) {
        Pojemniki = pojemniki;
    }

    public boolean[] getPojemnikiInfos() {
        return PojemnikiInfos;
    }

    public void setPojemnikiInfos(boolean[] pojemnikiInfos) {
        PojemnikiInfos = pojemnikiInfos;
    }

    public void setPojemnikiInfos(int poz, boolean wyb) {

        PojemnikiInfos[poz < PojemnikiInfos.length ? poz : 0] = poz < PojemnikiInfos.length && wyb;
    }

    public void setPojemnikStatus(PojemnikStatusInfo ob,int poz)
    {
        PojemnikiInfos[poz] =ob.getWybor();
    }

    public List<PojemnikStatusInfo> getpojemnikStatusInfoList() {
        List<PojemnikStatusInfo> wynik = new ArrayList<>();

        for (int i=0;i<Pojemniki.length;i++)
        {
            wynik.add(new PojemnikStatusInfo(Pojemniki[i],PojemnikiInfos[i]))  ;

        }

        return wynik;
    }
    public List<PojemnikStatusInfo> getpojemnikStatusInfoList(List<PojemnikStatusInfo> wynik) {


        return wynik;
    }
    @Override
    public String toString() {

        String s = null;
        for (int i = 0; i < getPojemniki().length; i++) {
            if (getPojemnikiInfos()[i]) {
                if (s == null) {
                    s = getPojemniki()[i];
                } else {
                    s += ", " + getPojemniki()[i];
                }
            }
        }
        s = s == null ? "" : s;
        return ("" + s);
    }

}
