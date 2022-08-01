package robac.pl.room_euro4.model;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import io.reactivex.Maybe;
import robac.pl.room_euro4.MainActivity;
import robac.pl.room_euro4.R;

public class NewMessageActivity extends AppCompatActivity {
    private NewMessageViewModel viewModel;
    private Spinner professionDropdown;
    private EditText qrCode;
    private EditText newContent;
    private CheckBox newIsBDO;
    private Button save;
    private boolean ChangeBDO=false;
    private Maybe<Message> changeob;
    private long Id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);
        bindViews();
        viewModel = new NewMessageViewModel(MainActivity.messageDao);

        Id = getIntent().getLongExtra("Id", 0);

    }


    private void updateMessages(List<Message> messages) {
        String[] qrCode = new String[messages.size()];
        for (int i = 0; i < messages.size(); i++)
            qrCode[i] = messages.get(i).getQrcode();

        professionDropdown.setAdapter(
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, qrCode)
        );
    }
    private void bindViews() {
        qrCode = findViewById(R.id.qrCode);
        newContent = findViewById(R.id.newContent);
        newIsBDO = findViewById(R.id.newIsBDO);

        save = findViewById(R.id.saveAction);
//        save.setOnClickListener(view -> addMessage(changeob));
       
    }

    private void addMessage(Message changeob) {
        if (!ChangeBDO) {
            viewModel.updateMessage(qrCode.getText().toString(),newContent.getText().toString(),newIsBDO.isChecked(),changeob);


            // TODO Missing 'else' word
        } else {
            viewModel.addMessage(changeob);

    }
}}