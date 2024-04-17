package com.khai.admin.service;

import com.khai.admin.entity.User;
import com.khai.admin.entity.security.KeyStore;
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
import java.util.Optional;
import java.util.UUID;

@Service
public class KeyTokenService {
    private TokenRepository tokenRepository;

    private final SecureRandom random = new SecureRandom();
    @Autowired
    public KeyTokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public KeyStore save(KeyStore keyStore) {
        try {
            tokenRepository.save(keyStore);
            return keyStore;
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
            generator.initialize(512);
            KeyPair pair= generator.generateKeyPair();
            return pair;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteTokenById(UUID id) {
        this.tokenRepository.deleteById(id);
    }

    public KeyStore getKeyStoreByUserId(UUID userId) {
        Optional<KeyStore> keyStore = tokenRepository.findByUserId(userId);
        if(keyStore.isPresent()) {
            return keyStore.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy keystore");
        }
    }

    public String convertToPEM(byte[] keyBytes, String keyType) {
        String encoded = java.util.Base64.getEncoder().encodeToString(keyBytes);
        StringBuilder pem = new StringBuilder();
        pem.append("-----BEGIN ").append(keyType).append("-----\n");
        pem.append(encoded).append("\n");
        pem.append("-----END ").append(keyType).append("-----");
        return pem.toString();
    }

    public String convertPublicKeyToPem(PublicKey publickey) {
        return convertToPEM(publickey.getEncoded(), "PUBLIC KEY");
    }
    public String convertPrivateKeyToPem(PublicKey privateKey) {
        return convertToPEM(privateKey.getEncoded(), "PUBLIC KEY");
    }

    public PublicKey convertPEMToPublicKey(String pemData) throws Exception {
        // Loại bỏ phần header và footer của chuỗi PEM
        pemData = pemData.replaceAll("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", ""); // Loại bỏ các khoảng trắng

        // Giải mã chuỗi Base64
        byte[] keyBytes = Base64.getDecoder().decode(pemData);
        return decodePublicKey(keyBytes);
    }

    public PrivateKey convertPEMToPrivateKey(String pemData) throws Exception {
        // Loại bỏ phần header và footer của chuỗi PEM
        pemData = pemData.replaceAll("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", ""); // Loại bỏ các khoảng trắng

        // Giải mã chuỗi Base64
        byte[] keyBytes = Base64.getDecoder().decode(pemData);
        return decodePrivateKey(keyBytes);
    }

//    public String handleRefreshToken(String refreshToken) {
//
//    }

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

    public PublicKey decodePublicKey(byte[] keyBytes) throws InvalidKeySpecException {
        PublicKey key = null;
        try {
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            key = keyFactory.generatePublic(spec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            return key;
        }
    }

    public PrivateKey decodePrivateKey(byte[] keyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        return key;
    }
}
