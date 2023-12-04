package interface_adapter.delete_clothing_item;


import lombok.Getter;

@Getter
public class DeleteState {

    public void setDeletedItem(Long deletedItem) {
        this.deletedItemId = deletedItem;
    }

    private Long deletedItemId;

    private String deleteError;

    public DeleteState(Long deletedItem, DeleteState copy) {
        this.deletedItemId = deletedItem;
        this.deleteError = copy.deleteError;
    }

    public DeleteState() {}

    public void setDeleteError(String deleteError) {
        this.deleteError = deleteError;
    }
}
