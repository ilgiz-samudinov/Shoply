package com.example.userservice.event;

import com.example.userservice.port.FileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class FileDeleteEventListener {
    private final FileStorage fileStorage;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onFileDelete(FileDeleteEvent event) {
        fileStorage.delete(event.getBucketName(), event.getKey());
    }

}
