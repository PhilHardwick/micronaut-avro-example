package technology.wick.bank.transfers.listener;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import technology.wick.bank.transfers.TransferEvent;

import javax.inject.Singleton;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@Singleton
@KafkaListener(groupId = "test-listener", offsetReset = OffsetReset.EARLIEST, clientId = "transfer-event-test-listener")
public class TransferEventListener {

    private BlockingQueue<TransferEvent> messages = new LinkedBlockingDeque<>();

    @Topic("transfer-events")
    public void transferEventReceived(TransferEvent transferEvent) {
        messages.add(transferEvent);
    }

    public BlockingQueue<TransferEvent> getMessages() {
        return messages;
    }
}
