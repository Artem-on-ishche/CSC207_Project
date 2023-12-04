package interface_adapter.create_wardrobe;

import use_case.create_wardrobe.CreateInputBoundary;
import use_case.create_wardrobe.CreateInputData;

import java.awt.*;
import java.util.Optional;

public class CreateWardrobeController {
    private final CreateInputBoundary createUseCaseInteractor;
    public CreateWardrobeController(CreateInputBoundary createUseCaseInteractor) {
        this.createUseCaseInteractor = createUseCaseInteractor;
    }

    public void execute(String username,
                        String name,
                        String imageSrc,
                        Optional<String> description,
                        int minimumAppropriateTemperature) {
        CreateInputData createInputData = new CreateInputData(username, name, imageSrc, description, minimumAppropriateTemperature);
        createUseCaseInteractor.execute(createInputData);
    }
}
