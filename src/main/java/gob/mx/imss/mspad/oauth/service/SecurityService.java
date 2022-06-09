package gob.mx.imss.mspad.oauth.service;


/**
 * @Author Itzi B. Enriquez R. LT
 * @Date   26 abr. 2022
 * @IMSS
 */
public interface SecurityService {

	byte[] decryptBytesWithPrivKey(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws Exception;
}
