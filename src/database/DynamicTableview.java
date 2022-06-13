package database;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DynamicTableview {

    public static void populateTableView(TableView tableview, ResultSet rs, ObservableList data) throws SQLException {
        try {
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {

                int j = i;
                TableColumn column = new TableColumn(rs.getMetaData().getColumnName(i + 1));

                column.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));

                 tableview.getColumns().addAll(column);
            }

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }


            tableview.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was a problem populating the data.");
        }
    }
}
