package sslclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.UnknownHostException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class CLIENT_AUTH_Client {
	private static void connectAndSend(String host, int port, String req)
			throws UnknownHostException, IOException {
		SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory
				.getDefault();
		SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket(host,
				port);
		String protocols[] = {
				"TLSv1", "TLSv1.1", "TLSv1.2"
		};
		sslsocket.setEnabledProtocols(protocols);

		try {
			PrintWriter out = new PrintWriter(sslsocket.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(
					sslsocket.getInputStream()));
			out.write("GET\t" + req + "\tHTTP/1.1\nHost:\t" + host + ":" + port
					+ "\n\n");
			out.flush();
			do {
				String receive = in.readLine();
				System.out.println("receive:" + receive);
			} while (in.ready());
		} finally {
			sslsocket.close();
		}
	}

	private static void init_ssl_properties(String trustStore_OR_keyStore,
			String keyStore, String storePassword) {
		String store = trustStore_OR_keyStore; // trustStore | keyStore
		String storeType = keyStore.endsWith(".p12") ? "PKCS12" : "JKS";
		System.setProperty("javax.net.ssl." + store, keyStore);
		System.setProperty("javax.net.ssl." + store + "Password", storePassword);
		System.setProperty("javax.net.ssl." + store + "Type", storeType);
	}

	private static void init_trust_ca_certs() {
		// String file =
		// "${JAVA_HOME}/lib/security/cacerts";

		// String file =
		// "/Library/Java/JavaVirtualMachines/jdk1.8.0_25.jdk/Contents/Home/jre/lib/security/cacerts";

		String file = "cert_server/ca/trustcacerts_keystore.jks";

		if (!(new File(file)).exists()) {
			System.err.println("信頼する認証局証明書(一覧)が含まれるキーストアファイルが見つかりません("
					+ (new File(file)).getAbsolutePath());
			System.exit(1);
		}
		String path = (new File(file)).getAbsolutePath();
		String pass = "changeit";
		init_ssl_properties("trustStore", path, pass);
	}

	private static void init_client_cert() {
		String file = "cert_client/client/client_keystore.jks";
		if (!(new File(file)).exists()) {
			System.err.println("クライアント証明書の含まれるキーストアファイルが見つかりません("
					+ (new File(file)).getAbsolutePath());
			System.exit(1);
		}
		String path = (new File(file)).getAbsolutePath();
		String pass = "clientpass";
		init_ssl_properties("keyStore", path, pass);
	}

	public static void main(String[] arstring) throws UnknownHostException,
			IOException {
		init_trust_ca_certs(); // 受け入れるサーバ証明書の設定
		init_client_cert(); // 送付するクライアント証明書の設定
		connectAndSend("localhost", 8443, "/");
	}
}
