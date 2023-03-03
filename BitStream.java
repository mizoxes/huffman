import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitStream {

    private byte [] stream;
    public int currentPutBit;
    public int currentGetBit;

    public BitStream(int bitCount) {
        int byteCount = bitCount / 8;
        if (bitCount % 8 != 0)
            ++byteCount;

        stream = new byte[byteCount];
        currentPutBit = 0;
        currentGetBit = 0;
    }

    public void putBits(int bits, int count) {
        int currentByte = currentPutBit / 8;
        int bitsFilled = currentPutBit % 8;
        int bitsEmpty = 8 - bitsFilled;

        if (count <= bitsEmpty) {
            stream[currentByte] |= bits << (bitsEmpty - count);
            currentPutBit += count;
        } else {
            int bits1 = bits >>> (count - bitsEmpty);
            int bits2 = bits & ((1 << (count - bitsEmpty)) - 1);

            putBits(bits1, bitsEmpty);
            putBits(bits2, count - bitsEmpty);
        }
    }

    int getBits(int count) throws IndexOutOfBoundsException {
        if (currentGetBit + count > currentPutBit)
            throw new IndexOutOfBoundsException();

        int currentByte = currentGetBit / 8;
        int bitsFilled = currentGetBit % 8;
        int bitsEmpty = 8 - bitsFilled;

        int bits = 0;

        if (count <= bitsEmpty) {
            bits |= (stream[currentByte] >>> (bitsEmpty - count)) & ((1 << count) - 1);
            currentGetBit += count;
        } else {
            int bits1 = getBits(bitsEmpty);
            int bits2 = getBits(count - bitsEmpty);
            bits = (bits1 << (count - bitsEmpty)) | bits2;
        }    

        return bits;
    }

    public void putBit(int bit) {
        putBits(bit, 1);
    }

    public void putChar(char c) {
        putBits(c, 8);
    }

    public void putInt(int n) {
        putBits(n, 32);
    }

    int getBit() throws IndexOutOfBoundsException {
        return getBits(1);
    }

    char getChar() throws IndexOutOfBoundsException {
        return (char) getBits(8);
    }

    int getInt() throws IndexOutOfBoundsException {
        return getBits(32);
    }

    public void writeToFile(String fileName) throws IOException {
        OutputStream outputStream = new FileOutputStream(fileName);

        int byteCount = currentPutBit / 8;
        if (currentPutBit % 8 != 0)
            ++byteCount;

        for (int i = 0; i < byteCount; i++)
            outputStream.write(stream[i]);

        outputStream.close();
    }

    public void readFromFile(String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(fileName);

        int byteRead = -1;
        int i = 0;
        while ((byteRead = inputStream.read()) != -1) {
            stream[i++] = (byte) byteRead;
            currentPutBit += 8;
        }

        inputStream.close();
    }

    public void debug_print(int start, int end) {
        char [] c = new char[8];
        for (int i = start; i <= end; i++) {
            for (int j = 0; j < 8; j++) c[7 - j] = (char) ('0' + ((stream[i] >>> j) & 1));
            for (int j = 0; j < 8; j++) System.out.print(c[j]);
        }
    }

}