package cn.coolbhu.snailgo.activities.regulations;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheshouye.api.client.WeizhangClient;
import com.cheshouye.api.client.json.CityInfoJson;

import java.util.ArrayList;
import java.util.List;

import cn.coolbhu.snailgo.R;
import cn.coolbhu.snailgo.adapters.ListAdapter;
import cn.coolbhu.snailgo.beans.ListModel;

public class CityListActivity extends AppCompatActivity {
    private ListView lv_list;
    private ListAdapter mAdapter;

    private String provinceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.csy_titlebar);

        initToolbar();

        Bundle bundle = getIntent().getExtras();
        provinceName = bundle.getString("province_name");
        final String provinceId = bundle.getString("province_id");


        lv_list = (ListView) findViewById(R.id.activity_city_list_listview_citis);

        mAdapter = new ListAdapter(this, getData(provinceId));
        lv_list.setAdapter(mAdapter);

        lv_list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                TextView txt_name = (TextView) view.findViewById(R.id.txt_name);

                Intent intent = new Intent();
                // 设置cityName
                intent.putExtra("city_name", txt_name.getText());
                // 设置cityId
                intent.putExtra("city_id",
                        txt_name.getTag().toString());
                setResult(20, intent);
                finish();
            }
        });
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


    /**
     * title:获取数据
     *
     * @param provinceId
     * @return
     */
    private List<ListModel> getData(String provinceId) {
        List<ListModel> list = new ArrayList<ListModel>();

        //处理一下异常
        try {

            List<CityInfoJson> cityList = WeizhangClient.getCitys(Integer
                    .parseInt(provinceId));

            //开通数量提示
            TextView txtListTip = (TextView) findViewById(R.id.activity_city_list_textview_list_tip);
            txtListTip.setText(provinceName + "已开通" + cityList.size() + "个城市, 其它城市将陆续开放");

            for (CityInfoJson cityInfoJson : cityList) {
                String cityName = cityInfoJson.getCity_name();
                int cityId = cityInfoJson.getCity_id();

                ListModel model = new ListModel();
                model.setNameId(cityId);
                model.setTextName(cityName);
                list.add(model);
            }
        } catch (Exception ex) {

            ex.printStackTrace();

            Toast.makeText(this, R.string.cxy_error, Toast.LENGTH_SHORT).show();
        }

        return list;
    }
}
