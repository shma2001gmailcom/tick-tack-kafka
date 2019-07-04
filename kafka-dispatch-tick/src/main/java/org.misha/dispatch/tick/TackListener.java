package org.misha.dispatch.tick;

import org.misha.dispatch.Listener;
import org.misha.dispatch.Sender;
import org.springframework.stereotype.Component;

@Component
class TackListener extends Listener {

    TackListener(Sender tickSender) {
        addObserver(tickSender);
    }
}
