public class Profesor {
    String nume;
    String prenume;
    @Override
    public String toString() {
        return "Profesor{" + "nume=" + nume + ", prenume=" + prenume + '}';
    }
    public Profesor(String nume, String prenume)
    {
        try {
            this.nume = nume;
            this.prenume = prenume;
        }catch (Exception e)
        {
            System.out.println("Something went wrong!");
        }
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
    public void setPrenume(String prenume) {
        try{
            this.prenume = prenume;
        }catch (Exception e)
        {
            System.out.println("Something went wrong!");
        }
    }
}