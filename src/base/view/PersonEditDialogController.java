package base.view;

import base.model.Person;
import base.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Окно для изменения информации об адресате.
 *
 * @author Marco Jakob
 */
public class PersonEditDialogController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField cityField;

    @FXML
    private TextField dateOfRecruitmentField;

    @FXML
    private TextField dateOfDismissalField;

    @FXML
    private TextField tariffField;

    @FXML
    private TextField noteField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField streetField;

    @FXML
    private TextField patronField;

    @FXML
    private TextField birthdayField;

    @FXML
    private TextField positionField;

    @FXML
    private TextField innField;

    @FXML
    private TextField balanceField;



    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;


    @FXML
    private void initialize() {
    }

    /**
     * Устанавливает сцену для этого окна.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Задаёт адресата, информацию о котором будем менять.
     *
     * @param person
     */
    public void setPerson(Person person) {
        this.person = person;

        firstNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        streetField.setText(person.getStreet());
        phoneField.setText(person.getPhone());
        cityField.setText(person.getCity());
        birthdayField.setText(DateUtil.format(person.getBirthday()));
        birthdayField.setPromptText("dd.mm.yyyy");
    }

    /**
     * Returns true, если пользователь кликнул OK, в другом случае false.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Вызывается, когда пользователь кликнул по кнопке OK.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setInn (innField.getText());
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setPatron(patronField.getText());
            person.setStreet(streetField.getText());
            person.setCity(cityField.getText());
            person.setPhone(phoneField.getText());
            person.setPosition(positionField.getText());
            person.setTariff(Double.parseDouble(tariffField.getText()));
            person.setSalaryBalance(Double.parseDouble(balanceField.getText()));
            person.setNote(noteField.getText());
            person.setBirthday(DateUtil.parse(birthdayField.getText()));
            person.setDateOfRecruitment(DateUtil.parse(dateOfRecruitmentField.getText()));
            person.setDateOfDismissal(DateUtil.parse(dateOfDismissalField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Вызывается, когда пользователь кликнул по кнопке Cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Проверяет пользовательский ввод в текстовых полях.
     *
     * @return true, если пользовательский ввод корректен
     */
    private boolean isInputValid() {
        String errorMessage = "";
        if (innField.getText().trim() == null || innField.getText().trim().length() == 0) {
            errorMessage += "Не введено ІНН!\n";
        }
        if (firstNameField.getText().trim() == null || firstNameField.getText().trim().length() == 0) {
            errorMessage += "Не введено прізвище!\n";
        }
        if (lastNameField.getText().trim() == null || lastNameField.getText().trim().length() == 0) {
            errorMessage += "Не введено імя!\n";
        }
        if (patronField.getText().trim() == null || patronField.getText().trim().length() == 0) {
            errorMessage += "Не введено по батькові!\n";
        }
        if (streetField.getText() == null || streetField.getText().length() == 0) {
            errorMessage += "Не введено вулицю!\n";
        }

        if (phoneField.getText() == null || phoneField.getText().length() == 0) {
            errorMessage += "Не введено телефон!\n";
        }

        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += "Не введено місто!!\n";
        }

       if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "Не введено день народження!\n";
        } else {
           if (!DateUtil.validDate(birthdayField.getText())) {
                errorMessage += "Не вірний формат дня народження, має бути : дд.мм.рррр!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Помилка!!!");
            alert.setHeaderText("Будь ласка введіть корректно дані");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}