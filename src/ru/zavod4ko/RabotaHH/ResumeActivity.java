package ru.zavod4ko.RabotaHH;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import android.view.View;
import android.app.DatePickerDialog;
import java.util.Calendar;

public class ResumeActivity extends BaseWorkActivity {
    private TextView textBirthday, textSex, textReply;
    private EditText editFIO, editPosition, editSalary, editPhone, editEmail;
    private SharedPreferences sPref;
    private Button buttonSendClient;
    public static final int SEND_CLIENT = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resume);
        textBirthday = (TextView) findViewById(R.id.textBirthday);
        textSex = (TextView) findViewById(R.id.textSex);
        editFIO = (EditText) findViewById(R.id.editFIO);
        editPosition = (EditText) findViewById(R.id.editPosition);
        editSalary = (EditText) findViewById(R.id.editSalary);
        editPhone = (EditText) findViewById(R.id.editPhone);
        editEmail = (EditText) findViewById(R.id.editEmail);
        buttonSendClient = (Button) findViewById(R.id.buttonSendClient);
        textReply = (TextView) findViewById(R.id.textReply);
        //диалог работодателя устанавливаем в режим невидимости
        textReply.setVisibility(4);
        //обработчик нажатия на поле Дата Рождения
        textBirthday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setBirthday();
            }
        });

        //обработчик нажатия на поле Пол
        textSex.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setSex();
            }
        });

        //обработчик нажатия на кнопку Отправить резюме
        buttonSendClient.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendResume();
            }
        });
    }

    //диалоговое окно выбора Пола
    public void setSex() {
        final String[] items = {getString(R.string.man), getString(R.string.women)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                        textSex.setText(items[item]);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    //диалоговое окно выбора Даты рождения, по умолчанию выставляем текущую дату
    public void setBirthday() {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog (this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                textBirthday.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)); // выбор по умолчанию
        dpd.show();
    }

    //отправка данных во второе Activity с ожиданием результата
    public void sendResume() {
        Intent sendClient = new Intent(this, ReplyActivity.class);
        sendClient.putExtra("editFIO", editFIO.getText().toString());
        sendClient.putExtra("textBirthday", textBirthday.getText().toString());
        sendClient.putExtra("textSex", textSex.getText().toString());
        sendClient.putExtra("editPosition", editPosition.getText().toString());
        sendClient.putExtra("editSalary", editSalary.getText().toString());
        sendClient.putExtra("editPhone", editPhone.getText().toString());
        sendClient.putExtra("editEmail", editEmail.getText().toString());
        startActivityForResult(sendClient, SEND_CLIENT);
    }

    //обработка полученного результата от другого Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //делаем диалог работодателя видимым
        textReply.setVisibility(0);
        //получаем данные от другого Activity
        textReply.setText(data.getStringExtra("reply"));
    }

    //востанавливаем сохраненные данные
    protected void onResume() {
        super.onResume();
        sPref = getPreferences(MODE_PRIVATE);
        editFIO.setText(sPref.getString("editFIO", ""));
        textBirthday.setText(sPref.getString("textBirthday", ""));
        textSex.setText(sPref.getString("textSex", ""));
        editPosition.setText(sPref.getString("editPosition", ""));
        editSalary.setText(sPref.getString("editSalary", ""));
        editPhone.setText(sPref.getString("editPhone", ""));
        editEmail.setText(sPref.getString("editEmail", ""));
    }

    //при закрытии приложения или изменении Activity (поворот экрана) сохраняем введенные данные
    protected void onPause() {
        super.onPause();
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("editFIO", editFIO.getText().toString());
        ed.putString("textBirthday", textBirthday.getText().toString());
        ed.putString("textSex", textSex.getText().toString());
        ed.putString("editPosition", editPosition.getText().toString());
        ed.putString("editSalary", editSalary.getText().toString());
        ed.putString("editPhone", editPhone.getText().toString());
        ed.putString("editEmail", editEmail.getText().toString());
        ed.commit();
    }

    //при повороте экрана сохраняем ответ работодателя
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("reply", textReply.getText().toString());
    }

    //востанавливаем ответ работодателя после поворота экрана
    protected void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        String reply = outState.getString("reply");
        if(reply.equals("")) {
            textReply.setVisibility(4);
        } else {
            textReply.setVisibility(0);
            textReply.setText(reply);
        }
    }
}
