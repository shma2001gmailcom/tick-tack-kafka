package org.misha.dispatch.tick;

import lombok.extern.slf4j.Slf4j;
import org.misha.dispatch.Listener;
import org.misha.dispatch.Sender;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;

import javax.annotation.PostConstruct;

@EnableBinding(Sink.class)
@Slf4j
class TackListener extends Listener {
    private final Sender tickSender;

    TackListener(Sender tickSender) {
        this.tickSender = tickSender;
    }

    @PostConstruct
    private void addSender() {
        addObserver(tickSender);
    }
}
