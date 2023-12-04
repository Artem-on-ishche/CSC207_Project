package data_access.api;

import data_access.api.IpAddressLocationDataSource;
import org.junit.jupiter.api.Test;

class IpAddressLocationDataSourceTest {

    @Test
    void getLocationData() {
        new IpAddressLocationDataSource().getLocationData();
    }
}