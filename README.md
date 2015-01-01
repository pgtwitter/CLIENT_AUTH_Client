CLIENT_AUTH_Client
==================
クライアント認証対応のクライアント

* javax.net.ssl.trustStore
  * 信頼する認証局証明書(一覧)のキーストアファイルのパス
  * 指定がなければJava付属で配布されているcacertsキーストアファイルが利用される．
  * '${JAVA_HOME}/lib/security/cacerts'等にあるらしい．
* javax.net.ssl.trustStorePassword
  * 信頼する認証局証明書(一覧)のキーストアファイルのパスワード
* javax.net.ssl.trustStoreType
  * 信頼する認証局証明書(一覧)のキーストアファイルの形式('JKS', 'PKCS12')
* javax.net.ssl.keyStore
  * クライアント証明書が含まれるキーストアファイルのパス
* javax.net.ssl.keyStorePassword
  * クライアント証明書が含まれるキーストアファイルのパスワード
* javax.net.ssl.keyStoreType
  * クライアント証明書が含まれるキーストアファイルの形式('JKS', 'PKCS12')
