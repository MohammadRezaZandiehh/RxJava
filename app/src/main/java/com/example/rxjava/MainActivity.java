package com.example.rxjava;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

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

        Observable.interval(1, 1, TimeUnit.SECONDS)
                .switchMap(n -> Observable.just(n * 10).delay(1, TimeUnit.SECONDS))
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        // vaghti movafagh shodim moshtarak beshim rooye obserable  zade mishe . */
                        disposable = d;
                    }

                    @Override
                    public void onNext(Long s) {
                        //vaghti heyvani dide shod esme heyvan seda zade mishe .
                        Log.d(TAG, "onNext: " + s.toString());
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