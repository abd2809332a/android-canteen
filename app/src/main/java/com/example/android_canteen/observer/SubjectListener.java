package com.example.android_canteen.observer;


public interface SubjectListener {
    void add(ObserverListener observerListener);
    void notifyObserver(String cardNo);
    void remove(ObserverListener observerListener);
}
