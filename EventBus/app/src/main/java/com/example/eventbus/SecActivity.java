package com.example.eventbus;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class SecActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sec_activity);
        Button button = (Button)findViewById(R.id.sec_back_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                发送消息 这里发送的消息类型可以是任意对象，如：String List 等
                CustomEvent<String> customEvent = new CustomEvent<>(EventType.Sec.SEC_BACKEVENT);
                customEvent.setData("SecActivity活动返回String");
                EventBus.getDefault().post(customEvent);

                finish();
            }
        });

        textView = (TextView)findViewById(R.id.sec_receive_text);
        Button receive_btn = (Button)findViewById(R.id.sec_sticky_btn);
        receive_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ////要接收时开始注册。注：这里注册要使用SecActivity.this,不能只使用this，否则会崩溃
                EventBus.getDefault().register(SecActivity.this);
            }
        });

    }
    //    粘性事件接收消息
    //不需要先注册，也能接受到事件，也就是一个延迟注册的过程
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public <T> void onEvent(CustomEvent<T> customEvent) {
        switch (customEvent.getFlag()){
            case EventType.Sec.SEC_STICKY_BACKEVENT:
                String stickyMsg = (String)customEvent.getData();
                textView.setText(stickyMsg);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 获取粘性事件
        CustomEvent customEvent = EventBus.getDefault().getStickyEvent(CustomEvent.class);
        if (customEvent != null){
//            不需要时，删除粘性事件，使不再被传递
            EventBus.getDefault().removeStickyEvent(customEvent);
        }

        EventBus.getDefault().unregister(this);

    }
}
