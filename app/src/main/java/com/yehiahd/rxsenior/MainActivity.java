package com.yehiahd.rxsenior;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button button2;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn);
        button2 = findViewById(R.id.btn2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doWorkWithoutRx();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doWork();
            }
        });


    }

    private void doWorkWithoutRx() {
        List<String> list = new ArrayList<>();
        list.add("Yehia");
        list.add("Mohamed");
        list.add("Ali");

        for (int i = 0; i < list.size(); i++) {
            String temp = list.get(i);
            list.remove(i);
            list.add(i, "Mohamed " + temp);
        }


        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).contains("Yehia")) {
                list.remove(i);
                i--;
            }
        }

        for (String s : list) {
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        }

    }

    private void doWork() {

        List<String> list = new ArrayList<>();
        list.add("Yehia");
        list.add("Mohamed");
        list.add("Ali");

        disposable = Observable.fromIterable(list)
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) {
                        return "Mohamed " + s;
                    }
                })
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) {
                        if (s.contains("Yehia"))
                            return true;
                        else
                            return false;
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.d("Crash", throwable.getMessage());
                    }
                }, new Action() {
                    @Override
                    public void run() {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
