package com.painter.util;

import org.apache.xerces.impl.dv.util.Base64;

public class Base64Sample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Base64 base64 = new Base64();
		String str = new String("TEST34543TES4534TTESTT35435445345ESTTESTTEST43543TEST");
		byte[] binaryData = str.getBytes();
		String afterEncode = base64.encode(binaryData);
		System.out.println(afterEncode);
		
		byte[] afterDecodebinaryData = base64.decode(afterEncode);
		
		String afterDecode ="";
		afterDecode = new String(afterDecodebinaryData);
		System.out.println(afterDecode);
	}

}
