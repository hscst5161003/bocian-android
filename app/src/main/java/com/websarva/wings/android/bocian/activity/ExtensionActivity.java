package com.websarva.wings.android.bocian.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.websarva.wings.android.bocian.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ExtensionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extension);

        Spinner sp = findViewById(R.id.extension_spinar_hour);

        Litenner lt = new Litenner();
        sp.setOnItemSelectedListener(lt);

        TextView time = findViewById(R.id.extension_tv_remainingTime);

        time.setText(getNowDate());


//        Spinner mSpinner = (Spinner)findViewById(R.id.spinner);
//        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.spinner_1,R.layout.activity_extension);
//
//        adapter.setDropDownViewResource(R.layout.dropdown_custom);
//        mSpinner.setAdapter(adapter);

    }

    /**
     * 現在日時をyyyy/MM/dd HH:mm:ss形式で取得する.<br>
     */
    public static String getNowDate(){
        final DateFormat df = new SimpleDateFormat("HH:mm");
        final Date date = new Date(System.currentTimeMillis());
        return df.format(date);
    }


    private class ClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id){
                case R.id.extension_bt_confirm:
                    //確定ボタンのクリック時の処理
//                  DBアクセス
//                  ↓
//                  延長時間をupdateする。
//                  ↓
//                  update成功の旨のダイアログを表示する。
//                  ↓
//                  終了。
                    break;
                case R.id.extension_bt_cancel:
                    //キャンセルボタンのクリック時の処理
//                  Intent intent = new Intent(this,スタート画面(起動時の画面));
                    break;
            }
        }
    }

//(小倉)スピナー内のアイテムが選択されたときのリスナー
//(小倉)21時以降は延長できないので21時を選択した場合は「00」分に設定する
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

