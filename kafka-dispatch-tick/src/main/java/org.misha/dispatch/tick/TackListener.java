package org.misha.dispatch.tick;

import org.misha.dispatch.AbstractListener;
import org.misha.dispatch.Sender;
import org.springframework.stereotype.Component;

@Component
class TackListener extends AbstractListener {

    TackListener(Sender tickSender) {
        addObserver(tickSender);
    }
}
