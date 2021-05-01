package com.example.rxjava;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private Disposable disposable;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable.just(1, 2, 3, 4, 5, 6)
                .skip(3)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        // vaghti movafagh shodim moshtarak beshim rooye obserable  zade mishe . */
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {
                        //vaghti heyvani dide shod esme heyvan seda zade mishe .
                        Log.d(TAG, "onNext: ");
                    }


                    @Override
                    public void onError(@NonNull Throwable e) {
                        // heyne anjame observe kakhataei b vojod biad .
                        Log.d(TAG, "onError: " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        //dar bala vaghti akharin esme ersal shod (yani too inja "Bear") in method seda zade mishe .
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}