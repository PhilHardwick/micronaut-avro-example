package technology.wick.bank.transfers;

import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import technology.wick.bank.transfers.listener.TransferEventListener;
import technology.wick.bank.transfers.producer.MakeTransferSender;

import javax.inject.Inject;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest(environments = "kafka")
public class TestTransferListener {

    @Inject
    private MakeTransferSender makeTransferSender;
    @Inject
    private TransferEventListener eventListener;

    @Test
    void sendsATransferEvent() throws InterruptedException {
        String destAccountId = UUID.randomUUID().toString();
        String srcAccountId = UUID.randomUUID().toString();
        makeTransferSender.sendMakeTransfer(MakeTransfer.newBuilder()
                .setDestAccountId(destAccountId)
                .setSrcAccountId(srcAccountId)
                .setAmount(100)
                .build());

        TransferEvent transferEvent = eventListener.getMessages().poll(5, TimeUnit.SECONDS);
        assertThat(transferEvent).isNotNull();
        assertThat(transferEvent.getAmount()).isEqualTo(100);
        assertThat(transferEvent.getSrcAccountId()).isEqualTo(srcAccountId);
        assertThat(transferEvent.getDestAccountId()).isEqualTo(destAccountId);
        assertThat(transferEvent.getTransferId()).isNotNull();
    }
}
