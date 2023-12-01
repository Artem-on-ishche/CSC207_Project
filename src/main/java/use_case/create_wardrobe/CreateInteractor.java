package use_case.create_wardrobe;

import model.ClothingItem;
import model.ClothingType;

import java.awt.*;

public class CreateInteractor implements CreateInputBoundary {
    private final CreateDataAccess createDataAccess;
    private final CreateOutputBoundary createPresenter;
    private final ClothingIdentificationService clothingIdentificationService;

    public CreateInteractor(
            CreateDataAccess createDataAccess,
            CreateOutputBoundary createPresenter,
            ClothingIdentificationService clothingIdentificationService
    ) {
        this.createDataAccess = createDataAccess;
        this.createPresenter = createPresenter;
        this.clothingIdentificationService = clothingIdentificationService;
    }

    @Override
    public void execute(CreateInputData inputData) {
        try {
            Image clothingImage = inputData.image();
            String identifiedClothingType = clothingIdentificationService.identifyClothingItem(clothingImage);


            ClothingItem clothingItem = new ClothingItem(
                    null,
                    inputData.name(),
                    clothingImage,
                    ClothingType.valueOf(identifiedClothingType),
                    inputData.minimumAppropriateTemperature(),
                    inputData.description()
            );
            createDataAccess.addClothingItem(clothingItem);
            createPresenter.prepareSuccessView(new CreateOutputData(clothingItem, false));

        } catch (RuntimeException e) {
            createPresenter.prepareFailView("Error adding clothing item "+ e.getMessage());
        }
    }
}
