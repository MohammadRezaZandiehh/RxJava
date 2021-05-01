package com.example.rxjava;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
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

        getMyObservable()
                .filter(n -> n > 2)
                .map(n -> n * 10)
                .concatMap(n -> Observable.just(n * 10).delay(1, TimeUnit.SECONDS))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        // vaghti movafagh shodim moshtarak beshim rooye obserable  zade mishe . */
                        disposable = d;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        //vaghti heyvani dide shod esme heyvan seda zade mishe .
                        Log.d(TAG, "onNext: " + integer.toString());
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

    private Observable<Integer> getMyObservable() {
        return Observable.create(emitter -> {
            try {
                for (int i = 0; i < 20; i++) {
                    if (!emitter.isDisposed()) {
                        emitter.onNext(i);
                    }
                }

                if (!emitter.isDisposed()) {
                    emitter.onComplete();
                }

            } catch (Exception e) {
                if (!emitter.isDisposed()) {
                    emitter.onError(e);
                }
            }


        });
    }
}