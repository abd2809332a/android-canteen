package com.example.android_canteen.observer2;


public interface SubjectListener2 {
    void add(ObserverListener2 observerListener);
    void notifyObserver(String cardNo);
    void remove(ObserverListener2 observerListener);
}
