package company.view;

import company.model.Person;
import company.util.DateUtil;
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

    @FXML
    private TextField tariffCentFeeid;

    @FXML
    private TextField balanceCentField;



    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;


    @FXML
    private void initialize() {
    }

    /**
     * Устанавливает сцену для этого окна.
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Задаёт person, информацию о котором будем менять.
     */
    public void setPerson(Person person) {
        this.person = person;

        innField.setText("" + person.getInn());
        firstNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        patronField.setText(person.getPatron());
        streetField.setText(person.getStreet());
        cityField.setText(person.getCity());
        phoneField.setText(person.getPhone());
        positionField.setText(person.getPosition());
        tariffField.setText(""+(int)person.getTariff());
        tariffCentFeeid.setText(""+person.getTariffCent());
//        salaryBalanceLabel.setText(""+person.getSalaryBalance());
//        salaryBalanceCentFe.setText(""+person.getSalaryBalanceCent());
        noteField.setText(person.getNote());
        birthdayField.setText(DateUtil.format(person.getBirthday()));
        dateOfRecruitmentField.setText(DateUtil.format(person.getDateOfRecruitment()));
        dateOfDismissalField.setText(DateUtil.format(person.getDateOfDismissal()));
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
            person.setInn (Integer.parseInt(innField.getText()));
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setPatron(patronField.getText());
            person.setStreet(streetField.getText());
            person.setCity(cityField.getText());
            person.setPhone(phoneField.getText());
            person.setPosition(positionField.getText());
            person.setTariff(Integer.parseInt(tariffField.getText()));
            person.setTariffCent(Integer.parseInt(tariffCentFeeid.getText()));
            person.setSalaryBalance(Integer.parseInt(balanceField.getText()));
            person.setSalaryBalanceCent(Integer.parseInt(balanceCentField.getText()));
            person.setNote(noteField.getText());
            person.setBirthday(DateUtil.parse(birthdayField.getText()));
            if (dateOfRecruitmentField.getText()!=null && dateOfRecruitmentField.getText().length()!=0)
                 person.setDateOfRecruitment(DateUtil.parse(dateOfRecruitmentField.getText()));
            if (dateOfDismissalField.getText()!=null && dateOfDismissalField.getText().length()!=0)
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
        if (innField.getText().trim().length() != 10) {
            errorMessage += "Кількість символів в ІНН не 10 символів!\n";
        }
        if (tariffCentFeeid.getText().trim().length() > 2 || balanceCentField.getText().trim().length() > 2) {
            errorMessage += "Забагато копійок!\n";
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
        if (dateOfRecruitmentField.getText()!=null && dateOfRecruitmentField.getText().length()!=0
                && !DateUtil.validDate(dateOfRecruitmentField.getText())) {
            errorMessage += "Не вірний формат дня прийому на роботу, має бути : дд.мм.рррр!\n";
        }
        if (dateOfDismissalField.getText()!=null && dateOfDismissalField.getText().length()!=0
                && !DateUtil.validDate(dateOfDismissalField.getText())) {
            errorMessage += "Не вірний формат дня звільнення, має бути : дд.мм.рррр!\n";
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