package com.offcn.oauth.token;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenxi
 * @date 2021/11/17 16:02
 * @description
 */
public class CreateJwtTest {

    @Test
    public void testCreateToken(){

        //证书文件路径(放在resources目录下)
        String key_location="dongyimai.jks";
        //秘钥库密码
        String key_password="dongyimai";
        //秘钥密码
        String keypwd = "dongyimai";
        //秘钥别名
        String alias = "dongyimai";

        ClassPathResource resource = new ClassPathResource(key_location);

        KeyStoreKeyFactory keyFactory = new KeyStoreKeyFactory(resource, key_password.toCharArray());

        KeyPair keyPair = keyFactory.getKeyPair(alias, keypwd.toCharArray());

        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey)keyPair.getPrivate();

        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("id", "1");
        tokenMap.put("name", "ujiuye");
        tokenMap.put("roles", "ROLE_VIP,ROLE_USER");

        Jwt jwt = JwtHelper.encode(JSON.toJSONString(tokenMap), new RsaSigner(rsaPrivateKey));

       System.out.println(jwt.getEncoded());


    }

    @Test
    public void testParseToken(){

        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6IlJPTEVfVklQLFJPTEVfVVNFUiIsIm5hbWUiOiJ1aml1eWUiLCJpZCI6IjEifQ.ch0k2_ZezFjWl3WWtcL7_gtgb7hPQnzYBCBBv9YumWWYZd1qcfJsF0XitNpWyiqSFJuWrDnEkiwlU_mN3PLJdzD7IXmclLM9BjBJCEa57yAEKt1EXvG6PlAG4oAoNDIVjgvzzY00l-FgdCm29VxFJgLrV2_KCSVWy1n0Rw0uUUKrblubYgSPwOk7kwDE9PN0Ld-oixlXUkagMB-IPh7X5W919N0cafw6FKozqZnaLwm4veReMqLYxaKlhREIC-fi-9r2MmoSyWhlvpHDjo8BJBDn2DG449WESXKNthSyHOuHMfANF5x4Mn6WH3vanH0Fc9KdSV21ZoN3GI6IO9rF_Q";

        String pubKey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoXyIWC6cLrX+zhlN4M/YNS4MIZovGvlOnLnCYMGP/WmmSiv91dr4IABRqQEVX8jyt2Lfeu3eD86qH6p3x0Cs8wACWCVu17P5M8xjxuV5EBX91JhMQlg0R3RZ7RzoT5gu/ug6xZ/bJjpwH5DFmudGw0EVd8ySlOWX6eGSUs7jg6pufm16G+F0JEx5KhnPRqRnp+pWqeIlhTbnMtOby5uDCBNAYRx+WUPUthxYW6d6gJ39i6k69hZvEUtIN3w/X0TkYoZaPQrhw/k7KfHjc0ehlu0WlZQLxdwar1NCetRknQ9cS0K053GymUaCImf2JNyDhzGoizCIO1yDvXtBXMGwGQIDAQAB-----END PUBLIC KEY-----";

        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(pubKey));

        System.out.println(jwt.getEncoded());

        System.out.println(jwt.getClaims());

    }
}
