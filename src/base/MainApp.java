package base;

import base.model.Person;
import base.util.DateUtil;
import base.view.PersonEditDialogController;
import base.view.PersonOverviewController;
import base.view.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.prefs.Preferences;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    /**
     * Данные, в виде наблюдаемого списка адресатов.
     */
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    /**
     * Конструктор
     */
    public MainApp() {
     // пустой конструктор
    }

    /**
     * Возвращает данные в виде наблюдаемого списка адресатов.
     * @return
     */
    public ObservableList<Person> getPersonData() {
        return personData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("ФОП Міськов В.Ю.");
        primaryStage.setMinHeight(800);
        primaryStage.setMinWidth(1200);
       // primaryStage.setScene(new Scene(root, 1500, 1000));

        // Устанавливаем иконку приложения.
        this.primaryStage.getIcons().add(new Image("file:src/resources/images/archive.png"));

        initRootLayout();

        showPersonOverview();
    }


    public void initRootLayout() {
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(rootLayout,1200,800);
            primaryStage.setScene(scene);

            // Даём контроллеру доступ к главному прилодению.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Пытается загрузить последний открытый файл с адресатами.
        File file = getPersonFilePath();
        if (file != null) {
            loadPersonDataFromFile(file);
        }
    }

    /**
     * Показывает в корневом макете сведения о персонале
     */
    public void showPersonOverview() {
        try {
            // Загружаем сведения об адресатах.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane)loader.load();

            // Помещаем сведения об адресатах в центр корневого макета.
            rootLayout.setCenter(personOverview);

            // Даём контроллеру доступ к главному приложению.
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Открывает диалоговое окно для изменения деталей указанного адресата.
     * Если пользователь кликнул OK, то изменения сохраняются в предоставленном
     * объекте адресата и возвращается значение true.
     *
     * @param person - объект адресата, который надо изменить
     * @return true, если пользователь кликнул OK, в противном случае false.
     */
    public boolean showPersonEditDialog(Person person) {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редагування даних");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page,1200,800);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Возвращает главную сцену.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Возвращает preference файла адресатов, то есть, последний открытый файл.
     * Этот preference считывается из реестра, специфичного для конкретной
     * операционной системы. Если preference не был найден, то возвращается null.
     *
     * @return
     */
    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Задаёт путь текущему загруженному файлу. Этот путь сохраняется
     * в реестре, специфичном для конкретной операционной системы.
     *
     * @param file - файл или null, чтобы удалить путь
     */
    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Обновление заглавия сцены.
            primaryStage.setTitle("ФОП Міськов В.Ю - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Обновление заглавия сцены.
            primaryStage.setTitle("ФОП Міськов В.Ю");
        }
    }
    /**
     * Загружает информацию об адресатах из указанного файла.
     * Текущая информация об адресатах будет заменена.
     *
     * @param file
     */
    public void loadPersonDataFromFile(File file) {

        try {
            File fileLoad = new File(file.getPath());
            char[] CB = new char[(int) fileLoad.length()];
            Reader reader = new InputStreamReader(new FileInputStream(fileLoad), "Cp1251");
            reader.read(CB);
            StringToPersonData(CB) ;


        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка!!!");
            alert.setHeaderText("Файл не завантажено");
            alert.setContentText("Відсутній файл: \n" + file.getPath());

            alert.showAndWait();
        }

    }
    private void StringToPersonData(char [] charsTemp){

        String strValue = "";
        int index = 1 ;
        boolean writeValue = false ;
        Person personTemp = new Person(salaryBalanceCent, tariffCent);
        for ( int i=0 ; i<charsTemp.length; i++ ){
            if (writeValue & (charsTemp[i]!= '^')) strValue = strValue + charsTemp [i];
            else {
                switch (charsTemp[i]) {
                    case '{': {
                        index = 1;
                        personTemp = new Person(salaryBalanceCent, tariffCent);
                        break;
                    }
                    case '}':
                        personData.add(personTemp);
                        break;
                    case '#':
                        writeValue = true;
                        break;
                    case '^': {
                        switch (index) {
                            case 1: {
                                personTemp.setInn(strValue);
                                break;
                            }
                            case 2: {
                                personTemp.setFirstName(strValue);
                                break;
                            }
                            case 3: {
                                personTemp.setLastName(strValue);
                                break;
                            }
                            case 4: {
                                personTemp.setPatron(strValue);
                                break;
                            }
                            case 5: {
                                personTemp.setStreet(strValue);
                                break;
                            }
                            case 6: {
                                personTemp.setCity(strValue);
                                break;
                            }
                            case 7: {
                                personTemp.setPhone(strValue);
                                break;
                            }
                            case 8: {
                                personTemp.setPosition(strValue);
                                break;
                            }
                            case 9: {
                                //personTemp.setTariff(Double.valueOf(strValue));
                                break;
                            }
                            case 10: {
                                //personTemp.setSalaryBalance(Double.valueOf(strValue));
                                break;
                            }
                            case 11: {
                                personTemp.setNote(strValue);
                                break;
                            }
                            case 12: {
                                personTemp.setBirthday(DateUtil.parse(strValue));
                                break;
                            }
                            case 13: {
                                personTemp.setDateOfRecruitment(DateUtil.parse(strValue));
                                break;
                            }
                            case 14: {
                                personTemp.setDateOfDismissal(DateUtil.parse(strValue));

                                break;
                            }


                        }
                        writeValue = false;
                        strValue = "";
                        index++;
                        break;
                    }

                }
            }

        }


    }

    /**
     * Сохраняет текущую информацию об адресатах в указанном файле.
     *
     * @param file
     */
    public void savePersonDataToFile(File file) {

            // конвертируем данные в personData в строку
            String string = "";
            int cout = 0 ;
            for ( Person person : personData){
                cout ++ ;
                string = string +cout + person.toString();
                string = string + DateUtil.format(person.getBirthday())+"^"+"\n  #";
                string = string + DateUtil.format(person.getDateOfRecruitment())+"^"+"\n  #";
                string = string + DateUtil.format(person.getDateOfDismissal())+"^"+"  }";
            }
            string = string + "&&&" ;
            //System.out.println(string);

            // записываем строку в текстовый файл
            try (PrintWriter filenew = new PrintWriter(file.getPath(), "Cp1251")){
                 filenew.println(string);
                 filenew.close();
                setPersonFilePath(file);
            } catch (FileNotFoundException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Помилка!!!");
                alert.setHeaderText("Файл не збережено!!!");
                alert.setContentText("Данні в файлі:\n" + file.getPath()+"не збереглись");

                alert.showAndWait();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


    }

    public static void main(String[] args) {
        launch(args);
    }
}
