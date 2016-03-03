###CLIENT_AUTH_Client
==================
####クライアント認証対応のクライアント

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

#### 利用条件

* いかなる種類の使用, 改造, 転載を行っても構いません.
* 改変, 部分的な利用したものはあなたのものになります. 公開するときはあなたの名前の元に公開してください.
* これによるいかなる損害も作者は責任を負わないものとします.
