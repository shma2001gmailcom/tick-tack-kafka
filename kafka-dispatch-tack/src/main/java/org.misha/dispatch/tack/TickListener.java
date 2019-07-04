package org.misha.dispatch.tack;

import lombok.extern.slf4j.Slf4j;
import org.misha.dispatch.Listener;
import org.misha.dispatch.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
class TickListener extends Listener {
    private final Sender tackSender;

    @Autowired
    public TickListener(Sender tackSender) {this.tackSender = tackSender;}

    @PostConstruct
    private void addSender() {
        addObserver(tackSender);
    }
}
