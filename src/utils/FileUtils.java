package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.servlet.ServletContext;

public class FileUtils {
	
	/**
	 * Fungsi untuk membaca file dari folder webinf
	 * @param servletContext
	 * @param fileInputExt
	 * @return
	 */
	public static String readFileFromWebInf(ServletContext servletContext, String fileInputExt) {
		FileInputStream fis = null;
		String result = null;
		try {
			File fileData = new File(servletContext.getRealPath("/WEB-INF/files/")+fileInputExt);
			fis = new FileInputStream(fileData);
			byte[] plaintext = new byte[fis.available()];
			fis.read(plaintext);
			fis.close();
			result = new String(plaintext);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Fungsi untuk membuat file yang disimpan ke folder webinf
	 * @param servletContext
	 * @param fileInputExt
	 * @param fileOutputExt
	 */
	public static void createFileInWebInf(ServletContext servletContext,String fileInputExt, String fileOutputExt) {
		try {
			FileOutputStream fos = new FileOutputStream(servletContext.getRealPath("/WEB-INF/files/")+fileInputExt);
			fos.write(fileOutputExt.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  Fungsi untuk enkripsi pesan dengan thales
	 * @param servletContext
	 * @param plaintext
	 * @return
	 */
	public static String thalesEncrypt(ServletContext servletContext,String plaintext) {
		FileInputStream in = null;
		KeyStore ks = null;
		String result = null;
		try {
			in = new FileInputStream(servletContext.getRealPath("/WEB-INF/files/keystores/")+"sym.keystore");
			ks = KeyStore.getInstance("nCipher.sworld","nCipherKM");
			ks.load(in,null);
			in.close();
			SecretKey k = (SecretKey) ks.getKey("deskey",null);
			Cipher c1 = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			c1.init(Cipher.ENCRYPT_MODE,k);
			
			/* playtext encode */
			byte[] ciphertext = c1.doFinal(plaintext.getBytes());
			result = new String(ciphertext);
			
		} catch (KeyStoreException | 
				NoSuchProviderException | 
				NoSuchAlgorithmException | 
				CertificateException | 
				IOException | 
				UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Fungsi untuk dekripsi pesan thales
	 * @param servletContext
	 * @param plaintextEncode
	 * @return
	 */
	public static String thalesDecrypt(ServletContext servletContext,String plaintextEncode) {
		FileInputStream in = null;
		KeyStore ks = null;
		String result = null;
		try {
			in = new FileInputStream(servletContext.getRealPath("/WEB-INF/files/keystores/")+"sym.keystore");
			ks = KeyStore.getInstance("nCipher.sworld","nCipherKM");
			ks.load(in,null);
			in.close();
			SecretKey k = (SecretKey) ks.getKey("deskey",null);
			Cipher c1 = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			c1.init(Cipher.DECRYPT_MODE,k);
			
			/* playtext encode */
			byte[] ciphertext = c1.doFinal(plaintextEncode.getBytes());
			result = new String(ciphertext);
			
		} catch (KeyStoreException | 
				NoSuchProviderException | 
				NoSuchAlgorithmException | 
				CertificateException | 
				IOException | 
				UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
