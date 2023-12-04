package data_access;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IpAddressLocationDataSourceTest {

    @Test
    void getLocationData() {
        new IpAddressLocationDataSource().getLocationData();
    }
}