package com.example.android_canteen;

import android.app.Presentation;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.android_canteen.base.BaseConstant;
import com.example.android_canteen.dialog.DialogpostEvent;
import com.example.android_canteen.requestbean.DiffrrentpostListReq;
import com.example.android_canteen.requestbean.DiffrrentqccxReq;
import com.example.android_canteen.requestbean.FindfoodtodayListReq;
import com.example.android_canteen.responbean.DifferentpostListRsp;
import com.example.android_canteen.responbean.DifferentpostcxtRsp;
import com.example.android_canteen.responbean.FindfoodtodayListRsp;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
public class DifferentDislay extends Presentation implements View.OnClickListener{
    Context context;
    Button qc;
    GridView gridview,gridview_cc;
    //下面弹框，出餐框，点餐端
    LinearLayout popwindow,holdeat_cc,holdeat;
    TextView yixuan,heji,dengcanid;
    Button qdcc;
    TextView nametext;
    ImageView peopimage;
    List<DiffrrentpostListReq.MenuListBean> menuList = new ArrayList<>();
    public DifferentDislay(Context outerContext, Display display) {
        super(outerContext,display);
        context=outerContext;
    }
    /**
     * 自定义Dialog监听器
     */
    private PriorityListener listener;
    public interface PriorityListener {
        /**
         * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示
         */
        public void refreshPriorityUI(ArrayList<String> string);
    }


    public Handler getHander() {
        return mHandler;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    setGridview();
                    break;
            }
        }
    };
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.diffrentdisplay_basket);

        intview(); //界面初始化
        getDatalouc();
    }
    public void  intview(){
        gridview=findViewById(R.id.gridview);
        qc=findViewById(R.id.qc);
        qc.setOnClickListener(this);
        popwindow=findViewById(R.id.popwindow);
        yixuan=findViewById(R.id.yixuan);
        heji=findViewById(R.id.heji);
        qdcc=findViewById(R.id.qdcc);
        qdcc.setOnClickListener(this);
        holdeat_cc=findViewById(R.id.holdeat_cc);
        dengcanid=findViewById(R.id.dengcanid);
        gridview_cc=findViewById(R.id.gridview_cc);
        holdeat=findViewById(R.id.holdeat);
        peopimage=findViewById(R.id.peopimage);
        nametext=findViewById(R.id.nametext);
        popwindow.setVisibility(View.GONE);
        holdeat.setVisibility(View.VISIBLE);
        holdeat_cc.setVisibility(View.GONE);
    }
    public void quxiao(){
        popwindow.setVisibility(View.GONE);
        holdeat.setVisibility(View.VISIBLE);
        holdeat_cc.setVisibility(View.GONE);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qc:
                postdifferentaa();
                break;

            case R.id.qdcc:

                postcc();//提交菜单

                break;
        }
    }


    public void postcc(){
        popwindow.setVisibility(View.GONE);

        menuList = new ArrayList<>();
        for (int i=0;i<name.size();i++){
            if (num.get(i)!=0){
                DiffrrentpostListReq.MenuListBean bean=new  DiffrrentpostListReq.MenuListBean();
                try {
                    bean.foodName=URLEncoder.encode(name.get(i), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                bean.id=id.get(i);
                bean.num=num.get(i);
                menuList.add(bean);
            }
        }

        getDatalouc(); //重新访问gridview 展示
        postdifferent();  // 提交订单

    }
    public static String userid="216594bfd73b4faa81e72d54b3d0abe5";
    public void postdifferentaa(){
        DiffrrentqccxReq req = new DiffrrentqccxReq();
        req.officeId = SpDate.officeId;
        req.roomId=SpDate.roomId;
        req.userId=userid;
        req.type=2;
        MyApp.getInstance().requestData(this, req, new listenerlouc5(), new errorlouc());
    }
    public void postdifferent(){
        DiffrrentpostListReq req = new DiffrrentpostListReq();
        req.officeId = SpDate.officeId;
        req.roomId=SpDate.roomId;
        req.userId=userid;
        req.type=2;
        req.menuList=menuList;
        MyApp.getInstance().requestData(this, req, new listenerlouc2(), new errorlouc());
    }
    ArrayList<String> image = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<Integer> money = new ArrayList<>();
    ArrayList<Integer> nub = new ArrayList<>();
    ArrayList<Integer> num = new ArrayList<>();
    ArrayList<Integer> id=new ArrayList<>();
    void getDatalouc() {
        FindfoodtodayListReq req = new FindfoodtodayListReq();
        req.officeId = SpDate.officeId;
        req.roomId=SpDate.roomId;

        MyApp.getInstance().requestData(this, req, new listenerlouc(), new errorlouc());
    }

    class listenerlouc2 implements Response.Listener<DifferentpostListRsp> {

        @Override
        public void onResponse(DifferentpostListRsp rsp) {

            if (rsp.status==10000){
                popwindow.setVisibility(View.GONE);
                holdeat.setVisibility(View.GONE);
                holdeat_cc.setVisibility(View.VISIBLE);
                nametext.setText(rsp.data.name);
                Glide.with(context).load(rsp.data.photo)
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(peopimage);
                ArrayList<String> imagecc=new ArrayList<>();
                ArrayList<String> namecc=new ArrayList<>();
                ArrayList<Integer> moneycc=new ArrayList<>();
                ArrayList<Integer> nubcc=new ArrayList<>();
                for (int i=0;i<rsp.data.menuList.size();i++){
                    imagecc.add(rsp.data.menuList.get(i).image);
                    namecc.add(rsp.data.menuList.get(i).foodName);
                    moneycc.add(rsp.data.menuList.get(i).price);
                    nubcc.add(rsp.data.menuList.get(i).number);
                }
                gridview_cc.setAdapter(new Ccadapter(imagecc,namecc,moneycc,nubcc));
                DialogpostEvent minaEventType = new DialogpostEvent();
                minaEventType.image=imagecc;
                minaEventType.name=namecc;
                minaEventType.money=moneycc;
                minaEventType.nub=nubcc;
                minaEventType.name_pop=rsp.data.name;
                minaEventType.orderId=rsp.data.orderId;
                EventBus.getDefault().post(minaEventType);
                Log.e("TAG", "Received: " + JSON.toJSON(minaEventType));
            }
            else {
                ToastUtils.showShort(rsp.info);
            }
        }
        }
    class listenerlouc5 implements Response.Listener<DifferentpostcxtRsp> {

        @Override
        public void onResponse(DifferentpostcxtRsp rsp) {

            if (rsp.status==10000){
                popwindow.setVisibility(View.GONE);
                holdeat.setVisibility(View.GONE);
                holdeat_cc.setVisibility(View.VISIBLE);
                nametext.setText(rsp.data.name);
                Glide.with(context).load(rsp.data.photo)
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(peopimage);
                ArrayList<String> imagecc=new ArrayList<>();
                ArrayList<String> namecc=new ArrayList<>();
                ArrayList<Integer> moneycc=new ArrayList<>();
                ArrayList<Integer> nubcc=new ArrayList<>();
                for (int i=0;i<rsp.data.orderDetailList.size();i++){
                    imagecc.add(rsp.data.orderDetailList.get(i).image);
                    namecc.add(rsp.data.orderDetailList.get(i).foodName);
                    moneycc.add(rsp.data.orderDetailList.get(i).price);
                    nubcc.add(rsp.data.orderDetailList.get(i).number);
                }
                gridview_cc.setAdapter(new Ccadapter(imagecc,namecc,moneycc,nubcc));
                DialogpostEvent minaEventType = new DialogpostEvent();
                minaEventType.image=imagecc;
                minaEventType.name=namecc;
                minaEventType.money=moneycc;
                minaEventType.nub=nubcc;
                minaEventType.name_pop=rsp.data.name;
                minaEventType.orderId=rsp.data.orderId;
                EventBus.getDefault().post(minaEventType);
                Log.e("TAG", "Received: " + JSON.toJSON(minaEventType));
            }
            else {
                ToastUtils.showShort(rsp.info);
            }
        }
    }
    class listenerlouc implements Response.Listener<FindfoodtodayListRsp> {

        @Override
        public void onResponse(FindfoodtodayListRsp rsp) {
            if (rsp.status == BaseConstant.REQUEST_SUCCES) {
                image=new ArrayList<>();
                name=new ArrayList<>();
                money=new ArrayList<>();
                nub=new ArrayList<>();
                num=new ArrayList<>();
                id=new ArrayList<>();
                for (int i = 0; i < rsp.data.size(); i++) {

                    image.add(rsp.data.get(i).image);
                    name.add(rsp.data.get(i).name);
                    money.add(rsp.data.get(i).price);
                    nub.add(rsp.data.get(i).allowance);
                    num.add(0);
                    id.add(rsp.data.get(i).id);
                }
                gridview.setAdapter(new Myadapter(image, name, money, nub,num));
            }
        }
    }
//    public void setadapter(ArrayList<String> image,ArrayList<String> name,ArrayList<Integer> money,ArrayList<Integer> nub){
//        gridview.setAdapter(new Myadapter(image, name, money, nub,num));
//    }
    public void setGridview(){
        gridview.setAdapter(new Myadapter(image, name, money, nub,num));
        String yixuantext ="";
        int hejimoney=0;
        for (int i=0;i<image.size();i++){
            if (num.get(i)!=0) {
                yixuantext = yixuantext + name.get(i) + num.get(i) + "份，";
                hejimoney = hejimoney + (money.get(i) * num.get(i));
            }
        }
        if (hejimoney==0){
            popwindow.setVisibility(View.GONE);
        }
        yixuan.setText("已选："+yixuantext.toString());
        heji.setText("合计：¥"+hejimoney/100);

    }

    class errorlouc implements Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError volleyError) {

            Log.i("BAOCUO","baocuo");
        }
    }



    public class Myadapter extends BaseAdapter  //listview 适配器
    {
        private ArrayList<String> b1;
        private ArrayList<String> b2;
        private ArrayList<Integer> b3;
        private ArrayList<Integer> b4;
        private ArrayList<Integer> b5;

        private LayoutInflater inflater;

        public Myadapter() {

        }

        public Myadapter(ArrayList<String> b1, ArrayList<String> b2, ArrayList<Integer> b3, ArrayList<Integer> b4, ArrayList<Integer> b5
        ) {
            this.b1 = b1;
            this.b2 = b2;
            this.b3 = b3;
            this.b4 = b4;
            this.b5 = b5;


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
            inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.layout_different, null);
            ImageView grd_image = convertView.findViewById(R.id.grd_image_dif);
            TextView grd_name = convertView.findViewById(R.id.grd_name_dif);
            TextView grd_money = convertView.findViewById(R.id.grd_money_dif);
            TextView grd_num = convertView.findViewById(R.id.grd_num_dif);
            Button ajj = convertView.findViewById(R.id.ajj);
            Button add=convertView.findViewById(R.id.add);
            TextView textView = convertView.findViewById(R.id.textview);
            Glide.with(getContext()).load(b1.get(position)).into(grd_image);
            grd_name.setText(b2.get(position));
            grd_money.setText("¥"+b3.get(position)/100);
            grd_num.setText("剩余"+b4.get(position)+"份");

            if (b5.get(position)==0){
                textView.setVisibility(View.GONE);
                ajj.setVisibility(View.GONE);
            }
            else{
                textView.setText(b5.get(position)+"");
                textView.setVisibility(View.VISIBLE);
                ajj.setVisibility(View.VISIBLE);
            }
            ajj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ToastUtils.showShort(position+"");
                    num.set(position,num.get(position)-1);
                    Message msg = new Message();
                    msg.what = 2;
                    mHandler.sendMessage(msg);

                }
            });
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ToastUtils.showShort(position+"");
                    num.set(position,num.get(position)+1);
                    Message msg = new Message();
                    msg.what = 2;
                    mHandler.sendMessage(msg);
                    popwindow.setVisibility(View.VISIBLE);
                }
            });


            return convertView;
        }
    }
    public class Ccadapter extends BaseAdapter  //listview 适配器
    {
        private ArrayList<String> b1;
        private ArrayList<String> b2;
        private ArrayList<Integer> b3;
        private ArrayList<Integer> b4;


        private LayoutInflater inflater;

        public Ccadapter() {

        }

        public Ccadapter(ArrayList<String> b1, ArrayList<String> b2, ArrayList<Integer> b3, ArrayList<Integer> b4
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
            inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.layout_mainfgird, null);
            ImageView grd_image = convertView.findViewById(R.id.grd_image);
            TextView grd_name = convertView.findViewById(R.id.grd_name);
            TextView grd_money = convertView.findViewById(R.id.grd_money);
            TextView grd_num = convertView.findViewById(R.id.grd_num);
//
            Glide.with(context).load(b1.get(position)).into(grd_image);
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
