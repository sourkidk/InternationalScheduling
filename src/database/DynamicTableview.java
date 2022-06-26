package database;

import com.mysql.cj.exceptions.UnableToConnectException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.ZoneOffset.UTC;

/**
 * The type Dynamic tableview.
 */
public class DynamicTableview {

    /**
     * The Sql formatter.
     */
    static DateTimeFormatter sqlFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    /**
     * Populate table view.
     *
     * @param tableview the tableview
     * @param rs        the rs
     * @param data      the data
     * @throws SQLException the sql exception
     */
    public static void populateTableView(TableView tableview, ResultSet rs, ObservableList data) throws SQLException {
//        ObservableList<Integer> objectIDList = FXCollections.observableArrayList();

        try {

            tableview.getItems().clear();
            tableview.getColumns().clear();
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {

                int j = i;
                TableColumn column = new TableColumn(rs.getMetaData().getColumnName(i + 1));

                column.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));

                 tableview.getColumns().addAll(column);
            }

            while (rs.next()) {
//                objectIDList.add(rs.getInt(0));
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

    /**
     * Populate appt table view.
     *
     * @param tableview the tableview
     * @param rs        the rs
     * @param data      the data
     * @throws SQLException the sql exception
     */
    public static void populateApptTableView(TableView tableview, ResultSet rs, ObservableList data) throws SQLException {
//        ObservableList<Integer> objectIDList = FXCollections.observableArrayList();

        try {

            tableview.getItems().clear();
            tableview.getColumns().clear();
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {

                int j = i;
                TableColumn column = new TableColumn(rs.getMetaData().getColumnName(i + 1));

                column.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));

                 tableview.getColumns().addAll(column);
            }

            while (rs.next()) {
//                objectIDList.add(rs.getInt(0));
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    ZoneId currentZone = ZoneId.systemDefault();
                    if(i == 5 || i == 6 ) {
                        LocalDateTime utcTime = LocalDateTime.parse(rs.getString(i), sqlFormatter);
                        ZonedDateTime zdtTime = ZonedDateTime.of(utcTime, UTC).withZoneSameInstant(currentZone);
                        row.add(zdtTime.format(sqlFormatter).toString());

                    }
                    else {
                        row.add(rs.getString(i));
                    }

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
