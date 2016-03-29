package org.coderswithoutborders.deglancer.di;

import org.coderswithoutborders.deglancer.MainApplication;
import org.coderswithoutborders.deglancer.model.ActionReceiver;
import org.coderswithoutborders.deglancer.services.TrackerService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Renier on 2016/03/29.
 */
@Component(modules = { ApplicationModule.class, ReceiverModule.class, BusModule.class })
@Singleton
public interface Graph {
    void inject(TrackerService service);
    void inject(ActionReceiver receiver);


    final class Initializer {
        public static Graph init(MainApplication application) {
            return DaggerGraph
                    .builder()
                    .applicationModule(new ApplicationModule(application))
                    .build();
        }
    }
}
