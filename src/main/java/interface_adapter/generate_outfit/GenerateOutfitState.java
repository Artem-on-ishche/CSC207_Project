package interface_adapter.generate_outfit;

import lombok.Getter;
import model.Outfit;

@Getter
public class GenerateOutfitState {
    public GenerateOutfitState(Outfit outfit, String generateOutfitError) {
        this.outfit = outfit;
        this.generateOutfitError = generateOutfitError;
    }

    public void setOutfit(Outfit outfit) {
        this.outfit = outfit;
    }


    private Outfit outfit;

    public void setGenerateOutfitError(String generateOutfitError) {
        this.generateOutfitError = generateOutfitError;
    }

    private String generateOutfitError;

    public GenerateOutfitState() {
    }
}
