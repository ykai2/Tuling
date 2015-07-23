package com.example.ykai.tuling;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ykai.tuling.bean.ChatMessage;
import com.example.ykai.tuling.bean.ChatMessage.Type;
import com.example.ykai.tuling.utils.HttpUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.LogRecord;

public class MainActivity extends Activity {
    private ListView mMsgs;
    private ChatMessageAdapter mAdapter;
    private List<ChatMessage> mDatas;

    private EditText mInputMsg;
    private Button mSendMsg;
    private Handler mHandler=new Handler()
    {
        public void handleMessage(android.os.Message msg){
            ChatMessage fromMessage=(ChatMessage)msg.obj;
            mDatas.add(fromMessage);
            mAdapter.notifyDataSetChanged();
       //     Log.e("aaa", "hand: "+fromMessage.getMsg().toString());

        }


    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initView();
        initDatas();
        initListener();
    }

    private void initListener() {
        mSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final String toMsg=mInputMsg.getText().toString();
                if(TextUtils.isEmpty(toMsg)){
                    Toast.makeText(MainActivity.this,"发送消息不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                ChatMessage toMessage=new ChatMessage();
                toMessage.setDate(new Date());
                toMessage.setType(Type.OUTCOMING);
                toMessage.setMsg(toMsg);
                mDatas.add(toMessage);
                mAdapter.notifyDataSetChanged();
                mInputMsg.setText("");

                new Thread()
                {
                    @Override
                    public void run() {
                        ChatMessage fromMessage= HttpUtils.sendMessage(toMsg);
                        Message m=Message.obtain();
                        m.obj=fromMessage;
                 //       Log.e("aaa", "run: "+fromMessage.getMsg());
                        mHandler.sendMessage(m);

                    }
                }.start();



            }
        });
    }

    private void initDatas() {
        mDatas=new ArrayList<ChatMessage>();
        mDatas.add(new ChatMessage("hi，我是Tuling",Type.INCOMING,new Date()));
        mAdapter=new ChatMessageAdapter(this,mDatas);
        mMsgs.setAdapter(mAdapter);
    }

    private void initView() {
        mMsgs=(ListView)findViewById(R.id.id_listview_msgs);
        mInputMsg=(EditText)findViewById(R.id.id_input_msg);
        mSendMsg=(Button)findViewById(R.id.id_send_msg);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
