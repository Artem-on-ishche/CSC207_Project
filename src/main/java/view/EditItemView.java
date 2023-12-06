package view;

import interface_adapter.get_clothing_item.GetItemState;
import interface_adapter.get_clothing_item.GetItemViewModel;
import interface_adapter.view_all_items.ViewAllItemsViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EditItemView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "Get Clothing Item";
    private final JButton saveChanges;

    private final JButton back;
    public EditItemView(GetItemViewModel getItemViewModel) {

        JPanel buttons = new JPanel();
        saveChanges = new JButton(ViewAllItemsViewModel.ADD_CLOTHING_ITEM);
        buttons.add(saveChanges);

        back = new JButton(ViewAllItemsViewModel.BACK_TO_MAIN_VIEW);
        buttons.add(back);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        var newState = evt.getNewValue();
        if (newState instanceof GetItemState state) {
            if (state.getClothingItem() != null) {
                JOptionPane.showMessageDialog(this, state.getClothingItem());
            }

        }
    }
}
