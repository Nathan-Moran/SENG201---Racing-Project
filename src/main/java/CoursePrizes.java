public class CoursePrizes {
    private int firstPlacePrize;
    private int secondPlacePrize;
    private int thirdPlacePrize;

    public CoursePrizes(int firstPlacePrize, int secondPlacePrize, int thirdPlacePrize) {
        this.firstPlacePrize = firstPlacePrize;
        this.secondPlacePrize = secondPlacePrize;
        this.thirdPlacePrize = thirdPlacePrize;
    }

    public int getFirstPlacePrize() {
        return firstPlacePrize;
    }

    public int getSecondPlacePrize() {
        return secondPlacePrize;
    }

    public int getThirdPlacePrize() {
        return thirdPlacePrize;
    }

    @Override
    public String toString() {
        return "Prizes -> 1st: $" + firstPlacePrize +
                ", 2nd: $" + secondPlacePrize +
                ", 3rd: $" + thirdPlacePrize;
    }
}