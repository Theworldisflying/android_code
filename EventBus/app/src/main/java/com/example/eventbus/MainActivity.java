package com.example.eventbus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        注册事件
        //可以自己实例化EventBus对象，但一般使用EventBus.getDefault()，根据post函数参数的类型，会自动调用订阅相应类型事件的函数
        EventBus.getDefault().register(this);

        textView = (TextView)findViewById(R.id.back_tv);

        Button secBtn = (Button)findViewById(R.id.sec_btn);
        secBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SecActivity.class));
            }
        });

        Button postBtn = (Button)findViewById(R.id.main_sec_sticky_btn);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //                发送粘性事件
                CustomEvent<String> customEvent = new CustomEvent<>(EventType.Sec.SEC_STICKY_BACKEVENT);
                customEvent.setData("粘性事件string");
                EventBus.getDefault().postSticky(customEvent);

                startActivity(new Intent(MainActivity.this, SecActivity.class));
            }
        });
    }
//    接收消息
    // 5种线程模式
    // POSTING MAIN  MAIN_ORDERED  BACKGROUND ASYNC
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public <T> void onEvent(CustomEvent<T> customEvent) {
        switch (customEvent.getFlag()){
            case EventType.Sec.SEC_BACKEVENT:
                String backMsg = (String)customEvent.getData();
                textView.setText(backMsg);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        当前活动销毁时解注EventBus，为防止内存泄漏
        EventBus.getDefault().unregister(this);
    }
}
