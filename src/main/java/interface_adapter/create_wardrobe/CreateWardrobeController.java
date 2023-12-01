package interface_adapter.create_wardrobe;

import entity.ClothingItem;
import use_case.create_wardrobe.CreateInputBoundary;
import use_case.create_wardrobe.CreateInputData;

import java.awt.*;
import java.util.Optional;

public class CreateWardrobeController {
    private final CreateInputBoundary createUseCaseInteractor;
    public CreateWardrobeController(CreateInputBoundary createUseCaseInteractor) {
        this.createUseCaseInteractor = createUseCaseInteractor;
    }

    public void execute(String name,
                        Image image,
                        Optional<String> description,
                        double minimumAppropriateTemperature) {
        CreateInputData createInputData = new CreateInputData(name, image, description, minimumAppropriateTemperature);
        createUseCaseInteractor.execute(createInputData);
    }
}
