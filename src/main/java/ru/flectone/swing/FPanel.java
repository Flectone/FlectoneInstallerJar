package ru.flectone.swing;

import ru.flectone.swing.pages.PageDefault;
import ru.flectone.utils.UtilsSystem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FPanel extends JPanel {

    public FPanel addComponent(Component component){
        this.add(component);
        return this;
    }

    public FPanel setPanelLayout(LayoutManager layout){
        setLayout(layout);
        return this;
    }

    public FPanel createRigidArea(int width, int height){
        add(Box.createRigidArea(new Dimension(width, height)));
        return this;
    }

    protected JCheckBox createCheckBox(String checkBoxName, String page){
        JCheckBox checkBox = new JCheckBox(UtilsSystem.getLocaleString(checkBoxName));
        checkBox.setAlignmentX(LEFT_ALIGNMENT);

        checkBox.setName(checkBoxName.split("\\.")[1]);
        if(checkBoxName.contains("litematic") && page.equals("farms")) checkBox.setName(checkBoxName.split("\\.")[1] + "litematic");

        ArrayList<JCheckBox> listCheckBox = new ArrayList<>();
        if(UtilsSystem.listCheckBox.get(page) != null){
            listCheckBox = UtilsSystem.listCheckBox.get(page);
        }
        listCheckBox.add(checkBox);
        UtilsSystem.listCheckBox.put(page, listCheckBox);

        if(page.equals("modsmain")){
            checkBox.setSelected(true);
        }
        if(page.equals("modsextension")) return checkBox;

        checkBox.addActionListener(e -> {
            int count = UtilsSystem.countCheckBoxHashMap.get(page);

            if(checkBox.isSelected()){
                count = count + 1;
            } else {
                count = count - 1;
            }
            ArrayList<Component> arrayList = UtilsSystem.enabledComponentsHashMap.get(page);
            for(Component component : arrayList){
                boolean bol = count > 0;
                component.setEnabled(bol);
                if(component instanceof JLabel){
                    ((JLabel) component).setText(UtilsSystem.getLocaleString("label.status.ready." + bol));
                }
            }

            UtilsSystem.countCheckBoxHashMap.put(page, count);
        });

        return checkBox;
    }

    //Create label
    protected JLabel createLabel(String labelName){
        //Create label with name from locale
        JLabel label = new JLabel(UtilsSystem.getLocaleString(labelName));
        //Set alignment
        label.setAlignmentX(LEFT_ALIGNMENT);
        return label;
    }
}
