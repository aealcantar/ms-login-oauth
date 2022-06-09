package gob.mx.imss.mspad.oauth.security;

/**
 * @Author Itzi B. Enriquez R. LT
 * @Date   27 abr. 2022
 * @IMSS
 */
public class JwtConfig {
	
	public static final String LLAVE_SECRETA = "E4D$hY$AS&W.";
	
	public static final String RSA_PRIVADA = "-----BEGIN RSA PRIVATE KEY-----\r\n" + 
			"MIIEpAIBAAKCAQEA1lOBVjOguBfXlos9KNQj3kp7I2IT1kcDRuZkZk30AyXLmmF1\r\n" + 
			"iQV++NbnHMyvEYHniartE57MaAwgdwkMjrDV0u1RRiznXafcI0oAvkdCgOR4o0hD\r\n" + 
			"v/eJ152zhttOh0OVRYA4UgEw03zPSvwH2/5ca6ukQK/ZDnle5l1e6BCMa0C/3ZGq\r\n" + 
			"6QVmCH7IxuQ46Bfh7EHMx+J7A3KgXCtmYyDmmrgRgmzfBBv7r8NVwRvGYFAA9VT8\r\n" + 
			"wvB8YeSB067QLRL+6Saqpte+2o9h9YZjLAXeB9fcoSmSIIup56uBEh6xbhf0X8MH\r\n" + 
			"t6fb8M0SOe1oXWSTMF04ATPA00lUxawSBQaZRwIDAQABAoIBAQCQqlTGppp5KD6w\r\n" + 
			"stNZV7fON1/ehjj1jYE8vg5Or5dg+IXxco8vKbKGrCfbXJ1ep9xtJB5Z+/nk0Sqo\r\n" + 
			"rkb3mYz6jo0pXO3eXYtNlonh5Z+R+KUlMYXpcWBtVVj0Udszmf6Lpvxc/cjDN5+D\r\n" + 
			"8ZIsgAhJ9NUthIRyLKJNxABni6P8T7+TpGVdRmwXNgYRKl7h0ryrp34i+gUS8ZrG\r\n" + 
			"LTmn8PBHBAxVFCwav5XelLqagILuHdJls81wDYN/Eb7YZ8itzBlMRr+Oida4Dcn5\r\n" + 
			"UGQ5PCBnbnIfN0JiGELtgt0PtJa1P3JwaGFQjYJ0bzPHyVl93XadNVpVOd/RfsVh\r\n" + 
			"33Y7hU+BAoGBAPZdOizfUo11+Vphgr1CTv3+lnWZoZoAbzQEkMORP1rQcLU5rpm6\r\n" + 
			"rPX/ya8WjOijorCoS0x2j0MtmjtIOLV09JNPAA7H+/JHeyVz7wVJ+rln834xb+NP\r\n" + 
			"6bKdM6cZaKfZwHfz1KtEU/scNqiBt5VIHAUNHbcxriFvhuNI7mRguIC3AoGBAN61\r\n" + 
			"fbRtZpYyKiYnbsUeugO4fwAskAv4TaCow08bngsnYWIcbxiAKJ+0lwIVmieE+in4\r\n" + 
			"O17W4junlgGwQJoM4ma9IMbB5EOjyhRrB+f6WPWFEczwJUSl1r0hpZntf2SQ22Ns\r\n" + 
			"B9CJAGMZa/r7kOghNgKs3dGhaKhf/pb79efR7vvxAoGAGwdDWKTvk6F7x9Sm2Uir\r\n" + 
			"Deaebyx5tEGT8asGjs4imz3+/4Liiu8WBq8XW2Qn9WXudV8hU7Q2J3KbVyGnRxEb\r\n" + 
			"vAWQxg5er8fz1LoRYQ0quqiS6CdYVSHxS60/e3eoKbIUEZToohmO9R+1R2tjzrbo\r\n" + 
			"jA1SU0TaC8UGkWzw6oZJ96UCgYEAxJt0XdQ2vGGM1CH7qAl4LdCyTXUJtYd/AFX8\r\n" + 
			"FdpLq40Ya4SujvKU6miC51QqwriiIGsD/rW1AvTveI4ged7l/8z3IoAJA62UuHHE\r\n" + 
			"iQZ5gkX3cPSP8gp1iIIrr7f6vs0i4KJ+Q5quvM20hd2QD11CURpKuMXFnt8f4iqM\r\n" + 
			"7Bpo4AECgYAvCJNVRtrgolYAR44ofiYVdhwMOk8mQnO1UshNBzsPBCeMKk0+8M3Y\r\n" + 
			"W3y2YE70B8hUBJQsIfD3bGuK9Gt5QVWo9qZUQfIK3wvv7HAMaGzS5C4QPjBsYbi8\r\n" + 
			"0pxtaBbCozPlxmf9uxOKrYndmjXWLoviVhTCKB+DLj6zU0Rl/B0vaw==\r\n" + 
			"-----END RSA PRIVATE KEY-----";
	
	public static final String RSA_PUBLICA = "-----BEGIN PUBLIC KEY-----\r\n" + 
			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1lOBVjOguBfXlos9KNQj\r\n" + 
			"3kp7I2IT1kcDRuZkZk30AyXLmmF1iQV++NbnHMyvEYHniartE57MaAwgdwkMjrDV\r\n" + 
			"0u1RRiznXafcI0oAvkdCgOR4o0hDv/eJ152zhttOh0OVRYA4UgEw03zPSvwH2/5c\r\n" + 
			"a6ukQK/ZDnle5l1e6BCMa0C/3ZGq6QVmCH7IxuQ46Bfh7EHMx+J7A3KgXCtmYyDm\r\n" + 
			"mrgRgmzfBBv7r8NVwRvGYFAA9VT8wvB8YeSB067QLRL+6Saqpte+2o9h9YZjLAXe\r\n" + 
			"B9fcoSmSIIup56uBEh6xbhf0X8MHt6fb8M0SOe1oXWSTMF04ATPA00lUxawSBQaZ\r\n" + 
			"RwIDAQAB\r\n" + 
			"-----END PUBLIC KEY-----";

}
