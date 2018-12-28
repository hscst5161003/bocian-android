package com.websarva.wings.android.bocian.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.websarva.wings.android.bocian.R;
import com.websarva.wings.android.bocian.fragment.ExtensionDialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;


public class ExtensionActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extension);

        /**@author Ogura Takumi
         * Spinner(時)のId取得
         * 21時が選択されたときのsetOnItemListenerのltのインスタンス化とspinnerにltの設定
         * 後ほど21時を選択した場合のSpinner(分)を不活性にするための処理使用するため
         */
        TextView useFinishTime = findViewById(R.id.extension_lastTime);
        TextView time = findViewById(R.id.extension_tv_remainingTime);
        Spinner sp = findViewById(R.id.extension_spinar_hour);
        Litenner lt = new Litenner();
        sp.setOnItemSelectedListener(lt);

        /**@author Ogura Takumi
         * 残り時間の欄に( 利用終了時刻 - 現在時刻 )を格納(フォーマットはHH:mm)
         */
        try {
            time.setText(remainingTime(changeDateData(useFinishTime.getText().toString()),getNowDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        /**@author Ogura Takumi
         * 確定ボタンの処理
         */
        findViewById(R.id.extension_bt_confirm).setOnClickListener(v -> {
            ExtensionDialogFragment extensionDialogFragment = new ExtensionDialogFragment();
            extensionDialogFragment.show(getSupportFragmentManager(),"ExtensionDialogFragment");
        });
        /**@author Ogura Takumi
         * キャンセルボタンの処理
         */
        findViewById(R.id.extension_bt_cancel).setOnClickListener( v -> finish());

    }

    /**@author Ogura Takumi
     * 現在日時をyyyy/MM/dd HH:mm形式で取得する.<br>
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDateTime getNowDate(){
        return LocalDateTime.now();
    }

    /**@author Ogura Takumi
     * @param  time
     * @return String型からLocalDate型に変換
     * @throws ParseException
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDateTime changeDateData(String time) throws ParseException {
        return LocalDateTime.parse(time,DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
    }

    /**
     * @author Ogura Takumi
     * @param from
     * @param to
     * duration型で二つの時間の差を測る(from,to)
     * LocalTime.MIDNIGHTはその日の深夜零時を指す(00:00)
     * それにdurationで測った差を足し差をHH:mmのフォーマットに変換する
     * @return remainingTime
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String remainingTime(LocalDateTime from, LocalDateTime to){
        Duration duration = Duration.between(from,to);          //二つの時間の差を測る
        LocalTime time = LocalTime.MIDNIGHT.plus(duration);    //(00:00)に差を足す
        String remainingTime = DateTimeFormatter.ofPattern("HH:mm").format(time);//(HH:mm)に変換する
        return remainingTime;
    }

    /**
     * @author Ogura Takumi
     * スピナー内のアイテムが選択されたときのリスナー
     * 21時以降は延長できないので21時を選択した場合は「00」分に設定する
     */
    private class Litenner implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // On selecting a spinner item
            Object item = parent.getItemAtPosition(position);
            Spinner sp2 = findViewById(R.id.extension_spinar_minutes);

            //「positon + 1」 は　lengthの開始オリジンが違うため
            if(getResources().getStringArray(R.array.spinner_1).length == position + 1){
                sp2.setSelection(0);
                sp2.setEnabled(false);
            }else{
                sp2.setEnabled(true);
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

}

