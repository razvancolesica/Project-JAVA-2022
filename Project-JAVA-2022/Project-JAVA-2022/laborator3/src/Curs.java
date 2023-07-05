import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Curs {
    String nume;
    String descriere;
    Profesor profu;
    Student[] studenti;
    int[] note;

    int an;

    public Curs(String nume, String descriere, int an, Profesor profu)
    {
        try{
            this.nume=nume;
            this.descriere=descriere;
            this.an=an;
            this.profu=profu;
            this.studenti=new Student[0];
            this.note=new int[studenti.length];
        }catch (Exception e)
        {
            System.out.println("ERROR 1");
        }

    }

    public void updateProfesor(Profesor profu) {
        this.profu = profu;
    }
    public void deleteTeacher() {
        this.profu = null;
    }

    public int indexOf(Student student) {
        try{
            for (int index = 0; index < studenti.length; index++) {
                if (Objects.equals(student.nume, studenti[index].nume) && Objects.equals(student.prenume, studenti[index].prenume)) {
                    return index;
                }
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            System.out.println("Index out of bounds "+e);
        }
        return -1;
    }

    public void noteaza(Student student, int nota) {
        try{
            int index = indexOf(student);
            note[index] = nota;
        }
        catch (Exception e)
        {
            System.out.println("ERROR 2");
        }
    }

    public void addStudent(Student student)
    {
        try {
            int noualungime = this.studenti.length + 1;
            Student[] aux = new Student[noualungime];
            int index = 0;
            for (Student s : studenti) {
                aux[index++] = s;
            }
            aux[index] = student;
            this.studenti = new Student[noualungime];
            System.arraycopy(aux, 0, studenti, 0, noualungime);
            int[] auxNote = new int[noualungime];
            index = 0;
            for (int n : this.note) {
                auxNote[index++] = n;
            }
            auxNote[index] = 0;
            this.note = new int[noualungime];
            System.arraycopy(auxNote, 0, this.note, 0, noualungime);
        }
        catch (Exception e)
        {
            System.out.println("ERROR 3");
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Curs: " + "nume=" + nume + ", descriere=" + descriere + "\nProfu: " + profu + "\nStudenti:\n");
        for (Student s : studenti) {
            str.append(s).append("\n");
        }
        return str.toString();
    }

    public void reportNote() {
        try{
            for(int i = 0; i < studenti.length; i++) {
                System.out.println(studenti[i] + " " + note[i]);
            }
        }
        catch (Exception e)
        {
            System.out.println("ERROR 4");
        }
    }

    public void reportStudents() {
        try{
            for (Student s: studenti) {
                System.out.println(s);
            }
        }
        catch (Exception e)
        {
            System.out.println("ERROR 5");
        }

    }
    public float mediaNotelor() {
        int sum = 0;
        for (int nota : note) {
            sum += nota;
        }
        return sum / (float) note.length;
    }
}