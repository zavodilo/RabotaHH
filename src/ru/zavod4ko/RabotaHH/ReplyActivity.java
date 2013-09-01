package ru.zavod4ko.RabotaHH;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ReplyActivity extends BaseWorkActivity {
    private TextView textBirthday, textSex, textFIO, textPosition, textSalary, textPhone, textEmail;
    private EditText editReply;
    private SharedPreferences sPref;
    private Button buttonSendReply;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reply);
        textBirthday = (TextView) findViewById(R.id.textBirthday);
        textSex = (TextView) findViewById(R.id.textSex);
        textFIO = (TextView) findViewById(R.id.textFIO);
        textPosition = (TextView) findViewById(R.id.textPosition);
        textSalary = (TextView) findViewById(R.id.textSalary);
        textPhone = (TextView) findViewById(R.id.textPhone);
        textEmail = (TextView) findViewById(R.id.textEmail);
        editReply = (EditText) findViewById(R.id.editReply);
        buttonSendReply = (Button) findViewById(R.id.buttonSendReply);

        //загружаем данные из первого Activity
        //Текстовые надписи, с всей информацией, полученной при вводе с первого Activity
        textBirthday.setText(getIntent().getStringExtra("textBirthday"));
        textSex.setText(getIntent().getStringExtra("textSex"));
        textFIO.setText(getIntent().getStringExtra("editFIO"));
        textPosition.setText(getIntent().getStringExtra("editPosition"));
        textSalary.setText(getIntent().getStringExtra("editSalary"));
        textPhone.setText(getIntent().getStringExtra("editPhone"));
        textEmail.setText(getIntent().getStringExtra("editEmail"));

        //обработчик нажатия на кнопку Отправить ответ
        buttonSendReply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //проверяем заполнял ли работодатель поле
                if(!editReply.getText().toString().equals(""))
                    sendReply();
            }
        });

        //обработчик нажатия на поле телефон и вызов приложения для звонков
        textPhone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+textPhone.getText().toString()));
                startActivity(intent);
            }
        });

        //обработчик нажатия на поле e-mail и вызов приложения для отправки почты
        textEmail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                // Кому
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                        new String[]{textEmail.getText().toString()});
                // Отправка
                startActivity(Intent.createChooser(emailIntent, "Отправка письма..."));
            }
        });
    }

    //отправка ответа работодателя в первое Activity
    public void sendReply() {
        Intent intent = new Intent();
        intent.putExtra("reply", editReply.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }


}
