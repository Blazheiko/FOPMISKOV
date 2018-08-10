package base.model;


import javafx.beans.property.*;

import java.time.LocalDate;


/**
 * Клас модель для Особи.
 *
 *
 */
public class Person {
    private final StringProperty inn;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty patron;
    private final StringProperty street;
    private final StringProperty city;
    private final StringProperty phone;
    private final StringProperty position;
    private final DoubleProperty tariff;
    private final DoubleProperty salaryBalance;
    private final StringProperty note;

    private final ObjectProperty<LocalDate> birthday;
    private final ObjectProperty<LocalDate> dateOfRecruitment;
    private final ObjectProperty<LocalDate> dateOfDismissal ;

    public Person(String inn, String firstName, String  lastName, String patron,
                  String  street, String  city, String  phone, String  position,
                  Double  tariff, Double  salaryBalance, String  note,
                  LocalDate birthday, LocalDate dateOfRecruitment, LocalDate dateOfDismissal) {
        this.inn = new SimpleStringProperty (inn);
        this.firstName = new SimpleStringProperty (firstName);
        this.lastName = new SimpleStringProperty (lastName);
        this.patron = new SimpleStringProperty (patron);
        this.street = new SimpleStringProperty (street);
        this.city = new SimpleStringProperty (city);
        this.phone = new SimpleStringProperty (phone);
        this.position = new SimpleStringProperty (position);
        this.tariff = new SimpleDoubleProperty(0.0);
        this.salaryBalance = new SimpleDoubleProperty (0.0);
        this.note = new SimpleStringProperty (note);
        this.birthday = new SimpleObjectProperty<LocalDate>(birthday);
        this.dateOfRecruitment = new SimpleObjectProperty<LocalDate>(dateOfRecruitment);
        this.dateOfDismissal = new SimpleObjectProperty<LocalDate>(dateOfDismissal);
    }

    public Person() {
        this (null,null,null,null,null,null,null,null,0.0,
                0.0,null,null,null,null);
    }

    public String getInn() {
        return inn.get();
    }

    public StringProperty innProperty() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn.set(inn);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getPatron() {
        return patron.get();
    }

    public StringProperty patronProperty() {
        return patron;
    }

    public void setPatron(String patron) {
        this.patron.set(patron);
    }

    public String getStreet() {
        return street.get();
    }

    public StringProperty streetProperty() {
        return street;
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getPosition() {
        return position.get();
    }

    public StringProperty positionProperty() {
        return position;
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public double getTariff() {
        return tariff.get();
    }

    public DoubleProperty tariffProperty() {
        return tariff;
    }

    public void setTariff(double tariff) {
        this.tariff.set(tariff);
    }

    public double getSalaryBalance() {
        return salaryBalance.get();
    }

    public DoubleProperty salaryBalanceProperty() {
        return salaryBalance;
    }

    public void setSalaryBalance(double salaryBalance) {
        this.salaryBalance.set(salaryBalance);
    }

    public String getNote() {
        return note.get();
    }

    public StringProperty noteProperty() {
        return note;
    }

    public void setNote(String note) {
        this.note.set(note);
    }

    public LocalDate getBirthday() {
        return birthday.get();
    }

    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }

    public LocalDate getDateOfRecruitment() {
        return dateOfRecruitment.get();
    }

    public ObjectProperty<LocalDate> dateOfRecruitmentProperty() {
        return dateOfRecruitment;
    }

    public void setDateOfRecruitment(LocalDate dateOfRecruitment) {
        this.dateOfRecruitment.set(dateOfRecruitment);
    }

    public LocalDate getDateOfDismissal() {
        return dateOfDismissal.get();
    }

    public ObjectProperty<LocalDate> dateOfDismissalProperty() {
        return dateOfDismissal;
    }

    public void setDateOfDismissal(LocalDate dateOfDismissal) {
        this.dateOfDismissal.set(dateOfDismissal);
    }

    @Override
    public String toString() {
        return " - Person" + " \n"+
                "{ #" + inn.get() +"^"+"\n"+
                "  #" + firstName.get() +"^"+"\n"+
                "  #" + lastName.get() +"^"+"\n"+
                "  #" + patron.get() +"^"+"\n"+
                "  #" + street.get() +"^"+"\n"+
                "  #" + city.get() +"^"+"\n"+
                "  #" + phone.get() +"^"+"\n"+
                "  #" + position.get() +"^"+"\n"+
                "  #" + tariff.get() +"^"+"\n"+
                "  #" + salaryBalance.get() +"^"+"\n"+
                "  #" + note.get() +"^"+"\n"+
                "  #" ;
    }
}
