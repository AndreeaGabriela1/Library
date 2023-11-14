package model;

public class AudioBook extends Book {
    private int runTime; // Durata în minute a cărții audio
    /*
    public AudioBook(Long id, String author, String title, LocalDate publishedDate, int runTime) {
        super(id, author, title, publishedDate);
        this.runTime = runTime;
    }
    */
    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    @Override
    public String toString() {
        return super.toString() + " | Runtime: " + runTime + " minutes";
    }
}
