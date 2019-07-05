package net.zinovev.services.bindings.dispatch.tick;

import net.zinovev.services.bindings.dispatch.AbstractListener;
import net.zinovev.services.bindings.dispatch.Sender;
import org.springframework.stereotype.Component;

@Component
class TackListener extends AbstractListener {

    TackListener(Sender tickSender) {
        addObserver(tickSender);
    }
}
