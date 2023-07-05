import javax.xml.parsers.ParserConfigurationException;

public class ClaseDemo {

    public static void main(String[] args) throws ParserConfigurationException {

        ManagerCursuri cursuri = new ManagerCursuri();
        cursuri.AddStudentPeAn();
        cursuri.AdaugaNote();
        cursuri.MENIU();
    }
}