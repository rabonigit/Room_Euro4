package robac.pl.room_euro4;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import robac.pl.room_euro4.databinding.FragmentSentInfoBinding;
import robac.pl.room_euro4.model.InfoSent;


public class sentInfoFragment extends Fragment {
    private static final String TAG = "sentInfoFragment";
    FragmentSentInfoBinding    binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    public sentInfoFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
//    public static sentInfoFragment newInstance(String param1, String param2) {
//        sentInfoFragment fragment = new sentInfoFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSentInfoBinding.inflate(inflater, container, false);
        // TODO: Rename and change types of parameters
        //    private String mParam1;
        //    private String mParam2;
        InfoSent obSentInfo = sentInfoFragmentArgs.fromBundle(getArguments()).getSzczegolySent();
        Log.d(TAG, "getArguments()= " + getArguments() + "\n" + obSentInfo);
        binding.textSentNumber.setText(obSentInfo.getSentNumber());
        binding.textKodSent.setText("Kod Odpadu = \t"+obSentInfo.getKodOdpadu());
        binding.textSentMasa.setText("Masa Odpadu =\t"+obSentInfo.getMasaOdpadu().toString()+" Mg.");
        binding.textSentNazwaKlienta.setText("Nazwa Klienta =\t"+obSentInfo.getNazwaKlienta());
        binding.textSentKierowca.setText("Kierowca =\t"+obSentInfo.getKierowca());
        binding.textSentNrZlecMarketingowego.setText("Nr Zlec Marketingowego=\t"+obSentInfo.getNumerZleceniaMarketingowego());
        binding.textSentNrZlecSam.setText("Nr Zlec Samochodowego=\t"+obSentInfo.getNumerZleceniaSamochodowego());
        return binding.getRoot();
//        return inflater.inflate(R.layout.fragment_sent_info, container, false);
    }
}