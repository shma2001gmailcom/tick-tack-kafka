package net.zinovev.services.bindings.dispatch.tack;

import net.zinovev.services.bindings.dispatch.AbstractListener;
import net.zinovev.services.bindings.dispatch.Sender;
import org.springframework.stereotype.Component;

@Component
class TickListener extends AbstractListener {

    public TickListener(Sender tackSender) {addObserver(tackSender);}
}
