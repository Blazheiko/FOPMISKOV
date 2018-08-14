package company;


import company.model.Person;
import company.util.DateUtil;
import company.view.PersonEditDialogController;
import company.view.PersonOverviewController;
import company.view.RootLayoutController;

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

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private ObservableList<Person> personData = FXCollections.observableArrayList();

    public ObservableList<Person> getPersonData() {
        return personData;
    }


    public Main() {

    }



    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("FOP Miskov VU");
        primaryStage.setMinHeight(800);
        primaryStage.setMinWidth(1200);
       // primaryStage.setScene(new Scene(root, 1500, 1000));


        this.primaryStage.getIcons().add(new Image("file:src/resources/images/archive.png"));

        initRootLayout();

        showPersonOverview();
    }


    public void initRootLayout() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class
                    .getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();


            Scene scene = new Scene(rootLayout,1200,800);
            primaryStage.setScene(scene);


            RootLayoutController controller = loader.getController();
            controller.setMain(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = getPersonFilePath();
        if (file != null) {
            loadPersonDataFromFile(file);
        }
    }


    public void showPersonOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane)loader.load();

            rootLayout.setCenter(personOverview);

            PersonOverviewController controller = loader.getController();
            controller.setMain(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showPersonEditDialog(Person person) {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();


            Stage dialogStage = new Stage();
            dialogStage.setTitle("??????????? ?????");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page,1200,800);
            dialogStage.setScene(scene);

            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }


    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }


    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            primaryStage.setTitle("FOP Miskov - " + file.getName());
        } else {
            prefs.remove("filePath");

            primaryStage.setTitle("FOP Miskov ");
        }
    }

    public void loadPersonDataFromFile(File file) {

        try {
            File fileLoad = new File(file.getPath());
            char[] CB = new char[(int) fileLoad.length()];
            Reader reader = new InputStreamReader(new FileInputStream(fileLoad), "Cp1251");
            reader.read(CB);
            StringToPersonData(CB) ;


        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("???????!!!");
            alert.setHeaderText("???? ?? ???????????");
            alert.setContentText("??????? ????: \n" + file.getPath());

            alert.showAndWait();
        }

    }
    private void StringToPersonData(char [] charsTemp){

        String strValue = "";
        int index = 1 ;
        boolean writeValue = false ;
        Person personTemp = new Person();
        for ( int i=0 ; i<charsTemp.length; i++ ){
            if (writeValue & (charsTemp[i]!= '^')) strValue = strValue + charsTemp [i];
            else {
                switch (charsTemp[i]) {
                    case '{': {
                        index = 1;
                        personTemp = new Person();
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
                                personTemp.setTariff(Integer.valueOf(strValue));
                                break;
                            }
                            case 10: {
                                personTemp.setTariffCent(Integer.valueOf(strValue));
                                break;
                            }
                            case 11: {
                                personTemp.setSalaryBalance(Integer.valueOf(strValue));
                                break;
                            }
                            case 12: {
                                personTemp.setSalaryBalanceCent(Integer.valueOf(strValue));
                                break;
                            }
                            case 13: {
                                personTemp.setNote(strValue);
                                break;
                            }
                            case 14: {
                                personTemp.setBirthday(DateUtil.parse(strValue));
                                break;
                            }
                            case 15: {
                                personTemp.setDateOfRecruitment(DateUtil.parse(strValue));
                                break;
                            }
                            case 16: {
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


    public void savePersonDataToFile(File file) {

            String string = "";
            int cout = 0 ;
            for ( Person person : personData){
                cout ++ ;
                string = string +cout + person.toString();
                string = string + DateUtil.format(person.getBirthday())+"^"+"\n  #";
                string = string + DateUtil.format(person.getDateOfRecruitment())+"^"+"\n  #";
                string = string + DateUtil.format(person.getDateOfDismissal())+"^"+"  }";
            }
            string = string + "&&& \n " ;
            //System.out.println(string);


            try (PrintWriter filenew = new PrintWriter(file.getPath(), "Cp1251")){
                 filenew.println(string);
                 filenew.close();
                setPersonFilePath(file);
            } catch (FileNotFoundException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!!!");
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
