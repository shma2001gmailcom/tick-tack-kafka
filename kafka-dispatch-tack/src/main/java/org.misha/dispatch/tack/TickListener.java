package org.misha.dispatch.tack;

import org.misha.dispatch.Listener;
import org.misha.dispatch.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class TickListener extends Listener {

    @Autowired
    public TickListener(Sender tackSender) {addObserver(tackSender);}
}
