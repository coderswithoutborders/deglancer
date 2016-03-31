package org.coderswithoutborders.deglancer;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import org.coderswithoutborders.deglancer.di.DIGraph;

/**
 * Created by Renier on 2016/03/29.
 */
public class MainApplication extends Application {
    private DIGraph mGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        mGraph = DIGraph.Initializer.init(this);
    }

    public DIGraph getGraph() {
        return mGraph;
    }

    public static MainApplication from(@NonNull Context context) {
        return (MainApplication) context.getApplicationContext();
    }
}