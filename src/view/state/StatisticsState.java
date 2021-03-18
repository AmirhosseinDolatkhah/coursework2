package view.state;

import view.GraphPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public abstract class StatisticsState extends JPanel {
    private GraphPanel gp;

    public StatisticsState(String tableName) {
        super(new BorderLayout());
        init(tableName);
    }

    private void init(String tableName) {
        var backButton = new JButton("Back");
        backButton.addActionListener(e -> backAction());
        var wrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        wrapper.add(backButton);
        add(wrapper, BorderLayout.SOUTH);

        var columns = new JComboBox<String>();
        var cols = getColumns(tableName);
        gp = new GraphPanel(tableName + ": " + cols[0], getStatisticsOf(tableName, cols[0]));
        columns.addActionListener(e -> {
            gp.setName(tableName + ": " + columns.getSelectedItem());
            gp.setValues(getStatisticsOf(tableName, (String) columns.getSelectedItem()));
        });
        for (var s : cols)
            columns.addItem(s);
        add(gp, BorderLayout.CENTER);

        var wrapper2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        wrapper2.add(columns);
        add(columns, BorderLayout.NORTH);
    }

    protected abstract String[] getColumns(String name);
    protected abstract Map<String, Integer> getStatisticsOf(String tableName, String col);
    protected abstract void backAction();
}
