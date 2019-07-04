package org.misha.dispatch.tack;

import org.misha.dispatch.Listener;
import org.misha.dispatch.Sender;
import org.springframework.stereotype.Component;

@Component
class TickListener extends Listener {

    public TickListener(Sender tackSender) {addObserver(tackSender);}
}
