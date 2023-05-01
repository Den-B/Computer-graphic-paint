package Helpers.Files;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BmpFile {

    private BmpBitMapFileHeader header;
    private BmpBitMapInfo info;
    private BufferedImage image;

    public BmpFile(BufferedImage image){
        this.image = image;
        header = new BmpBitMapFileHeader(BmpBitMapFileHeader.size+BmpBitMapInfo.size+image.getHeight()+image.getWidth()*4,BmpBitMapFileHeader.size+BmpBitMapInfo.size);
        info = new BmpBitMapInfo(image.getWidth(), image.getHeight());
    }

    public BmpFile(File file) throws BmpException, IOException {
        FileInputStream stream = null;
        try {
            if(!file.canRead())throw new BmpException("File with the similar name exists.");
            stream = new FileInputStream(file);
            header = new BmpBitMapFileHeader(stream);
            info = new BmpBitMapInfo(stream);
            stream.close();
            stream = new FileInputStream(file);
            readColorSet(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            stream.close();
        }
    }

    private void readColorSet(FileInputStream stream) throws BmpException {
        image = new BufferedImage(info.getWidth(), info.getHeight(),BufferedImage.TYPE_INT_RGB);
        try {
            stream.skip(header.getByteOffset());
            int imageSize = image.getWidth() * image.getHeight() * info.getNumberOfBytePerOnePixel();
            ByteBuffer buffer = ByteBuffer.allocate(imageSize).put(stream.readNBytes(imageSize));
            buffer.position(0);
            for(int row = this.image.getHeight()-1; row >=0 ; row--){
                for(int column = 0; column < image.getWidth(); column++){
                    int blue = Byte.toUnsignedInt(buffer.get());
                    int green = Byte.toUnsignedInt(buffer.get());
                    int red = Byte.toUnsignedInt(buffer.get());
                    buffer.get();
                    Color newColor = new Color(red,green,blue);
                    image.setRGB(column,row,newColor.getRGB());
                }
            }
        } catch (IOException e) {
            throw  new BmpException("It's impossible to get all pixel's values.");
        }
    }

    public void writeToFile(File file) throws BmpException, IOException {
        FileOutputStream stream = null;
        try {
            if(!file.canWrite()) throw new BmpException("Can't write to file.");
            stream = new FileOutputStream(file.getAbsoluteFile());
            header.writeToFile(stream);
            info.writeToFile(stream);
            writeToFileColors(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            stream.close();
        }
    }

    private void writeToFileColors(FileOutputStream stream) {
        ByteBuffer buffer = ByteBuffer.allocate(image.getWidth()*image.getHeight()*4);
        try {
            for(int row = this.image.getHeight()-1; row >=0 ; row--){
                for(int column = 0; column < image.getWidth(); column++){
                    Color colorAt = new Color(image.getRGB(column, row));
                    buffer.put((byte)colorAt.getBlue());
                    buffer.put((byte)colorAt.getGreen());
                    buffer.put((byte)colorAt.getRed());
                    buffer.put((byte)0);
                }
            }
            stream.write(buffer.array());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getRgbImage() {
        return image;
    }
}

class BmpBitMapFileHeader{

    private ByteBuffer BmpName = LittleEndianByteBuffer.allocate(2).putShort((short)19778);
    private final ByteBuffer fileSize = LittleEndianByteBuffer.allocate(4);
    private final ByteBuffer reserved = LittleEndianByteBuffer.allocate(4).putInt(0);
    private final ByteBuffer byteOffset = LittleEndianByteBuffer.allocate(4);
    static int size = 14;

    public BmpBitMapFileHeader(int fileSize, int byteOffset){
        this.fileSize.putInt(fileSize);
        this.byteOffset.putInt(byteOffset);
    }

    public BmpBitMapFileHeader(FileInputStream stream) throws BmpException {
        try {
            ByteBuffer buffer = LittleEndianByteBuffer.getBufferForReading(2,stream);
            short name = buffer.getShort();
            if(name == 16973 || name == 19778){
                BmpName = buffer;
            }
            else throw new BmpException("It is not bmp format.");
            fileSize.put(stream.readNBytes(4));
            buffer = LittleEndianByteBuffer.getBufferForReading(4,stream);
            if(buffer.getInt() != 0) throw new BmpException("It is not bmp format.");
            byteOffset.put(stream.readNBytes(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(FileOutputStream stream) throws BmpException {
        try {
            stream.write(BmpName.array());
            stream.write(fileSize.array());
            stream.write(reserved.array());
            stream.write(byteOffset.array());
        } catch (IOException e) {
            throw new BmpException(e.getMessage());
        }
    }

    public int getByteOffset() {
        return byteOffset.position(0).getInt();
    }
}

class BmpBitMapInfo{

    private final ByteBuffer structureSize = LittleEndianByteBuffer.allocate(4).putInt(40);
    private final ByteBuffer width = LittleEndianByteBuffer.allocate(4);
    private final ByteBuffer height = LittleEndianByteBuffer.allocate(4);
    private final ByteBuffer planes = LittleEndianByteBuffer.allocate(2).putShort((short)1);
    private ByteBuffer numberOfBytePerOnePixel = LittleEndianByteBuffer.allocate(2).putShort((short)32);
    private final ByteBuffer compressionType = LittleEndianByteBuffer.allocate(4).putInt(0);
    private final ByteBuffer sizeOfImage = LittleEndianByteBuffer.allocate(4);
    private ByteBuffer xPixelPixelPerMeter = LittleEndianByteBuffer.allocate(4).putInt(2795);
    private ByteBuffer yPixelPixelPerMeter = LittleEndianByteBuffer.allocate(4).putInt(2795);
    private ByteBuffer sizeOfTableOfColors = LittleEndianByteBuffer.allocate(4).putInt(0);
    private ByteBuffer numberOfCellsOfTableOfColors = LittleEndianByteBuffer.allocate(4).putInt(0);
    static int size = 40;

    public BmpBitMapInfo(int width, int height){
        this.width.putInt(width);
        this.height.putInt(height);
        this.sizeOfImage.putInt(width*height*4);
    }

    public BmpBitMapInfo(FileInputStream stream) throws BmpException {
        try {
            ByteBuffer buffer = LittleEndianByteBuffer.getBufferForReading(4,stream);
            if(getBeginBuffer(buffer).getInt() != 40) throw new BmpException("I can work only with 40 size structure.");
            width.put(stream.readNBytes(4));
            height.put(stream.readNBytes(4));
            buffer = LittleEndianByteBuffer.getBufferForReading(2,stream);
            if(getBeginBuffer(buffer).getShort() != 1) throw new BmpException("Wrong structure.");
            numberOfBytePerOnePixel = LittleEndianByteBuffer.allocate(2).put(stream.readNBytes(2));
            buffer = LittleEndianByteBuffer.getBufferForReading(4,stream);
            if(getBeginBuffer(buffer).getInt() != 0) throw new BmpException("It's impossible to work with this compressed file.");
            sizeOfImage.put(stream.readNBytes(4));
            xPixelPixelPerMeter = LittleEndianByteBuffer.allocate(4).put(stream.readNBytes(4));
            yPixelPixelPerMeter = LittleEndianByteBuffer.allocate(4).put(stream.readNBytes(4));
            sizeOfTableOfColors = LittleEndianByteBuffer.allocate(4).put(stream.readNBytes(4));
            numberOfCellsOfTableOfColors = LittleEndianByteBuffer.allocate(4).put(stream.readNBytes(4));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(FileOutputStream stream) throws BmpException {
        try {
            stream.write(structureSize.array());
            stream.write(width.array());
            stream.write(height.array());
            stream.write(planes.array());
            stream.write(numberOfBytePerOnePixel.array());
            stream.write(compressionType.array());
            stream.write(sizeOfImage.array());
            stream.write(xPixelPixelPerMeter.array());
            stream.write(yPixelPixelPerMeter.array());
            stream.write(sizeOfTableOfColors.array());
            stream.write(numberOfCellsOfTableOfColors.array());
        } catch (IOException e) {
            throw new BmpException(e.getMessage());
        }
    }

    public int getWidth() {
        return width.position(0).getInt();
    }

    public int getHeight() {
        return height.position(0).getInt();
    }

    public short getNumberOfBytePerOnePixel() {
        return numberOfBytePerOnePixel.position(0).getShort();
    }

    public ByteBuffer getBeginBuffer(ByteBuffer buffer){
        return buffer.position(0);
    }
}

class LittleEndianByteBuffer {

    static ByteBuffer allocate(int capacity){
        ByteBuffer buffer = ByteBuffer.allocate(capacity);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return buffer;
    }

    public static ByteBuffer getBufferForReading(int numberOfBytes, FileInputStream stream) throws IOException {
        ByteBuffer buffer = LittleEndianByteBuffer.allocate(numberOfBytes).put(stream.readNBytes(numberOfBytes));
        return buffer.position(0);
    }
}

