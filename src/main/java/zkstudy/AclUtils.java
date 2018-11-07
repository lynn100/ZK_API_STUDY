package zkstudy;

import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.security.NoSuchAlgorithmException;

public class AclUtils {
    public static String getDigestUserPwd(String id) throws NoSuchAlgorithmException {
        return DigestAuthenticationProvider.generateDigest(id);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String id = "sdsd:ads";
        String idDigested = getDigestUserPwd(id);
        System.out.println(idDigested);
    }
}
