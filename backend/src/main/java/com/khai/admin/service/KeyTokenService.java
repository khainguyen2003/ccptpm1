package com.khai.admin.service;

import com.khai.admin.entity.security.Token;
import com.khai.admin.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class KeyTokenService {
    private TokenRepository tokenRepository;

    private final SecureRandom random = new SecureRandom();
    @Autowired
    public KeyTokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public String createToken(int userId, PublicKey publicKey) {
        Token newToken = new Token(userId, publicKey.toString());
        try {
            Token token = tokenRepository.save(newToken);
            return publicKey.toString();
        } catch(DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public KeyPair generateKeyPair() {
        KeyPairGenerator generator = null;
        try {
            generator = KeyPairGenerator.getInstance("RSA");
            KeyPair pair = generator.generateKeyPair();
            return pair;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    public String encrypt(String content, Key pubKey) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] contentBytes = content.getBytes();
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA");
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] cipherContent = cipher.doFinal(contentBytes);
        String encoded = Base64.getEncoder().encodeToString(cipherContent);
        return encoded;
    }

    public String decrypt(String cipherContent, Key privKey) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privKey);
        byte[] cipherContentBytes = Base64.getDecoder().decode(cipherContent.getBytes());
        byte[] decryptedContent = cipher.doFinal(cipherContentBytes);
        String decoded = new String(decryptedContent);
        return decoded;
    }

    public String encodeKey(Key key) {
        byte[] keyBytes = key.getEncoded();
        String encodedKeyStr = Base64.getEncoder().encodeToString(keyBytes);
        return encodedKeyStr;
    }

    public PublicKey decodePublicKey(String keyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Base64.getDecoder().decode(keyStr);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(spec);
        return key;
    }

    public PrivateKey decodePrivateKey(String keyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Base64.getDecoder().decode(keyStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        return key;
    }
}
