package org.misha.dispatch;

import java.io.IOException;

public interface Listener {

    void onMessage(String messageJson) throws IOException;
}
