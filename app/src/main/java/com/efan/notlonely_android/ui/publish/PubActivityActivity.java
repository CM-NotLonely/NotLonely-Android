package com.efan.notlonely_android.ui.publish;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.efan.basecmlib.activity.BaseActivity;
import com.efan.basecmlib.annotate.ContentView;
import com.efan.basecmlib.annotate.OnClick;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.notlonely_android.R;
import com.efan.notlonely_android.config.APIConfig;
import com.efan.notlonely_android.utils.NetStateUtils;
import com.efan.notlonely_android.utils.ToastUtils;
import com.efan.request.RequestUtils;
import com.efan.request.callback.Callback;
import com.efan.request.response.Response;


@ContentView(id = R.layout.activity_pub_activity)
public class PubActivityActivity extends BaseActivity {

    @ViewInject(id = R.id.et_activity_name)
    private EditText et_activity_name;
    @ViewInject(id = R.id.et_activity_time)
    private EditText et_activity_time;
    @ViewInject(id = R.id.et_activity_location)
    private EditText et_activity_location;
    @ViewInject(id = R.id.et_activity_people)
    private EditText et_activity_people;
    @ViewInject(id = R.id.et_activity_money)
    private EditText et_activity_money;
    @ViewInject(id = R.id.et_activity_content)
    private EditText et_activity_content;

    private String activity_name;
    private String activity_time;
    private String activity_location;
    private String activity_people;
    private String activity_money;
    private String activity_content;


    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    @OnClick(value = {R.id.iv_back, R.id.bottombar})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bottombar:
                activity_name = et_activity_name.getText().toString();
                activity_time = et_activity_time.getText().toString();
                activity_location = et_activity_location.getText().toString();
                activity_people = et_activity_people.getText().toString();
                activity_money = et_activity_money.getText().toString();
                activity_content = et_activity_content.getText().toString();
                if (checkActivity(activity_name, activity_time, activity_location, activity_people, activity_money, activity_content)) {
                    if(!NetStateUtils.hasNetWorkConnection(PubActivityActivity.this)){
                        ToastUtils.show(PubActivityActivity.this,"请检查网络连接设置");
                        return;
                    }
                    RequestUtils.post()
                            .url(APIConfig.Creat_activity_default)
                            .addParams("title",activity_name)
                            .addParams("location",activity_location)
                            .addParams("cost",activity_money)
                            .addParams("details",activity_content)
                            .addParams("time",activity_time)
                            .addParams("count",activity_people)
                            .build()
                            .execute(new Callback() {
                                @Override
                                public void onError(Exception e) {

                                }

                                @Override
                                public void onResponse(Response response) {
                                    //返回成功后，跳转至该活动的那个页面
                                }
                            });
                }
                break;
        }

    }

    /**
     * 输入合法性检查
     * @param activity_name
     * @param activity_time
     * @param activity_location
     * @param activity_people
     * @param activity_money
     * @param activity_content
     * @return
     */
    private boolean checkActivity(String activity_name, String activity_time, String activity_location, String activity_people, String activity_money, String activity_content) {
        if(activity_name.equals("")||activity_time.equals("")||activity_location.equals("")||activity_people.equals("")||activity_money.equals("")||activity_content.equals("")){
            ToastUtils.show(getApplicationContext(), "请将活动信息补充完整，亲~", Toast.LENGTH_SHORT);
            return  false;
        }
        return true;
    }

}
