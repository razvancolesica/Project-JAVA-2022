import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.*;
import java.io.IOException;
public class ManagerCursuri {
    ArrayList<Curs> cursuri = new ArrayList<>();
    ArrayList<Student> studenti = new ArrayList<>();
    ArrayList<Profesor> profesori = new ArrayList<>();
    ArrayList<Student> studentiXML = new ArrayList<>();
    ArrayList<Profesor> profesoriXML = new ArrayList<>();

    private boolean readFromXML = false;
    public ManagerCursuri() throws ParserConfigurationException {
        cursuri.addAll(List.of(StaticDatabase.cursuri));
        if(readFromXML==false)               //INCARCA DATELE PT STUDENTI SI PROFESORI DIN STATICDATABASE
        {
            studenti.addAll(List.of(StaticDatabase.studenti));
            profesori.addAll(List.of(StaticDatabase.profesori));
        }
        else {                           //INCARCA DATELE PT STUDENTI SI PROFESORI DIN FISIERELE XML
            ReadXMLfileStudents();
            studenti.addAll(studentiXML);
            ReadXMLfileTeachers();
            profesori.addAll(profesoriXML);
        }
    }
    int indexOf(Curs curs) {
        try {
            for (int index = 0; index < cursuri.size(); index++) {
                if (Objects.equals(cursuri.get(index).nume, curs.nume)) {
                    return index;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds " + e);
        }
        return -1;
    }
    void replaceStudent(Curs fromCurs, Student oldStudent, Student withNewStudent) {
        Curs curs = cursuri.get(this.indexOf(fromCurs));
        curs.studenti[curs.indexOf(oldStudent)] = withNewStudent;
    }

    public void modifica(Curs cursExistent, Curs newCurs) {
        int index = this.indexOf(cursExistent);
        this.cursuri.set(index, newCurs);
    }

    public void afiseazaCursuriLaConsola() {
        for (Curs c : cursuri)
            System.out.println(c);
    }
    // Raport note tuturor studentilor
    public void reportNote() {
        for (Curs c : cursuri) {
            c.reportNote();
        }
    }
    public void reportStudents(Curs curs) {
        this.cursuri.get(indexOf(curs)).reportStudents();
    }
    // Raport note la un anumit curs
    public void reportNote(Curs curs) {
        System.out.println(curs.nume);
        this.cursuri.get(indexOf(curs)).reportNote();
        System.out.println();
    }
    // Raport note date de un profesor
    public float reportMediaNotelor(Profesor profesor) {
        float mediaNotelor = 0.0f;
        int count = 0;
        for (Curs c : cursuri) {
            if (c.profu.nume.equals(profesor.nume) && c.profu.prenume.equals(profesor.prenume)) {
                mediaNotelor += c.mediaNotelor();
                count++;
            }
        }
        return mediaNotelor / count;
    }

    public void AddStudentPeAn() {                                // ADAUGARE STUDENTI PE AN
        try {
            for (Curs curs : cursuri) {
                for (Student student : studenti) {
                    if (student.grupa % 10 == curs.an) {
                        curs.addStudent(student);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR 6");
        }
    }
    public int GenerateRandom() {
        int min = 1;
        int max = 10;
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }

    public void noteStudent(Student student) {                                 // NOTE STUDENT LA CURS
        int cnt = 0;
        try {
            for (Curs curs : cursuri) {
                for (int k = 0; k < curs.note.length; k++) {
                    if (student.grupa % 10 == curs.an) {
                        System.out.println(curs.nume + " " + curs.note[k]);
                        cnt++;
                        break;
                    }
                }
            }
            if (cnt == 0) {
                throw new Exception("The student does not have grades!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void restante(Student student) {                           // RESTANTE STUDENT
        int cnt = 0;
        try {
            for (Curs curs : cursuri) {
                for (int k = 0; k < curs.note.length; k++) {
                    if (student.grupa % 10 == curs.an) {
                        if (curs.note[k] < 5) {
                            System.out.println("Restanta la " + curs.nume);
                            cnt++;
                        } else {
                            System.out.println("Ai trecut la " + curs.nume);
                            cnt++;
                        }
                        break;
                    }
                }
            }
            if (cnt == 0) {
                throw new Exception("The student does not have grades!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public float mediaStudent(Student student) {                     // MEDIA NOTELOR STUDENTULUI
        float media = 0;
        float cnt = 0;
        float suma = 0;
        try {
            for (Curs curs : cursuri) {
                for (int k = 0; k < curs.note.length; k++) {
                    if (student.grupa % 10 == curs.an) {
                        suma = suma + curs.note[k];
                        cnt++;
                        break;
                    }
                }
            }
            if (cnt != 0) {
                media = suma / cnt;
            } else {
                throw new Exception("\nThe student does not have a grade point average!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return media;
    }

    public void AdaugaNote() {                    //ADAUGARE NOTE STUDENTI
        int cnt = 0;
        try {
            for (Curs curs : cursuri) {
                for (Student student : studenti) {
                    if (student.grupa % 10 == curs.an) {
                        curs.noteaza(student, GenerateRandom());
                        cnt++;
                    }
                }
            }
            if (cnt == 0) {
                throw new Exception("Student has no courses!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void profLaCurs(Profesor profesor) {                     // CURSURILE LA  CARE ESTE ASOCIAT PROFESORUL
        int cnt = 0;
        try {
            for (Curs curs : cursuri) {
                if (Objects.equals(curs.profu.nume, profesor.nume)) {
                    System.out.println(curs.nume);
                    cnt++;
                }
            }
            if (cnt == 0)
                throw new Exception("Courses not found!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void medieNoteLaCurs(Profesor profesor) {                 // MEDIA NOTELOR ACORDATE DE PROFESOR
        float media = 0;
        float suma = 0;
        float cnt = 0;
        try {
            for (Curs curs : cursuri) {
                for (int j = 0; j < curs.note.length; j++) {
                    if (Objects.equals(curs.profu.nume, profesor.nume)) {
                        suma = suma + curs.note[j];
                        cnt++;
                    }
                }
            }
            if (cnt != 0) {
                media = suma / cnt;
                System.out.println("Media este " + media);
            } else {
                throw new Exception("Courses not found");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void noteProfesor(Profesor profesor) {                     // NOTELE ACORDATE DE PROFESOR
        int cnt = 0;
        try {
            for (Curs curs : cursuri) {
                int k = 0;
                while (k < curs.note.length) {
                    if (Objects.equals(curs.profu.nume, profesor.nume)) {
                        System.out.println(curs.nume + " " + curs.note[k]);
                        cnt++;
                    }
                    k++;
                }
            }
            if (cnt == 0)
                throw new Exception("Courses not found!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void addProfesorToFile()                   // ADAUGARE PROFESOR IN XML PRIN CITIRE DE LA TASTATURA CU REGENERARE DE FISIER
    {
        Profesor profNou = new Profesor("","");
        Scanner citeste = new Scanner(System.in);
        System.out.println("Nume:");
        String citesteNume = citeste.nextLine();
        System.out.println("Prenume:");
        String citestePrenume = citeste.nextLine();
        profNou.setNume(citesteNume);
        profNou.setPrenume(citestePrenume);
        profesori.add(profNou);
        final String xmlFilePath = "C:\\Users\\razva\\Desktop\\Java facultate\\Java laborator\\laborator3\\laborator3\\ProfesoriDatabase.xml";
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element rootElement = document.createElement("PROFESORI");
            document.appendChild(rootElement);

            for(Profesor i:profesori)
            {
                Element student = document.createElement("Profesor");
                rootElement.appendChild(student);
                Element nume = document.createElement("Nume");
                nume.appendChild(document.createTextNode(i.nume));
                student.appendChild(nume);
                Element prenume = document.createElement("Prenume");
                prenume.appendChild(document.createTextNode(i.prenume));
                student.appendChild(prenume);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");

        } catch (ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
        }
    }
    public void addStudentToFile()              // ADAUGARE STUDENT IN XML PRIN CITIRE DE LA TASTATURA CU REGENERARE DE FISIER
    {
        Student studentNou = new Student("","",0);
        Scanner citeste = new Scanner(System.in);
        System.out.println("Nume:");
        String citesteNume = citeste.nextLine();
        System.out.println("Prenume:");
        String citestePrenume = citeste.nextLine();
        System.out.println("Grupa:");
        int citesteGrupa = citeste.nextInt();
        studentNou.setNume(citesteNume);
        studentNou.setPrenume(citestePrenume);
        studentNou.setGrupa(citesteGrupa);
        studenti.add(studentNou);
        final String xmlFilePath = "C:\\Users\\razva\\Desktop\\Java facultate\\Java laborator\\laborator3\\laborator3\\StudentiDatabase.xml";
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element rootElement = document.createElement("STUDENTI");
            document.appendChild(rootElement);

            for(Student i:studenti)
            {
                Element student = document.createElement("Student");
                rootElement.appendChild(student);
                Attr grupa = document.createAttribute("Grupa");
                grupa.setValue(String.valueOf(i.grupa));
                student.setAttributeNode(grupa);
                Element nume = document.createElement("Nume");
                nume.appendChild(document.createTextNode(i.nume));
                student.appendChild(nume);
                Element prenume = document.createElement("Prenume");
                prenume.appendChild(document.createTextNode(i.prenume));
                student.appendChild(prenume);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");

        } catch (ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
        }
    }

    public void CreateXMLfileStudents() {
        final String xmlFilePath = "C:\\Users\\razva\\Desktop\\Java facultate\\Java laborator\\laborator3\\laborator3\\StudentiDatabase.xml";
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element rootElement = document.createElement("STUDENTI");
            document.appendChild(rootElement);

            for (Student value : studenti) {
                Element student = document.createElement("Student");
                rootElement.appendChild(student);                                      // GENERARE DE FISIER CU STUDENTI
                Attr grupa = document.createAttribute("Grupa");
                grupa.setValue(String.valueOf(value.grupa));
                student.setAttributeNode(grupa);
                Element nume = document.createElement("Nume");
                nume.appendChild(document.createTextNode(value.nume));
                student.appendChild(nume);
                Element prenume = document.createElement("Prenume");
                prenume.appendChild(document.createTextNode(value.prenume));
                student.appendChild(prenume);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");

        } catch (ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
        }
    }

    public void ReadXMLfileStudents() throws ParserConfigurationException {
        final String FILENAME = "C:\\Users\\razva\\Desktop\\Java facultate\\Java laborator\\laborator3\\laborator3\\StudentiDatabase.xml";

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(FILENAME));
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("Student");                                //CITIRE STUDENTI DIN FISIER XML
            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element element = (Element) node;
                    String nume = element.getElementsByTagName("Nume").item(0).getTextContent();
                    String prenume = element.getElementsByTagName("Prenume").item(0).getTextContent();
                    int grupa = Integer.parseInt(element.getAttribute("Grupa"));
                    Student studentNou = new Student("","",0);
                    studentNou.setNume(nume);
                    studentNou.setPrenume(prenume);
                    studentNou.setGrupa(grupa);
                    studentiXML.add(studentNou);
                }
            }
        } catch (IOException | SAXException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void CreateXMLfileTeachers() {
        final String xmlFilePath = "C:\\Users\\razva\\Desktop\\Java facultate\\Java laborator\\laborator3\\laborator3\\ProfesoriDatabase.xml";
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element rootElement = document.createElement("PROFESORI");
            document.appendChild(rootElement);

            for (Profesor profesor : profesori) {                                                     // GENERARE DE FISIER XML CU PROFESORI
                Element student = document.createElement("Profesor");
                rootElement.appendChild(student);
                Element nume = document.createElement("Nume");
                nume.appendChild(document.createTextNode(profesor.nume));
                student.appendChild(nume);
                Element prenume = document.createElement("Prenume");
                prenume.appendChild(document.createTextNode(profesor.prenume));
                student.appendChild(prenume);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");

        } catch (ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
        }
    }

    public void ReadXMLfileTeachers() throws ParserConfigurationException {
        final String FILENAME = "C:\\Users\\razva\\Desktop\\Java facultate\\Java laborator\\laborator3\\laborator3\\ProfesoriDatabase.xml";

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(FILENAME));
            doc.getDocumentElement().normalize();                                              // CITIRE PROFESORI DIN FISIER XML
            NodeList list = doc.getElementsByTagName("Profesor");
            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element element = (Element) node;
                    String nume = element.getElementsByTagName("Nume").item(0).getTextContent();
                    String prenume = element.getElementsByTagName("Prenume").item(0).getTextContent();
                    Profesor profNou = new Profesor("","");
                    profNou.setNume(nume);
                    profNou.setPrenume(prenume);
                    profesoriXML.add(profNou);
                }
            }
        } catch (IOException | SAXException ex) {
            throw new RuntimeException(ex);
        }
    }



    public int contorStudenti(Curs curs)
    {
        int cnt=0;
        for (Curs c : cursuri) {
            for (Student student : studenti) {
                if (student.grupa % 10 == c.an && curs.an == c.an) {
                    cnt++;
                }
            }
        }
        return cnt;
    }
    class nrStudentiCompare implements Comparator<Curs>
    {
        public int compare(Curs c1,Curs c2)
        {
            return Integer.compare(contorStudenti(c1), contorStudenti(c2));
        }
    }
    public void sortareCursStudenti()                 // SORTAREA CURSURILOR DUPA NR DE STUDENTI
    {
        cursuri.sort(new nrStudentiCompare());
        afiseazaCursuriLaConsola();
    }

    class numeCompare implements Comparator<Curs>
    {
        public int compare(Curs c1,Curs c2)
        {
            return Integer.compare(c1.nume.compareTo(c2.nume), 0);
        }
    }
    public void sortareCursNume()                // SORTAREA CURSURILOR DUPA NUME
    {
        cursuri.sort(new numeCompare());
        afiseazaCursuriLaConsola();
    }

    class profesoriCompare implements Comparator<Curs>
    {
        public int compare(Curs c1, Curs c2)
        {
            return Integer.compare(c1.profu.nume.compareTo(c2.profu.nume),0);
        }
    }
    public void sortareCursProfesori()           // SORTAREA CURSURILOR DUPA NUMELE PROFESORILOR
    {
        cursuri.sort(new profesoriCompare());
        afiseazaCursuriLaConsola();
    }

    class GrupaComparator implements Comparator<Student>
    {
        public int compare(Student s1, Student s2)
        {
            return Integer.compare(s1.grupa, s2.grupa);
        }
    }

    public void sortareStudenti()            // SORTAREA STUDENTILOR
    {
        studenti.sort(new GrupaComparator());
        for(Student s:studenti)
        {
            System.out.println(s);
        }
    }

    public void MENIU()
    {
        Scanner citeste = new Scanner(System.in);
        System.out.print("Username:");
        String readName = citeste.nextLine();
        System.out.print("Password:");
        String readPassword = citeste.nextLine();
        for(Student student: studenti)                            // PARTEA DE CONECTARE SI MENIU PENTRU STUDENTI
        {
            String username = student.nume + "." + student.prenume;
            String password = student.nume.toLowerCase() + student.prenume.toLowerCase();

            if ( username.equals(readName) && password.equals(readPassword)) {
                try {
                    System.out.print("\nMedia notelor:");
                    System.out.println(mediaStudent(student));
                    System.out.println("-----------------------------------------");
                    System.out.println("Notele la cursuri:");
                    noteStudent(student);
                    System.out.println("-----------------------------------------");
                    System.out.println("Restante: ");
                    restante(student);
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        for(Profesor profesor: profesori)                        // PARTEA DE CONECTARE SI MENIU PENTRU PROFESORI
        {
            String username = profesor.nume + "." + profesor.prenume;
            String password = profesor.nume.toLowerCase() + profesor.prenume.toLowerCase();
            if ( username.equals(readName) && password.equals(readPassword))
            {
                try
                {
                    System.out.println("\nCursurile la care este asociat:");
                    profLaCurs(profesor);
                    System.out.println("-----------------------------------------");
                    System.out.println("\nMedia notelor acordate:");
                    medieNoteLaCurs(profesor);
                    System.out.println("-----------------------------------------");
                    System.out.println("\nNote la cursuri:");
                    noteProfesor(profesor);
                }
                catch(Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
        }
        if( readName.equals("admin") && readPassword.equals("admin"))                // PARTEA CONECTARE LA ADMIN
        {
            try{
                Admin();
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("-----------------------------------------");
        System.out.println("Alegeti x pentru a inchide meniul sau ok pentru a continua");
        System.out.println("\n");

        String close = citeste.next();
        if (Objects.equals(close, "x")) {
            System.out.println("Ati inchis meniul!");
        } else if (Objects.equals(close, "ok")) {
            MENIU();
        }
    }
    public void Admin()                                        // PARTEA DE MENIU PENTRU ADMIN
    {
        Scanner citeste = new Scanner(System.in);
        try{
            System.out.println("""
                        Optiuni:
                        a) Genereaza baza de fisiere pentru studenti.
                        b) Adauga student.
                        c) Genereaza baza de date pentru profesori.
                        d) Adauga profesor.
                        e) Sorteaza studentii.
                        f) Sorteaza cursurile dupa numarul studentilor.
                        g) Sorteaza cursurile dupa nume.
                        h) Sorteaza cursurile dupa numele profesorilor.""");
            String open=citeste.next();
            switch (open) {
                case "a" -> CreateXMLfileStudents();
                case "b" -> addStudentToFile();
                case "c" -> CreateXMLfileTeachers();
                case "d" -> addProfesorToFile();
                case "e" -> sortareStudenti();
                case "f" -> sortareCursStudenti();
                case "g" -> sortareCursNume();
                case "h" -> sortareCursProfesori();
            }
        }
        catch (Exception e)
        {
            System.out.println("Something went wrong");
        }
    }
}