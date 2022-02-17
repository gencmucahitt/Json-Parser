package jsonparser;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import product.ProductData;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

public class UserService {

    Map<String, String> hm = new HashMap<>();
    StringBuilder st = new StringBuilder();
    String ref = "c7c2de28d81d3da4a386fc8444d574f2";

    public UserService() {
        st.append("https://www.jsonbulut.com/json/");
    }

       public ProductData productResult(int input){

        try {
            st.append("product.php");
            hm.put("ref",ref);
            hm.put("start", String.valueOf(input));
            hm.put("count","3");

            String stData = Jsoup.connect(st.toString()).data(hm).ignoreContentType(true).timeout(15000).sslSocketFactory(socketFactory()).get().body().text();
            Gson gson = new Gson();
            return gson.fromJson(stData,ProductData.class);

        }catch (Exception ex){
            System.err.println("Product hatası"+ex);

        }

        return null;
    }

    static private SSLSocketFactory socketFactory() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            SSLSocketFactory result = sslContext.getSocketFactory();
            return result;
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to create a SSL socket factory", e);
        }
    }
}
