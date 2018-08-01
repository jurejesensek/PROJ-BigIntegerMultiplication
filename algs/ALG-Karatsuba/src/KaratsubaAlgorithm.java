import java.math.BigInteger;

public class KaratsubaAlgorithm extends BigIntegerMultiplicationAbsAlgorithm {

    private final static BigInteger LIMIT = new BigInteger(String.valueOf(Byte.MAX_VALUE));

    @Override
    protected void execute(byte[] firstFactor, byte[] secondFactor, byte[] result) {
        byte[] r = karatsuba(new BigInteger(firstFactor), new BigInteger(secondFactor)).toByteArray();
        for (int i = 0; i < r.length; i++) {
            result[i] = r[i];
        }
    }

    private BigInteger karatsuba(BigInteger first, BigInteger second) {
        // use simple multiplication for smallish numbers
        if (first.compareTo(LIMIT) < 0 || second.compareTo(LIMIT) < 0) {
            return first.multiply(second);
        }

        // size of numbers
        int m = Math.max(first.bitLength(), second.bitLength());
        // half the size, rounded up
        m = (m / 2) + (m % 2);

        // split the sequences
        BigInteger firstLow = first.shiftRight(m);
        BigInteger firstHigh = first.subtract(firstLow.shiftLeft(m));
        BigInteger secondLow = second.shiftRight(m);
        BigInteger secondHigh = second.subtract(secondLow.shiftLeft(m));

        // calculate sub-expressions
        BigInteger z2 = karatsuba(firstHigh, secondHigh);
        BigInteger z0 = karatsuba(firstLow, secondLow);
        BigInteger z1 = karatsuba(firstLow.add(firstHigh), secondLow.add(secondHigh));

        return z2.add(z1.subtract(z2).subtract(z0).shiftLeft(m)).add(z0.shiftLeft(m * 2));
    }
}
