package aws;

import java.net.URI;

import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.http.apache.ProxyConfiguration;

public class ProxyClient {

	/**
	 * Proxyを使用する場合は、
	 * 各client.builder().httpClient(proxy).build();
	 * というように使う
	 * @return
	 */
	public static SdkHttpClient getProxy() {
		try {
			return ApacheHttpClient.builder().proxyConfiguration(
					ProxyConfiguration.builder().endpoint(
							new URI("http://hoge:8080")).build()).build();
					
		}catch (Exception e) {
			// ここに到達することは無い
			return null;
		}
	}
}
