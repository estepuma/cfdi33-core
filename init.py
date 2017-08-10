from M2Crypto import X509
import base64
from tempfile import mkstemp
from shutil import move, copy
from os import remove
import sys

encoded_string_private_file = ''
no_certificado = ''
encoded_string_certificado = ''
source_file_path = './cfdi33-xml/src/main/resources/cfdi.properties'
tests_file_path = './cfdi33-xml/src/test/resources/'

def initFile():
    global encoded_string_private_file
    global no_certificado
    global encoded_string_certificado

    print ('**** Init file ****')
    with open(source_file_path, 'r') as f:
        for line in f:
            if "private.key.file.path=" in line:
                variable_name, private_file_path = line.split("private.key.file.path=")
                path_without_new_line_pf = private_file_path[:-1]
                with open(path_without_new_line_pf, "rb") as private_file:
                    encoded_string_private_file = base64.b64encode(private_file.read())
                    print '- Archivo Llave privada codificado base64: ', encoded_string_private_file
                private_file.close()

            if "cert.file.path=" in line:
                variable_name, cert_path = line.split("cert.file.path=")
                path_without_new_line = cert_path[:-1]
                cert = X509.load_cert(path_without_new_line, X509.FORMAT_DER)
                numero_serie = '{0:x}'.format(int(cert.get_serial_number()))
                no_certificado = str(numero_serie[1::2])
                print '- No certificado: ', no_certificado

                with open(path_without_new_line, "rb") as cert_file:
                    encoded_string_certificado = base64.b64encode(cert_file.read())
                    print '- Certificado codificado base64: ', encoded_string_certificado
                cert_file.close()
    f.close()

def replace_variables():
    fh, target_file_path = mkstemp()
    with open(target_file_path, 'w') as target_file:
        with open(source_file_path, 'r') as source_file:
            for line in source_file:
                if "private.key.file.string" in line:
                    target_file.write("private.key.file.string=" + encoded_string_private_file + '\n')
                elif "cert.file.string" in line:
                    target_file.write("cert.file.string=" + encoded_string_certificado + '\n')
                elif "cert.no.certificado" in line:
                    target_file.write("cert.no.certificado=" + no_certificado + '\n')
                else:
                    target_file.write(line)
    remove(source_file_path)
    move(target_file_path, source_file_path)
    copy(source_file_path, tests_file_path)

initFile()
replace_variables()
