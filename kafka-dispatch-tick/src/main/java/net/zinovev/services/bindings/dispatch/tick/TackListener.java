package net.zinovev.services.bindings.dispatch.tick;

import lombok.extern.slf4j.Slf4j;
import net.zinovev.services.bindings.dispatch.AbstractListener;
import net.zinovev.services.bindings.dispatch.Sender;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class TackListener extends AbstractListener {

    TackListener(Sender tickSender) {
        //addObserver(tickSender);
    }
}
