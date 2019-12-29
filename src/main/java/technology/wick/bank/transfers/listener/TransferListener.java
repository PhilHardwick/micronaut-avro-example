package technology.wick.bank.transfers.listener;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.messaging.annotation.SendTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import technology.wick.bank.transfers.MakeTransfer;
import technology.wick.bank.transfers.TransferEvent;

import javax.inject.Singleton;
import java.util.UUID;

@Singleton
@KafkaListener(groupId = "transfers", offsetReset = OffsetReset.EARLIEST)
public class TransferListener {

    private static final Logger LOG = LoggerFactory.getLogger(TransferListener.class);

    @Topic("transfers")
    @SendTo("transfer-events")
    public TransferEvent makeTransferReceived(MakeTransfer makeTransfer) {
        LOG.info("Make transfer received {}", makeTransfer);
        return TransferEvent.newBuilder()
                .setTransferId(UUID.randomUUID().toString())
                .setDestAccountId(makeTransfer.getDestAccountId())
                .setSrcAccountId(makeTransfer.getSrcAccountId())
                .setAmount(makeTransfer.getAmount())
                .build();
    }

}
