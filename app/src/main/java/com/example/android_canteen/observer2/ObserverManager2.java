package com.example.android_canteen.observer2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/28.
 */

public class ObserverManager2 implements SubjectListener2 {

    private static ObserverManager2 observerManager;

    //观察者接口集合
    private List<ObserverListener2> list = new ArrayList<>();

    /**
     * 单例
     */
    public static ObserverManager2 getInstance(){
        if (null == observerManager){
            synchronized (ObserverManager2.class){
                if (null == observerManager){
                    observerManager = new ObserverManager2();
                }
            }
        }
        return observerManager;
    }

    /**
     * 加入监听队列
     */
    @Override
    public void add(ObserverListener2 observerListener) {
        list.add(observerListener);
    }

    /**
     * 通知观察者刷新数据
     */
    @Override
    public void notifyObserver(String cardNo) {
        for (ObserverListener2 observerListener : list){
            observerListener.observerUpData2(cardNo);
        }
    }

    /**
     * 监听队列中移除
     */
    @Override
    public void remove(ObserverListener2 observerListener) {
        if (list.contains(observerListener)){
            list.remove(observerListener);
        }
    }
}
