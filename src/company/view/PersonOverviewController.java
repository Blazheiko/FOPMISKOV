package company.view;

import company.Main;
import company.model.Person;
import company.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;



public class PersonOverviewController {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;
    @FXML
    private TableColumn<Person, String> patronColumn;

    @FXML
    private TableColumn<Person, String> innColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private Label tariffLabel;

    @FXML
    private Label salaryBalanceLabel;

    @FXML
    private Label patronLabel;

    @FXML
    private Label dateOfRecruitmentLabel;

    @FXML
    private Label dateOfDismissal;
    @FXML
    private Label innLabel;

    @FXML
    private Label noteLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label positionLabel;


    @FXML
    private Label tariffCentLabel;

    @FXML
    private Label salaryBalanceCentLabel;




    // Ссылка на главное приложение.
    private Main main;


    @FXML
    private void initialize() {

        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        innColumn.setCellValueFactory(cellData -> cellData.getValue().innProperty());
        patronColumn.setCellValueFactory(cellData -> cellData.getValue().patronProperty());
        //birhtdayColumn.setCellValueFactory(cellData -> cellData.getValue().birthdayProperty());

        // Очистка дополнительной информации об адресате.
        showPersonDetails(null);

        // Слушаем изменения выбора, и при изменении отображаем
        // дополнительную информацию об адресате.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }
    /**
     * Вызывается, когда пользователь кликает по кнопке удаления.
     */
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Відсутність вибору");
            alert.setHeaderText("Не вибрано персону");
            alert.setContentText("Будь ласка виберіть персону з таблиці.");

            alert.showAndWait();
        }
    }

    /**
     * Конструктор.
     * Конструктор вызывается раньше метода initialize().
     */
    public PersonOverviewController() {
    }



    /**
     * Вызывается главным приложением, которое даёт на себя ссылку.
     *
     * @param main
     */
    public void setMain(Main main) {
        this.main = main;

        // Добавление в таблицу данных из наблюдаемого списка
        personTable.setItems(main.getPersonData());
    }
    /**
     * Заполняет все текстовые поля, отображая подробности об адресате.
     * Если указанный адресат = null, то все текстовые поля очищаются.
     *
     * — адресат типа Person или null
     */
    private void showPersonDetails(Person person) {
        if (person != null) {
            // Заполняем метки информацией из объекта person.
            innLabel.setText(person.getInn());
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            patronLabel.setText(person.getPatron());
            streetLabel.setText(person.getStreet());
            cityLabel.setText(person.getCity());
            phoneLabel.setText(person.getPhone());
            positionLabel.setText(person.getPosition());
            tariffLabel.setText(""+person.getTariff());
            tariffCentLabel.setText(""+person.getTariffCent());
            salaryBalanceLabel.setText(""+person.getSalaryBalance());
            salaryBalanceCentLabel.setText(""+person.getSalaryBalanceCent());
            noteLabel.setText(person.getNote());
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
            dateOfRecruitmentLabel.setText(DateUtil.format(person.getDateOfRecruitment()));
            dateOfDismissal.setText(DateUtil.format(person.getDateOfDismissal()));


        } else {
            // Если Person = null, то убираем весь текст.
            innLabel.setText("");
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            patronLabel.setText("");
            streetLabel.setText("");
            cityLabel.setText("");
            phoneLabel.setText("");
            positionLabel.setText("");
            tariffLabel.setText("");
            tariffCentLabel.setText("");
            salaryBalanceLabel.setText("");
            salaryBalanceCentLabel.setText("");
            noteLabel.setText("");
            birthdayLabel.setText("");
            dateOfRecruitmentLabel.setText("");
            dateOfDismissal.setText("");
        }
    }
    /**
     * Вызывается, когда пользователь кликает по кнопке New...
     * Открывает диалоговое окно .
     */
    @FXML
    private void handleNewPerson() {
        Person tempPerson = new Person();
        boolean okClicked = main.showPersonEditDialog(tempPerson);
        if (okClicked) {
            main.getPersonData().add(tempPerson);
        }
    }

    /**
     * Вызывается, когда пользователь кликает по кнопка Edit...
     * Открывает диалоговое окно для изменения выбранного адресата.
     */
    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = main.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }

        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Відсутність вибору");
            alert.setHeaderText("Персона не вибрана");
            alert.setContentText("Будь ласка виберіть персону з таблиці..");

            alert.showAndWait();
        }
    }
}