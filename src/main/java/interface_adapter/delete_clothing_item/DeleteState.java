package interface_adapter.delete_clothing_item;


public class DeleteState {

    private String deleteError;

    public DeleteState(DeleteState copy) {
        this.deleteError = copy.deleteError;
    }

    public DeleteState() {}

    public String getDeleteError() {
        return deleteError;
    }

    public void setDeleteError(String deleteError) {
        this.deleteError = deleteError;
    }
}
