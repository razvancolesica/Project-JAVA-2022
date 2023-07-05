public class StaticDatabase
{
    public static Student[] studenti = new Student[] {
            new Student("Colesica","Razvan",4712),
            new Student("Gavrilescu","Raul",4721),
            new Student("Lazlow","Szolt",4711),
            new Student("Cinipa","Alexandru",4712),
            new Student("Gamalie","Teodor",4722),
            new Student("Dragomir","Eduard",4721),
            new Student("Duruian","Laurentiu",4722),
            new Student("Pirnuta","Denis",4712),
            new Student("Nica","Ioana",4724),
            new Student("Popa","Stefania",4713),
            new Student("Georgescu","Alexandra",4722),
            new Student("Mircea","Andrei",4722),
            new Student("Simion","Razvan",4722),
            new Student("Vorovenci","Gabriel",4711),
            new Student("Szanto","Francisc",4714),
            new Student("Gavrilescu","Sabina",4713)
    };

    public static Profesor[] profesori = new Profesor[] {
            new Profesor("Popovici","Maria"),
            new Profesor("Panaitescu","Anton"),
            new Profesor("Ivanovici","Matei"),
            new Profesor("Alexandrescu","Vasile"),
            new Profesor("Patru","George"),
            new Profesor("Banateanu","Mihai")
    };

    public static Curs[] cursuri = new Curs[] {
            new Curs("PCLP2","Programare Java",2,profesori[0]),
            new Curs("ME","Masuratori electrice si electronice",2,profesori[1]),
            new Curs("PCLP1","Programare orientata pe obiecte , C++",1,profesori[0]),
            new Curs("Bazele electrotehnicii","Stabilirea bazelor in electrotehnica",1,profesori[2]),
            new Curs("Inteligenta Artificiala","Programare Phyton",3,profesori[5]),
            new Curs("Prelucrarea Semnalelor","Prelucrare de semnale cu transformate Fourier",3,profesori[4]),
            new Curs("Analiza Matematica","Calculul de integrale si derivate",4,profesori[3]),
            new Curs("Teoria semnalelor","Studiul semnalelor",4,profesori[2]),
            new Curs("Fizica","Studiul mecanicii si electrotehnicii",1,profesori[1])
    };

}
