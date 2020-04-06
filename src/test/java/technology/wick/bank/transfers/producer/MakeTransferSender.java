package technology.wick.bank.transfers.producer;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import technology.wick.bank.transfers.MakeTransfer;

import java.util.UUID;

@KafkaClient
public interface MakeTransferSender {

    @Topic("transfer-commands")
    void sendMakeTransfer(@KafkaKey UUID transferId, MakeTransfer makeTransfer);

}
