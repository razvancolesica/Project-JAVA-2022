
public class Student {
    String nume;
    String prenume;
    int grupa;
    public Student(String nume, String prenume, int grupa)
    {
        this.nume = nume;
        this.prenume = prenume;
        this.grupa = grupa;
    }
    @Override
    public String toString() {
        return "Student{" + "nume=" + nume + ", prenume=" + prenume + ", grupa=" +
                grupa + '}';
    }
    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        try{
            this.nume = nume;
        }
        catch (Exception e)
        {
            System.out.println("Something went wrong!");
        }
    }
    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        try{
            this.prenume = prenume;
        }catch (Exception e)
        {
            System.out.println("Something went wrong!");
        }
    }
    public int getGrupa() {
        return grupa;
    }
    public void setGrupa(int grupa) {
        try{
            this.grupa = grupa;
        }catch (Exception e)
        {
            System.out.println("Something went wrong!");
        }
    }
}