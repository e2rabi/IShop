package com.errabi.ishop.services;

import com.errabi.common.exception.IShopException;
import com.errabi.ishop.entities.Role;
import com.errabi.ishop.entities.User;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jose.jwk.*;
import com.nimbusds.jwt.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

/**
 * Token security operations like create,verify operations.
 */
@Service
public class TokenService {

    private final static String ISSUER = "ERRABI";

    @Value("${ishop.jwt.keystore-path}")
    private String keystorePath ;
    @Value("${ishop.jwt.keystore-passe}")
    private char[] keystorePassword ;
    @Value("${ishop.jwt.key-passe}")
    private char[] keyPass ;
    @Value("${ishop.jwt.key-alias}")
    private String keyAlias;
    @Value("${ishop.jwt.expire-seconds}")
    private long expireInSc;

    public JWKSet getJwkSet() throws NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException, JOSEException {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream(ResourceUtils.getFile(keystorePath)),keystorePassword);

        JWK jwk = JWK.load(keyStore, keyAlias, keystorePassword);
        return new JWKSet(jwk);
    }

    public String newToken(User user) throws JOSEException, KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
        JWSSigner signer = new RSASSASigner(getJwkSet()
                                    .getKeyByKeyId(keyAlias)
                                    .toRSAKey().toPrivateKey());

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                                                .subject(ISSUER)
                                                .issuer(ISSUER)
                                                .claim("userName",user.getUsername())
                                                .claim("scope",user.getAuthorities())
                                                .expirationTime(new Date(new Date().getTime() + 60 * expireInSc))
                                                .build();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(getJwkSet().getKeyByKeyId(keyAlias)
                        .toRSAKey()
                        .getKeyID())
                        .build(),
                claimsSet);

        signedJWT.sign(signer);

        return signedJWT.serialize();
    }


    public Map<String, Object> verify(final String token) throws UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, JOSEException, ParseException {

        var signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new RSASSAVerifier(getJwkSet().getKeyByKeyId(keyAlias).toRSAKey().toRSAPublicKey());

        signedJWT.verify(verifier);

        var expiryDate = signedJWT.getJWTClaimsSet().getExpirationTime().toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDateTime();

        if(LocalDateTime.now().isAfter(expiryDate)){
            throw new IShopException("Token expired");
        }
        return signedJWT.getJWTClaimsSet().getClaims();
    }




}