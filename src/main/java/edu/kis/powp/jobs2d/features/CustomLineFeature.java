package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.adapter.LineDrawerAdapter;
import edu.kis.powp.jobs2d.drivers.lineshape.CustomLine;

import javax.swing.JCheckBoxMenuItem;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class CustomLineFeature {
    private static final CustomLine line = new CustomLine();

    private static final List<JCheckBoxMenuItem> colorGroup = new ArrayList<>();
    private static final List<JCheckBoxMenuItem> thicknessGroup = new ArrayList<>();

    public static void setupCustomLineFeature(Application application) {
        Job2dDriver customLineDriver = new LineDrawerAdapter(DrawerFeature.getDrawerController(), line);
        DriverFeature.addDriver("Custom line", customLineDriver);

        setupCustomLineMenu(application);
    }

    private static void setupCustomLineMenu(Application application) {
        application.addComponentMenu(CustomLine.class, "Line Settings");

        addRadioOption(application, "Color: Black", () -> line.setColor(Color.BLACK), colorGroup);
        addRadioOption(application, "Color: Blue",  () -> line.setColor(Color.BLUE),  colorGroup);
        addRadioOption(application, "Color: Red",   () -> line.setColor(Color.RED),   colorGroup);


        addRadioOption(application, "Thickness: 1.0", () -> line.setThickness(1.0f), thicknessGroup);
        addRadioOption(application, "Thickness: 3.0", () -> line.setThickness(3.0f), thicknessGroup);
        addRadioOption(application, "Thickness: 5.0", () -> line.setThickness(5.0f), thicknessGroup);


        application.addComponentMenuElementWithCheckBox(CustomLine.class, "Dotted",
                (ActionEvent e) -> {
                    line.setDotted(!line.isDotted());
                    DriverFeature.updateDriverInfo();
                }, false);
    }

    private static void addRadioOption(Application app, String label, Runnable action, List<JCheckBoxMenuItem> group) {
        app.addComponentMenuElementWithCheckBox(CustomLine.class, label, (ActionEvent e) -> {
            action.run();
            selectOption(e, group);
        }, false);
    }

    private static void selectOption(ActionEvent e, List<JCheckBoxMenuItem> group) {
        JCheckBoxMenuItem clickedItem = (JCheckBoxMenuItem) e.getSource();

        if (!group.contains(clickedItem)) {
            group.add(clickedItem);
        }

        for (JCheckBoxMenuItem item : group) {
            boolean isClicked = (item == clickedItem);
            item.setState(isClicked);
            item.setEnabled(!isClicked);
        }

        DriverFeature.updateDriverInfo();
    }
}