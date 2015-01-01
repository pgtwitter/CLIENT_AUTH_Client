#!/bin/bash -x
#自己署名ルートCA(myCA0|CA-for-Server)によるサーバ証明書の作成

rm -rf certs_server
mkdir certs_server
cd certs_server

mkdir ca
mkdir server
mkdir -p demoCA/newcerts
touch demoCA/index.txt
echo "01" > demoCA/serial

#[CA]
openssl req -keyout ca/cakey.pem -out ca/cacert.pem -new -x509 -newkey rsa:2048
#cp -prf /Library/Java/JavaVirtualMachines/jdk1.8.0_25.jdk/Contents/Home/jre/lib/security/cacerts ca/trustcacerts_keystore.jks 
#上記cacertsのパスワードは'changeit'.
keytool -import -file ca/cacert.pem -trustcacerts -alias myCA0 -keystore ca/trustcacerts_keystore.jks

#[Server]
keytool -genkey -keyalg RSA -keysize 2048 -alias localhost -keystore server/server_keystore.jks -dname "CN=localhost, O=CA-for-Server, ST=Some-State, C=JP"
keytool -certreq -alias localhost -keystore server/server_keystore.jks -file server/server.csr
openssl ca -keyfile ca/cakey.pem -cert ca/cacert.pem -in server/server.csr -out server/server.crt
keytool -import -file ca/cacert.pem -alias myCA0 -keystore server/server_keystore.jks
keytool -import -file server/server.crt -alias localhost -keystore server/server_keystore.jks