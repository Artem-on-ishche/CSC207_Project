package interface_adapter;

public class ClothingAddFailed extends RuntimeException {
    public ClothingAddFailed(String error) {
        super(error);
    }
}