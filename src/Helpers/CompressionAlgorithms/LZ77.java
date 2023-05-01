package Helpers.CompressionAlgorithms;

import Helpers.CompressionAlgorithms.CompressionException.CompressionException;

import java.io.*;
import java.util.LinkedList;
import java.util.ListIterator;

public class LZ77 {

    static int dictionarySize = 1023;
    static int windowSize = 63;

    static private LZ77Data findMaxMatch(SlidingByteWindow dictionary, SlidingByteWindow window, FileInputStream stream, int firstMatchIndex, int dictionaryMaxSize) throws IOException {
        int maxMatchLength = 0;
        int globalFirstIndex = firstMatchIndex;

        int length = 0;
        int firstIndex = firstMatchIndex;

        ListIterator<Byte> dictionaryIterator = dictionary.getIterator(firstMatchIndex);
        ListIterator<Byte> windowIterator = window.getIterator();

        for(;dictionaryIterator.hasNext() && windowIterator.hasNext();){
            int nextIndex = dictionaryIterator.nextIndex();
            Byte nextValue = dictionaryIterator.next();
            if(!windowIterator.next().equals(nextValue)){
                if(length >= maxMatchLength){
                    maxMatchLength = length;
                    globalFirstIndex = firstIndex;
                }
                length = 0;
                windowIterator = window.getIterator();
                if(window.getFirst().equals(nextValue)) firstIndex = nextIndex;
                else firstIndex = findNext(dictionaryIterator, window.getFirst());
                if(dictionaryIterator.hasNext()) dictionaryIterator.previous();
            }
            else length++;
        }
        if(length >= maxMatchLength){
            globalFirstIndex = firstIndex;
            maxMatchLength = length;
        }

        byte[] buffer = window.moveForward(stream.readNBytes(Math.min(maxMatchLength, stream.available())), maxMatchLength);
        int offset = dictionary.getSize() - globalFirstIndex;
        dictionary.extend(buffer, dictionaryMaxSize);
        Byte lastCharacter = null;
        if(stream.available() > 0) lastCharacter =  readNext(window,stream);
        else{
            if(window.getSize() > 0) lastCharacter =  window.narrow();
            else return new LZ77Data((maxMatchLength - 1), offset, buffer[buffer.length - 1]);
        }
        dictionary.moveForward(lastCharacter, dictionaryMaxSize);
        return new LZ77Data(maxMatchLength, offset, lastCharacter);
    }

    static int findNext(ListIterator<Byte> iterator, Byte value){
        int index = iterator.nextIndex();
        while (iterator.hasNext()) {
            index = iterator.nextIndex();
            Byte nextValue =  iterator.next();
            if(nextValue.equals(value)){
                return index;
            }
        }
        return index - 1;
    }


    static public void compress(String inputFileName, String outputFileName) throws CompressionException {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {

            //file work
            File inputFile = new File(inputFileName);
            File outputFile = new File(outputFileName);
            if(!inputFile.exists()) throw new CompressionException("Input file must exists.");
            if(!outputFile.createNewFile()) throw new CompressionException("Output file must doesn't exist.");
            inputStream = new FileInputStream(inputFile);
            outputStream = new FileOutputStream(outputFile);

            //windows
            SlidingByteWindow window = new SlidingByteWindow(inputStream.readNBytes((int)Math.min(windowSize, inputFile.length())));
            SlidingByteWindow dictionary = new SlidingByteWindow();

            while(inputStream.available() > 0 || !window.isEmpty()){
                LZ77Data data = null;
                int firstIndex = dictionary.findIndexOf(window.getFirst());

                if(firstIndex != -1){
                    data = findMaxMatch(dictionary, window, inputStream, firstIndex, dictionarySize);
                }
                else{
                    byte character = readNext(window,inputStream);
                    data = new LZ77Data(character);
                    dictionary.moveForward(character, dictionarySize);
                }
                outputStream.write(data.getByteArray());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }

                if(inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    static public void decompress(String inputFileName, String outputFileName) throws CompressionException {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        SlidingByteWindow window = new SlidingByteWindow();

        try {

            //file work
            File inputFile = new File(inputFileName);
            File outputFile = new File(outputFileName);
            if(!inputFile.exists()) throw new CompressionException("Input file must exists.");
            if(!outputFile.createNewFile()) throw new CompressionException("Output file must doesn't exist.");
            inputStream = new FileInputStream(inputFile);
            outputStream = new FileOutputStream(outputFile);

            while(inputStream.available() > 0){
                LZ77Data data = new LZ77Data(inputStream.readNBytes(3));
                if(data.isEmpty()){
                    outputStream.write(data.getData());
                    window.moveForward(data.getData(), dictionarySize);
                }
                else{
                    byte[] buffer = new byte[data.getLength() + 1];
                    for(int index = 0; index < data.getLength(); index++){
                        Byte value =  window.get(window.getSize() - data.getOffset() + index);
                        buffer[index] = value;
                    }
                    buffer[buffer.length - 1] = data.getData();
                    outputStream.write(buffer);
                    window.extend(buffer, dictionarySize);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }

                if(inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    static private Byte readNext(SlidingByteWindow window,FileInputStream stream) throws IOException {
        if(stream.available() > 0) return window.moveForward((byte) stream.read());
        else return  window.narrow();
    }


}

class SlidingByteWindow{

    private LinkedList<Byte> list = new LinkedList<Byte>();

    public SlidingByteWindow(){}

    public SlidingByteWindow(byte[] initArray){
        for(byte value: initArray){
            list.add(value);
        }
    }

    public void extend(Byte value){
        list.addLast(value);
    }

    public void extend(byte[] array){
       for(byte value : array){
           this.extend(value);
       }
    }

    public void extend(byte[] values, int maxSize){
        int index = 0;
        for(;index < Math.min(values.length,maxSize -  this.getSize()); index++){
            this.extend(values[index]);
        }
        for(;index < values.length; index++){
            this.moveForward(values[index]);
        }
    }


    public void extend(LinkedList<Byte> values, int maxSize){
        int index = 0;
        for(;index <= Math.min(maxSize, values.size() - 1); index++){
            this.extend(values.get(index));
        }
        for(;index < values.size(); index++){
            this.extend(values.get(index));
        }
    }

    public Byte narrow(){
        return list.removeFirst();
    }


    public Byte moveForward(byte value){
        this.extend(value);
        return narrow();
    }

    public void moveForward(byte value, int maxSize){
        if(this.getSize() < maxSize) this.extend(value);
        else this.moveForward(value);
    }

    public byte[] moveForward(byte[] array, int needToDoMoves){
        byte[] result = new byte[needToDoMoves];
        for(int index = 0; index < array.length; index++){
            result[index] = this.moveForward(array[index]);
        }
        Byte[] buffer = narrow(Math.max(0, needToDoMoves - array.length));
        for(int resultIndex = array.length, index = 0;index < buffer.length ; resultIndex++, index++){
            result[resultIndex] =  buffer[index];
        }
        return result;
    }

    public byte[] moveForward(byte[] array){
        byte[] buffer = new byte[array.length];
        for(int index = 0; index < array.length; index++){
            buffer[index] = this.moveForward(array[index]);
        }
        return buffer;
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }

    public int findIndexOf(Byte value){
        return list.indexOf(value);
    }

    public ListIterator<Byte> getIterator(){
        return list.listIterator();
    }

    public ListIterator<Byte> getIterator(int index){
        return list.listIterator(index);
    }

    public Byte getFirst(){
        return list.getFirst();
    }

    public Byte getLast(){
        return list.getFirst();
    }

    public int getSize(){
        return list.size();
    }

    public Byte[] narrow(int numberOf){
        Byte[] array = new Byte[numberOf];
        for(int number = 0;number < numberOf; number++){
            array[number] = this.narrow();
        }
        return array;
    }

    public Byte get(int index){
        return list.get(index);
    }

}

class LZ77Data{

    private int length = 1;
    private int offset = 0;
    private byte data;

    public LZ77Data(byte data){
        this.data = data;
    }

    public LZ77Data(int length,int offset, byte data){
        this.data = data;
        this.length = length;
        this.offset = offset;
    }

    public LZ77Data(byte[] data) throws CompressionException {
        if(data.length != 3) throw new CompressionException("This byte array can't be converted into LZ77DATA.");
        this.data = data[2];
        this.length = (byte)(Byte.toUnsignedInt(data[1]) & 63);
        this.offset = Byte.toUnsignedInt(data[0])<<2 | Byte.toUnsignedInt(data[1])>>6;
    }

    public byte[] getByteArray(){
        byte[] byteData = new byte[3];
        byteData[0] = (byte)(offset>>2);
        byteData[1] = (byte)((offset<<6) | length);
        byteData[2] = data;
        return byteData;
    }

    public boolean isEmpty(){
        return offset == 0;
    }

    public int getLength() {
        return length;
    }

    public int getOffset() {
        return offset;
    }

    public byte getData(){
        return data;
    }

}