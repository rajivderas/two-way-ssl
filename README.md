# **two-way-ssl with Spring Boot** # 

### **Prerequisites** ###  
- Java 1.8
- Spring boot 2.5.2
- Java keytool utility

**Create A Self Signed Client Cert:**

`keytool -genkeypair -alias two-way-ssl-gateway -keyalg RSA -keysize 2048 -storetype JKS -keystore two-way-ssl-gateway.jks -validity 3650 -ext SAN=dns:localhost,ip:127.0.0.1` 

**Create Self Signed Server Cert:**

`keytool -genkeypair -alias two-way-ssl-ms -keyalg RSA -keysize 2048 -storetype JKS -keystore two-way-ssl-ms.jks -validity 3650 -ext SAN=dns:localhost,ip:127.0.0.1`

**Create public certificate file from client cert:**

`keytool -export -alias two-way-ssl-gateway -file two-way-ssl-gateway.crt -keystore two-way-ssl-gateway.jks`

**Create Public Certificate File From Server Cert:**

`keytool -export -alias two-way-ssl-ms -file two-way-ssl-ms.crt -keystore two-way-ssl-ms.jks`

**Import Client Cert to Server jks File:**

`keytool -import -alias two-way-ssl-gateway -file two-way-ssl-gateway.crt -keystore two-way-ssl-ms.jks`

**Import Server Cert to Client jks File:**

`keytool -import -alias two-way-ssl-ms -file two-way-ssl-ms.crt -keystore two-way-ssl-gateway.jks`

> Because it’s 2 way SSL. When we access gateway url in browser, our browser becomes the client to our gateway application and so, our gateway web app will ask the browser to present a cert for authentication.
To overcome this, we will have to import a cert to our browser. But our browser can’t understand a .jks file. Instead, it understands PKCS12 format. So, how do we convert .jks file to PKCS12 format? Again, keytool command to the rescue!!

`keytool -importkeystore -srckeystore two-way-ssl-ms.jks -destkeystore two-way-ssl-ms.p12 -srcstoretype JKS -deststoretype PKCS12 -srcstorepass two-way-ssl-ms -deststorepass two-way-ssl-ms -srcalias two-way-ssl-ms -destalias two-way-ssl-ms -srckeypass two-way-ssl-ms -destkeypass two-way-ssl-ms -noprompt`
