package unit;

import org.junit.jupiter.api.*;
import utilities.DbConnection;

class DbConnectionTest {

    @Test
    void instance() {
    }

    @Test
    void datastore() {
    }

    @Test
    void connect() {
        Assertions.assertNotNull(DbConnection.instance().datastore());
    }
}