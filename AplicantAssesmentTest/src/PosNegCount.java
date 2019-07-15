public class PosNegCount {
    public int positiveCount = 0;
    public int negativeCount = 0;

    public PosNegCount(int b, int c) {s
        positiveCount = b;
        negativeCount = c;
    }

    public String toString() {
        return positiveCount + " " + negativeCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PosNegCount other = (PosNegCount) obj;

        return positiveCount == other.positiveCount && negativeCount == other.negativeCount;
    }}