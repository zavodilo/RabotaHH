package ru.zavod4ko.RabotaHH;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

//базовое Activity приложения
public class BaseWorkActivity extends Activity {
    protected static final int ABOUT =101;

    //меню приложения
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, ABOUT, Menu.NONE, getString(R.string.about));
        return (super.onCreateOptionsMenu(menu));
    }

    //выбор пункта меню
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==ABOUT) {
            showAboutDialog();
        } else {
            return false;
        }
        return true;
    }

    //отображение диалога из пункта меню приложения
    public void showAboutDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage(getString(R.string.info));
        Dialog dialog = dialogBuilder.create();
        dialog.show();
    }

    //всплывающие сообщения
    protected void toast(String textShow) {
        Toast.makeText(getBaseContext(), textShow, Toast.LENGTH_SHORT).show();
    }

}
