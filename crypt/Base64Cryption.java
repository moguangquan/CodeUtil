import java.io.UnsupportedEncodingException;
import org.apache.axiom.util.base64.Base64Utils;
import exception.CRM2Errcodes;
import exception.CRM2Exception;

/**
 * Base64加解密
 */
public class Base64Cryption {
    /**
     * 加密
     * 
     * @param input
     *            明文
     * @return 密文
     */
    public static String encrypt(String input) {
        return encrypt(input.getBytes());
    }
    
    /**
     * 加密
     * 
     * @param input
     *            明文
     * @return 密文
     */
    public static String encrypt(byte[] input) {
        return Base64Utils.encode(input);
    }
    
    /**
     * 解密
     * 
     * @param input
     *            密文
     * @return 明文
     */
    public static byte[] decrypt(String input) {
        return Base64Utils.decode(input);
    }
    
    public static String decryptToStr(String input) {
        try {
            return new String(Base64Utils.decode(input), "GBK");
        } catch (UnsupportedEncodingException e) {
            throw new CRM2Exception(CRM2Errcodes.CRYPT_ERROR, "Base64解密错误!", e).addParam("errMsg",
                e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        String xmlStr = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" standalone=\"yes\" ?>"
            + "<RESULTS xmlns=\"XML\" sets=\"1\">"
            + "<RESULT cols=\"50\" datatype=\"ORDSCHDL\" rows=\"1\" setnum=\"1\">"
            + "<row rownum=\"1\">"
            + "<col colnum=\"1\" name=\"TASK_ID\" type=\"\">209991604955</col>"
            + "<col colnum=\"2\" name=\"OS_ID\" type=\"\">201498996137</col>"
            + "<col colnum=\"3\" name=\"REQ_TYPE\" type=\"\">NORMAL</col>"
            + "<col colnum=\"4\" name=\"ABNORMAL_ID\" type=\"\">0</col>"
            + "<col colnum=\"5\" name=\"IS_CANCEL\" type=\"\">S0F</col>"
            + "<col colnum=\"6\" name=\"ROLL_TASK_ID\" type=\"\">0</col>"
            + "<col colnum=\"7\" name=\"REQ_ID\" type=\"\">207466255385</col>"
            + "<col colnum=\"8\" name=\"SUBS_ID\" type=\"\">213373233507</col>"
            + "<col colnum=\"9\" name=\"CUST_ID\" type=\"\">2979466141</col>"
            + "<col colnum=\"10\" name=\"SERV_ID\" type=\"\">3748029302</col>"
            + "<col colnum=\"11\" name=\"PROD_CODE\" type=\"\">CUST</col>"
            + "<col colnum=\"12\" name=\"ACTION_CODE\" type=\"\">YH001</col>"
            + "<col colnum=\"13\" name=\"PRIORITY\" type=\"\">1</col>"
            + "<col colnum=\"14\" name=\"EXCHANGE_ID\" type=\"\">0</col>"
            + "<col colnum=\"15\" name=\"OP_STAFF\" type=\"\">system</col>"
            + "<col colnum=\"16\" name=\"REMARK\" type=\"\"></col>"
            + "<col colnum=\"17\" name=\"PREWARM_TIME\" type=\"\">20131017095036</col>"
            + "<col colnum=\"18\" name=\"WARM_TIME\" type=\"\">20131017095036</col>"
            + "<col colnum=\"19\" name=\"COMPHDL_STATE\" type=\"\">S0F</col>"
            + "<col colnum=\"20\" name=\"TIME_LEV\" type=\"\"></col>"
            + "<col colnum=\"21\" name=\"CITY_ID\" type=\"\">200</col>"
            + "<col colnum=\"22\" name=\"SWITCHTYPE\" type=\"\"></col>"
            + "<col colnum=\"23\" name=\"DPCODE\" type=\"\"></col>"
            + "<col colnum=\"24\" name=\"CABCODE\" type=\"\"></col>"
            + "<col colnum=\"25\" name=\"TRUNKCODE\" type=\"\"></col>"
            + "<col colnum=\"26\" name=\"ONUCODE\" type=\"\"></col>"
            + "<col colnum=\"27\" name=\"OBDCODE\" type=\"\"></col>"
            + "<col colnum=\"28\" name=\"ZHXCODE\" type=\"\"></col>"
            + "<col colnum=\"29\" name=\"LAN_CODE\" type=\"\"></col>"
            + "<col colnum=\"30\" name=\"REQ_CODE\" type=\"\">GZ20131015420101286</col>"
            + "<col colnum=\"31\" name=\"SUBS_CODE\" type=\"\">200131015575276618</col>"
            + "<col colnum=\"32\" name=\"CUST_NBR\" type=\"\"></col>"
            + "<col colnum=\"33\" name=\"SERV_NBR\" type=\"\"></col>"
            + "<col colnum=\"34\" name=\"ACC_NBR\" type=\"\"></col>"
            + "<col colnum=\"35\" name=\"ONU_FROM\" type=\"\"></col>"
            + "<col colnum=\"36\" name=\"ONU_EQPID\" type=\"\"></col>"
            + "<col colnum=\"37\" name=\"ORD_SOURCE\" type=\"\">OS</col>"
            + "<col colnum=\"38\" name=\"ORD_TYPE\" type=\"\">WO0987</col>"
            + "<col colnum=\"39\" name=\"STEP_TYPE\" type=\"\">8963</col>"
            + "<col colnum=\"40\" name=\"FEE_HDL\" type=\"\">1</col>" + "</row>" + "</RESULT>"
            + "</RESULTS>";
        String strEncrypt = Base64Cryption.encrypt(xmlStr);
        System.out.println("strEncrypt=" + strEncrypt);
        try {
            String s = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iSVNPLTg4NTktMSIgc3RhbmRhbG9uZT0ieWVzIiA/Pgo8UkVTVUxUUyB4bWxucz0iWE1MIiBzZXRzPSIxIj4KCiAgPFJFU1VMVCBjb2xzPSI1MCIgZGF0YXR5cGU9Ik9SRFNDSERMIiByb3dzPSIxIiBzZXRudW09IjEiPgogICAgPHJvdyByb3dudW09IjEiPgogICAgICA8Y29sIGNvbG51bT0iMSIgbmFtZT0iVEFTS19JRCIgdHlwZT0iIj44MDQwMzQwMDc1Nzg8L2NvbD4KICAgICAgPGNvbCBjb2xudW09IjIiIG5hbWU9Ik9TX0lEIiB0eXBlPSIiPjgwMDcwOTQyNjU4MzwvY29sPgogICAgICA8Y29sIGNvbG51bT0iMyIgbmFtZT0iUkVRX1RZUEUiIHR5cGU9IiI+Tk9STUFMPC9jb2w+CiAgICAgIDxjb2wgY29sbnVtPSI0IiBuYW1lPSJBQk5PUk1BTF9JRCIgdHlwZT0iIj4wPC9jb2w+CiAgICAgIDxjb2wgY29sbnVtPSI1IiBuYW1lPSJJU19DQU5DRUwiIHR5cGU9IiI+UzBGPC9jb2w+CiAgICAgIDxjb2wgY29sbnVtPSI2IiBuYW1lPSJST0xMX1RBU0tfSUQiIHR5cGU9IiI+MDwvY29sPgogICAgICA8Y29sIGNvbG51bT0iNyIgbmFtZT0iUkVRX0lEIiB0eXBlPSIiPjgwMzg2ODE5MjQ1MDwvY29sPgogICAgICA8Y29sIGNvbG51bT0iOCIgbmFtZT0iU1VCU19JRCIgdHlwZT0iIj44MTIxMTQyODc3NDY8L2NvbD4KICAgICAgPGNvbCBjb2xudW09IjkiIG5hbWU9IkNVU1RfSUQiIHR5cGU9IiI+ODI2NzU2MjU3MzwvY29sPgogICAgICA8Y29sIGNvbG51bT0iMTAiIG5hbWU9IlNFUlZfSUQiIHR5cGU9IiI+ODU0NTMyODIwNjwvY29sPgogICAgICA8Y29sIGNvbG51bT0iMTEiIG5hbWU9IlBST0RfQ09ERSIgdHlwZT0iIj5ZRERIPC9jb2w+CiAgICAgIDxjb2wgY29sbnVtPSIxMiIgbmFtZT0iQUNUSU9OX0NPREUiIHR5cGU9IiI+WURESF8wMDE8L2NvbD4KICAgICAgPGNvbCBjb2xudW09IjEzIiBuYW1lPSJQUklPUklUWSIgdHlwZT0iIj4xPC9jb2w+CiAgICAgIDxjb2wgY29sbnVtPSIxNCIgbmFtZT0iRVhDSEFOR0VfSUQiIHR5cGU9IiI+OTEwMDE8L2NvbD4KICAgICAgPGNvbCBjb2xudW09IjE1IiBuYW1lPSJPUF9TVEFGRiIgdHlwZT0iIj5zeXN0ZW08L2NvbD4KICAgICAgPGNvbCBjb2xudW09IjE2IiBuYW1lPSJSRU1BUksiIHR5cGU9IiI+PC9jb2w+CiAgICAgIDxjb2wgY29sbnVtPSIxNyIgbmFtZT0iUFJFV0FSTV9USU1FIiB0eXBlPSIiPjIwMTUwNDE3MDIzMTQ5PC9jb2w+CiAgICAgIDxjb2wgY29sbnVtPSIxOCIgbmFtZT0iV0FSTV9USU1FIiB0eXBlPSIiPjIwMTUwNDE3MTQzMTQ5PC9jb2w+CiAgICAgIDxjb2wgY29sbnVtPSIxOSIgbmFtZT0iQ09NUEhETF9TVEFURSIgdHlwZT0iIj5TMEY8L2NvbD4KICAgICAgPGNvbCBjb2xudW09IjIwIiBuYW1lPSJUSU1FX0xFViIgdHlwZT0iIj48L2NvbD4KICAgICAgPGNvbCBjb2xudW09IjIxIiBuYW1lPSJDSVRZX0lEIiB0eXBlPSIiPjIwMDwvY29sPgogICAgICA8Y29sIGNvbG51bT0iMjIiIG5hbWU9IlNXSVRDSFRZUEUiIHR5cGU9IiI+PC9jb2w+CiAgICAgIDxjb2wgY29sbnVtPSIyMyIgbmFtZT0iRFBDT0RFIiB0eXBlPSIiPjwvY29sPgogICAgICA8Y29sIGNvbG51bT0iMjQiIG5hbWU9IkNBQkNPREUiIHR5cGU9IiI+PC9jb2w+CiAgICAgIDxjb2wgY29sbnVtPSIyNSIgbmFtZT0iVFJVTktDT0RFIiB0eXBlPSIiPjwvY29sPgogICAgICA8Y29sIGNvbG51bT0iMjYiIG5hbWU9Ik9OVUNPREUiIHR5cGU9IiI+1tDOxDwvY29sPgogICAgICA8Y29sIGNvbG51bT0iMjciIG5hbWU9Ik9CRENPREUiIHR5cGU9IiI+PC9jb2w+CiAgICAgIDxjb2wgY29sbnVtPSIyOCIgbmFtZT0iWkhYQ09ERSIgdHlwZT0iIj48L2NvbD4KICAgICAgPGNvbCBjb2xudW09IjI5IiBuYW1lPSJMQU5fQ09ERSIgdHlwZT0iIj48L2NvbD4KICAgICAgPGNvbCBjb2xudW09IjMwIiBuYW1lPSJSRVFfQ09ERSIgdHlwZT0iIj5HWjIwMTUwNDE1Nzc1MTM3NzA2PC9jb2w+CiAgICAgIDxjb2wgY29sbnVtPSIzMSIgbmFtZT0iU1VCU19DT0RFIiB0eXBlPSIiPjIwMDE1MDQxNTkyNTUyNDM3NTwvY29sPgogICAgICA8Y29sIGNvbG51bT0iMzIiIG5hbWU9IkNVU1RfTkJSIiB0eXBlPSIiPjIwMjAzNTA1MjE5MzAwMDA8L2NvbD4KICAgICAgPGNvbCBjb2xudW09IjMzIiBuYW1lPSJTRVJWX05CUiIgdHlwZT0iIj5HWjIwMDAwMDAwODU0NTMyODIwNjwvY29sPgogICAgICA8Y29sIGNvbG51bT0iMzQiIG5hbWU9IkFDQ19OQlIiIHR5cGU9IiI+MTMzMTI4MTY3OTY8L2NvbD4KICAgICAgPGNvbCBjb2xudW09IjM1IiBuYW1lPSJPTlVfRlJPTSIgdHlwZT0iIj48L2NvbD4KICAgICAgPGNvbCBjb2xudW09IjM2IiBuYW1lPSJPTlVfRVFQSUQiIHR5cGU9IiI+PC9jb2w+CiAgICAgIDxjb2wgY29sbnVtPSIzNyIgbmFtZT0iT1JEX1NPVVJDRSIgdHlwZT0iIj5PUzwvY29sPgogICAgICA8Y29sIGNvbG51bT0iMzgiIG5hbWU9Ik9SRF9UWVBFIiB0eXBlPSIiPldPMDk4NzwvY29sPgogICAgICA8Y29sIGNvbG51bT0iMzkiIG5hbWU9IlNURVBfVFlQRSIgdHlwZT0iIj44OTYzPC9jb2w+CiAgICAgIDxjb2wgY29sbnVtPSI0MCIgbmFtZT0iRkVFX0hETCIgdHlwZT0iIj4xPC9jb2w+CiAgICA8L3Jvdz4KICA8L1JFU1VMVD4KCjwvUkVTVUxUUz4gIM==";
            String strDecrypt = Base64Cryption.decryptToStr(s);
            System.out.println("strDecrypt=" + strDecrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
