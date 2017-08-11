# cfdi33-core
SDK para generación de CFDI's version 3.3 del SAT

# Pre - requisitos
- Java 8
- Spring
- python 2.7.13
  - Instalar biblioteca M2Crypto

# Instalación
1. Se copia el archivo que contiene el certificado y el archivo de la llave privada en la ruta cfdi33-core/cfdi33-xml/src/main/resources/llaves/
2. Se actualiza el archivo de propiedades cfdi33-core/cfdi33-xml/src/main/resources/cfdi.properties:
  - Se actualiza la variable private.key.file.path con la ruta absoluta del archivo de llave privada
  - Se actualiza la variable private.key.pwd con el password de la llave privada
  - Se actualiza la variable cert.file.path con la ruta absoluta del archivo del certificado
3. Se ejecuta el script de python cfdi33-core/init.py, este script se encarga de actualizar las variables private.key.file.string, cert.file.string y cert.no.certificado tomando el certificado y la llave privada del punto 1.
  - python init.py
4. Ejecutar: mvn clean install

# Notas
1. No es necesario ejecutar el script de python, sin embargo se deberán actualizar las variables private.key.file.string, cert.file.string y cert.no.certificado manualmente.
2. La aplicación contiene llaves de prueba, si se cambian las llaves las pruebas de junit fallarán, hay dos opciones:
  - Omitir las pruebas: "mvn clean install -Dmaven.test.skip=true"
  - Actualizar las clases ComprobanteTest.java y CertificadoUtilTest.java con los valores adecuados de sello, cadena original, número de certificado, certificado y llave privada.
