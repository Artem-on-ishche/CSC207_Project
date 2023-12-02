package use_case.create_wardrobe;

import model.ClothingItem;
import model.ClothingType;
import model.ImageData;

public class CreateInteractor implements CreateInputBoundary {
    private final CreateDataAccess createDataAccess;
    private final CreateOutputBoundary createPresenter;
    private final ClothingIdentificationService clothingIdentificationService;
    private final ImageCreator imageCreator;

    public CreateInteractor(
            CreateDataAccess createDataAccess,
            CreateOutputBoundary createPresenter,
            ClothingIdentificationService clothingIdentificationService,
            ImageCreator imageCreator) {
        this.createDataAccess = createDataAccess;
        this.createPresenter = createPresenter;
        this.clothingIdentificationService = clothingIdentificationService;
        this.imageCreator = imageCreator;
    }

    @Override
    public void execute(CreateInputData inputData) {
        try {
            String clothingImageSrc = inputData.imageSrc();
            ClothingType identifiedClothingType = clothingIdentificationService.identifyClothingItem(clothingImageSrc);
            ImageData clothingImage = imageCreator.fromImageSrc(clothingImageSrc);


            ClothingItem clothingItem = new ClothingItem(
                    null,
                    inputData.name(),
                    clothingImage,
                    identifiedClothingType,
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
