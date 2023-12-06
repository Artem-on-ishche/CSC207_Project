package data_access.api;

import data_access.api.FashionAPI;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class FashionAPITest {

    @Test
    void identifyClothingItem() throws IOException {
        FashionAPI fashionAPI = new FashionAPI();
        fashionAPI.identifyClothingItem("docs/img.png");
    }
}