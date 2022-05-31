package se.ya.bokningssystem.frontend.admin.report;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;
import se.ya.bokningssystem.backend.entity.BookingEO;
import se.ya.bokningssystem.backend.entity.ResourceEO;
import se.ya.bokningssystem.backend.entity.UserEO;
import se.ya.bokningssystem.frontend.admin.report.handlers.Finder;

import java.time.LocalDate;

@Getter
public class ReportController {

    @FXML
    private Button btn_reset;

    @FXML
    private ChoiceBox<ResourceEO> cb_resource;

    @FXML
    private ChoiceBox<UserEO> cb_user;

    @FXML
    private TableColumn<BookingEO, ResourceEO> col_resource;

    @FXML
    private TableColumn<BookingEO, LocalDate> col_return;

    @FXML
    private TableColumn<BookingEO, UserEO> col_user;

    @FXML
    private TableView<BookingEO> tv;

    private final ObservableList<BookingEO> bookingEOS = FXCollections.observableArrayList();

    private final Finder finder = new Finder(this);

    private final ObservableList<UserEO> users = FXCollections.observableArrayList();

    private final ObservableList<ResourceEO> resources = FXCollections.observableArrayList();



    @FXML private void initialize(){

        finder.populateNoFilter();
        tv.setItems(bookingEOS);
        col_user.setCellValueFactory(new PropertyValueFactory<>("user"));
        col_resource.setCellValueFactory(new PropertyValueFactory<>("resource"));
        col_return.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        //choicebox data
        cb_user.setItems(users);
        finder.getUsers();
        cb_resource.setItems(resources);
        finder.getResorces();

        //actionhandler
        btn_reset.setOnAction(event -> finder.reset());

        //userChangeListener

        cb_user.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                cb_resource.getSelectionModel().select(null);
                finder.populateByUser(newValue);
            }

        });

        //resourcechangelistener
        cb_resource.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cb_user.getSelectionModel().select(null);
                finder.populateByResource(newValue);
            }
        });

    }

}
