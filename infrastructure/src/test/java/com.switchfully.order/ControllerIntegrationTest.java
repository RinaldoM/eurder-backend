package com.switchfully.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring (Boot) will set up a Test Application Context.
 * For Test Application Context for which Transactions are enabled,
 * The transaction will automatically be rolled back.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TestApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public abstract class ControllerIntegrationTest {

    @LocalServerPort
    private int port;

    public int getPort() {
        return port;
    }

    @BeforeEach
    public void clearAndFlushDatabase() {
        clearDatabase();
        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();
    }

    public abstract void clearDatabase();
}
