package data_access;

import model.ClothingType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FashionAPITest {

    @Test
    void identifyClothingItem() {
        FashionAPI fashionAPI = new FashionAPI();
        fashionAPI.identifyClothingItem("https://img.freepik.com/free-photo/black-woman-trendy-grey-leather-jacket-posing-beige-background-studio-winter-autumn-fashion-look_273443-141.jpg");
    }
}