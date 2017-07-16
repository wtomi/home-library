package wojtowicz.tomi.booklibrary.registration.listener;

import org.springframework.context.ApplicationListener;
import wojtowicz.tomi.booklibrary.registration.event.OnRegistrationCompleteEvent;

/**
 * Created by tommy on 7/16/2017.
 */
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent>{
    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {

    }
}
