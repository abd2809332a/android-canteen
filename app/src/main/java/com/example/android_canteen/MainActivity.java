package com.example.android_canteen;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.example.android_canteen.base.BaseConstant;
import com.example.android_canteen.dialog.DialogpostEvent;
import com.example.android_canteen.requestbean.FindfoodtodayListReq;
import com.example.android_canteen.requestbean.FoodtodaychucReq;
import com.example.android_canteen.responbean.FindfoodtodayListRsp;
import com.example.android_canteen.responbean.FoodtodayRsp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    ArrayList<String> image = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<Integer> money = new ArrayList<>();
    ArrayList<Integer> nub = new ArrayList<>();
    @BindView(R.id.gridview)
    GridView gridview;
    @BindView(R.id.yixuan)
    TextView yixuan;
    @BindView(R.id.heji)
    TextView heji;
    @BindView(R.id.quxiao)
    Button quxiao;
    @BindView(R.id.quedingchucan)
    Button quedingchucan;
    @BindView(R.id.holdeat)
    LinearLayout holdeat;
    @BindView(R.id.nopeople)
    LinearLayout nopeople;
    DisplayManager mDisplayManager;//屏幕管理类
    Display[] displays;//屏幕数组
    DifferentDislay mPresentation;
    TextView nametxt;
   int orderId=0;
   Button src_over1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        intview();

        //获取宽高比例
//        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
//        Log.i("Resolution",mDisplayMetrics.widthPixels+"____"+mDisplayMetrics.heightPixels+"____"+mDisplayMetrics.density+"___"+mDisplayMetrics.densityDpi+"--");

        holdeat.setVisibility(View.GONE);
        nopeople.setVisibility(View.VISIBLE);


        EventBus.getDefault().register(this);
        showdifferent();

        getDatalouc();

    }
    private void showdifferent(){
        mDisplayManager = (DisplayManager)MainActivity.this.getSystemService(Context.DISPLAY_SERVICE);

        displays =mDisplayManager.getDisplays();
//        Toast.makeText(MainActivity.this, "window 获取屏幕数为："+displays.length, Toast.LENGTH_SHORT).show();
        if (displays.length<2){

        }
        else {
             mPresentation = new DifferentDislay(MainActivity.this, displays[1]);//displays[1]是副屏
            mPresentation.getWindow().setType(
                    WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            mPresentation.show();
        }

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(DialogpostEvent messageEvent) {
        holdeat.setVisibility(View.VISIBLE);
        nopeople.setVisibility(View.GONE);
        Log.i("event++","1");
        String yixuantext ="";
        int hejimoney=0;
        gridview.setAdapter(new Myadapter(messageEvent.image,messageEvent.name, messageEvent.money, messageEvent.nub));
        for (int i=0;i<messageEvent.image.size();i++){
            yixuantext=yixuantext+messageEvent.name.get(i)+messageEvent.nub.get(i)+"份，";
            hejimoney=hejimoney+(messageEvent.money.get(i)*messageEvent.nub.get(i));
        }
        nametxt.setText(messageEvent.name_pop+"   等餐中");
        yixuan.setText("已选："+yixuantext.toString());
        heji.setText("合计：¥"+hejimoney/100);
        orderId=messageEvent.orderId;


    }


    private void intview(){
        src_over1=findViewById(R.id.src_over1);
        nametxt=findViewById(R.id.nametxt);
        holdeat=findViewById(R.id.holdeat);
        nopeople=findViewById(R.id.nopeople);
        gridview=findViewById(R.id.gridview);
        yixuan=findViewById(R.id.yixuan);
        heji=findViewById(R.id.heji);
        quxiao=findViewById(R.id.quxiao);
        quedingchucan=findViewById(R.id.quedingchucan);
        quxiao.setOnClickListener(this);
        quedingchucan.setOnClickListener(this);
        src_over1.setOnClickListener(this);
    }

    void getDatalouc() {
        FindfoodtodayListReq req = new FindfoodtodayListReq();
        req.officeId = SpDate.officeId;
        req.roomId=SpDate.roomId;

        MyApp.getInstance().requestData(this, req, new listenerlouc(), new errorlouc());
    }
    void getqdcc() {
        FoodtodaychucReq req = new FoodtodaychucReq();
        req.officeId = SpDate.officeId;
        req.roomId=SpDate.roomId;
        req.orderId=orderId;
        MyApp.getInstance().requestData(this, req, new listenerlouc5(), new errorlouc());
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.quxiao:
                nopeople.setVisibility(View.VISIBLE);
                mPresentation.quxiao();
                holdeat.setVisibility(View.GONE);
                break;
            case R.id.quedingchucan:
                if (orderId==0){
                    ToastUtils.showShort("暂时无人点餐");
                }else{
                    getqdcc();
                }
                break;
            case  R.id.src_over1:
                finish();
                break;
        }
    }

    class listenerlouc implements Response.Listener<FindfoodtodayListRsp> {

        @Override
        public void onResponse(FindfoodtodayListRsp rsp) {

            if (rsp.status == BaseConstant.REQUEST_SUCCES) {

                for (int i = 0; i < rsp.data.size(); i++) {
                    image.add(rsp.data.get(i).image);
                    name.add(rsp.data.get(i).name);
                    money.add(rsp.data.get(i).price);
                    nub.add(rsp.data.get(i).allowance);
                }
                gridview.setAdapter(new Myadapter(image, name, money, nub));
//                mPresentation.setadapter(image,name, money, nub);
            }
        }
    }
    class listenerlouc5 implements Response.Listener<FoodtodayRsp> {

        @Override
        public void onResponse(FoodtodayRsp rsp) {

            if (rsp.status == BaseConstant.REQUEST_SUCCES) {
                nopeople.setVisibility(View.VISIBLE);
                mPresentation.quxiao();
                holdeat.setVisibility(View.GONE);
                ToastUtils.showShort("出餐成功");
//                mPresentation.setadapter(image,name, money, nub);
            }
            else{
                ToastUtils.showShort(rsp.info);
            }
        }
    }

    public  void showgridview(ArrayList<String>image,ArrayList<String>name,ArrayList<Integer>money,ArrayList<Integer>nub){
        gridview.setAdapter(new Myadapter(image, name, money, nub));
    }
    class errorlouc implements Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }
    }
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public class Myadapter extends BaseAdapter  //listview 适配器
    {
        private ArrayList<String> b1;
        private ArrayList<String> b2;
        private ArrayList<Integer> b3;
        private ArrayList<Integer> b4;


        private LayoutInflater inflater;

        public Myadapter() {

        }

        public Myadapter(ArrayList<String> b1, ArrayList<String> b2, ArrayList<Integer> b3, ArrayList<Integer> b4
        ) {
            this.b1 = b1;
            this.b2 = b2;
            this.b3 = b3;
            this.b4 = b4;


        }

        public int getCount() {

            return b1.size();
        }

        public Object getItem(int arg0) {

            return null;
        }

        public long getItemId(int arg0) {

            return 0;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {//添加适配器内容
            inflater = LayoutInflater.from(MainActivity.this);
            convertView = inflater.inflate(R.layout.layout_mainfgird, null);
            ImageView grd_image = convertView.findViewById(R.id.grd_image);
            TextView grd_name = convertView.findViewById(R.id.grd_name);
            TextView grd_money = convertView.findViewById(R.id.grd_money);
            TextView grd_num = convertView.findViewById(R.id.grd_num);
//
            Glide.with(MainActivity.this).load(b1.get(position)).into(grd_image);
            grd_name.setText(b2.get(position));
            grd_money.setText("¥"+b3.get(position)/100);
            grd_num.setText(b4.get(position)+"份");
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() //listview 按钮实现
            {
                public void onItemClick(AdapterView<?> parent, View view,
                                        final int position, long id) {

                }

            });

            return convertView;
        }
    }
}
