package com.switchfully.order;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring (Boot) will set up a Test Application Context.
 * For Test Application Context for which Transactions are enabled,
 * The transaction will automatically be rolled back.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TestApplication.class})
@Transactional
public abstract class IntegrationTest {

}
