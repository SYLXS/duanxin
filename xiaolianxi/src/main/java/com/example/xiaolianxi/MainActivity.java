package com.example.xiaolianxi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.MobSDK;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class MainActivity extends AppCompatActivity {

    private EventHandler eventHandler;
    private boolean boolShowInDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView get_sms=(TextView)findViewById(R.id.tv);
        // 如果希望在读取通信录的时候提示用户，可以添加下面的代码，并且必须在其他代码调用之前，否则不起作用；如果没这个需求，可以不加这行代码
        SMSSDK.setAskPermisionOnReadContact(boolShowInDialog);

        // 创建EventHandler对象
        // 处理你自己的逻辑
        MobSDK.init(this,"201176ddab4d0","f38cd35db894e56fbd988739d49fffc8");
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable) data;
                    String msg = throwable.getMessage();
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                } else {
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        // 处理你自己的逻辑
                    }
                }
            }
        };
get_sms.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        //打开注册界面
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(eventHandler);
        //显示注册的面板
        registerPage.show(MainActivity.this);

    }
});

        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler);
    }


    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }




}