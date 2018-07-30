package pro.biocontainers.pipelines.utilities;

import org.apache.commons.codec.digest.DigestUtils;

public class GeneralUtils {

    public static String getHashName(String name){
        return DigestUtils.sha1Hex(name);
    }
}
