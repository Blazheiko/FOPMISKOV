package company.model;

import java.util.List;

public interface PersonDAO {
    public List <Person> getListPerson ();
    public List <Person> getListPersonArhiv ();
    public void apdatePersonDb (int inn);
    public void addPersonToDb ();
    public void addPersonArhiv (int inn);
    public void addPersonActiv (int inn);



}
