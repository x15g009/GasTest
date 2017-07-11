package jp.ac.chiba_fjb.x15g009.gastest;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

class SendData{
    public int a;
    public int b;
}
class RecvData{
    public int anser;
}

public class MainActivity extends AppCompatActivity {

    final String GAS_URL="https://script.google.com/macros/s/AKfycbwTh-qqziynCcrQ9RPSp4ivSQ-sV8c8YNlaPcrn0nlNbgCRJ4A/exec";
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendGas(10,20);
    }
    void sendGas(final int a,final int b){
        //通信はスレッドで行う必要がある
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();

                //送信データの作成
                SendData sendData = new SendData();
                sendData.a = a;
                sendData.b = b;
                //GASに接続
                RecvData recvData = Json.send(GAS_URL,sendData,RecvData.class);
                //データの表示
                if(recvData != null)
                    setText(""+recvData.anser);
                else
                    setText("受信エラー");
            }
        };
        thread.start();
    }
    void setText(final String text){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                TextView textView = (TextView)findViewById(R.id.textView);
                textView.setText(text);
            }
        });
    }
}
