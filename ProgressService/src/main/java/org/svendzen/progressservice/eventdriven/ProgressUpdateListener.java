package org.svendzen.progressservice.eventdriven;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ProgressUpdateListener {

    private static final Logger log = LoggerFactory.getLogger(ProgressUpdateListener.class);

    @RabbitListener(queues = "progressQueue")
    public void handleProgressMessage(String message) {
        // Process message here. e.g. update progress in db.
        log.info("Received message: {}", message);
        // Parse message & update student progress.
    }
}
