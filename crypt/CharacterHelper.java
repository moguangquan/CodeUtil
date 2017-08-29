
/**
 * 把字符 0~9 ,abcdef 转换为 0~9 ,abcdef的byte形式
 */
public class CharacterHelper {
    /**
     * 字符 0~9 ,abcdef 转换为 0----f byte
     * 
     * @param c
     * @return
     */
    public static byte Char2Byte(char c) {
        byte iReturn = 0;
        switch (c) {
            case '0':
                iReturn = 0x00;
                break;
            case '1':
                iReturn = 0x01;
                break;
            case '2':
                iReturn = 0x02;
                break;
            case '3':
                iReturn = 0x03;
                break;
            case '4':
                iReturn = 0x04;
                break;
            case '5':
                iReturn = 0x05;
                break;
            case '6':
                iReturn = 0x06;
                break;
            case '7':
                iReturn = 0x07;
                break;
            case '8':
                iReturn = 0x08;
                break;
            case '9':
                iReturn = 0x09;
                break;
            case 'A':
            case 'a':
                iReturn = 0x0a;
                break;
            case 'B':
            case 'b':
                iReturn = 0x0b;
                break;
            case 'C':
            case 'c':
                iReturn = 0x0c;
                break;
            case 'D':
            case 'd':
                iReturn = 0x0d;
                break;
            case 'E':
            case 'e':
                iReturn = 0x0e;
                break;
            case 'F':
            case 'f':
                iReturn = 0x0f;
                break;
        }
        
        return iReturn;
    }
}
