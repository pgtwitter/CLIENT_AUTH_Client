#!/bin/bash -x
#自己署名ルートCA(myCA1|CA-for-Client)によるクライアント証明書の作成

rm -rf certs_client
mkdir certs_client
cd certs_client

mkdir ca
mkdir client
mkdir -p demoCA/newcerts
touch demoCA/index.txt
echo "01" > demoCA/serial

#[CA]
openssl req -keyout ca/cakey.pem -out ca/cacert.pem -new -x509 -newkey rsa:2048
#cp -prf /Library/Java/JavaVirtualMachines/jdk1.8.0_25.jdk/Contents/Home/jre/lib/security/cacerts ca/trustcacerts_keystore.jks
#上記cacertsのパスワードは'changeit'.
keytool -import -file ca/cacert.pem -trustcacerts -alias myCA1 -keystore ca/trustcacerts_keystore.jks

#[Client]
keytool -genkey -keyalg RSA -keysize 2048 -alias client -keystore client/client_keystore.jks -dname "CN=client, O=CA-for-Client, ST=Some-State, C=JP"
keytool -certreq -alias client -keystore client/client_keystore.jks -file client/client.csr
openssl ca -keyfile ca/cakey.pem -cert ca/cacert.pem -in client/client.csr -out client/client.crt
keytool -import -file ca/cacert.pem -alias myCA1 -keystore client/client_keystore.jks
keytool -import -file client/client.crt -alias client -keystore client/client_keystore.jks

#jks to p12
keytool -importkeystore -srckeystore client/client_keystore.jks -destkeystore client/client_keystore.p12 -srcstoretype JKS -deststoretype PKCS12 -srcstorepass clientpass -deststorepass clientpass -srcalias client -destalias client -srckeypass clientpass -destkeypass clientpass -noprompt
