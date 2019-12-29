package technology.wick.bank.transfers.producer;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;
import technology.wick.bank.transfers.MakeTransfer;

@KafkaClient
public interface MakeTransferSender {

    @Topic("transfers")
    void sendMakeTransfer(MakeTransfer makeTransfer);

}
