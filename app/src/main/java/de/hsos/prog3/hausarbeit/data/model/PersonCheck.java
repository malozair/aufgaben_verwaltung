package de.hsos.prog3.hausarbeit.data.model;
public class PersonCheck {
    private Person person;
    private boolean isChecked;

    public PersonCheck(Person person, boolean isChecked) {
        this.person = person;
        this.isChecked = isChecked;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
