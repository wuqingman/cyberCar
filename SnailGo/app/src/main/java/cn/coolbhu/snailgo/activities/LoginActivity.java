package cn.coolbhu.snailgo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cn.coolbhu.snailgo.MyApplication;
import cn.coolbhu.snailgo.R;
import cn.coolbhu.snailgo.fragments.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //优化跳转，如果登陆成功了的话就没有必要再登陆了。
        if (MyApplication.isLoginSucceed) {

            startActivity(new Intent(this, MainActivity.class));

            this.finish();
        }

        setContentView(R.layout.activity_login);

        initToolbar();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_login_framelayout, LoginFragment.newInstance()).commit();
    }

    //initToolbar
    private void initToolbar() {

        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar);

        setSupportActionBar(mToolbar);

        ActionBar mActionBar = getSupportActionBar();

        if (mActionBar != null) {

            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
