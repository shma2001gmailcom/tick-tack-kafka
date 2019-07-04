package org.misha.dispatch.tack;

import org.misha.dispatch.AbstractListener;
import org.misha.dispatch.Sender;
import org.springframework.stereotype.Component;

@Component
class TickListener extends AbstractListener {

    public TickListener(Sender tackSender) {addObserver(tackSender);}
}
