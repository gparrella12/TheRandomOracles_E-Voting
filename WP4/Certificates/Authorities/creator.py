# https://gist.github.com/fntlnz/cf14feb5a46b2eda428e000157447309, https://www.cockroachlabs.com/docs/stable/create-security-certificates-openssl.html
import os
def write_conf_file(r): 
    with open('conf/'+r+'.cnf', 'w') as f:
	    f.write("""# OpenSSL node configuration file
[ ca ]
default_ca = CA_default
[ CA_default ]
default_days = 365
database = ../Authorities/conf/index_"""+r+""".txt
serial = ../Authorities/conf/serial_"""+r+""".txt
default_md = sha256
copy_extensions = copy
unique_subject = no

[ req ]
prompt=no
distinguished_name = distinguished_name
req_extensions = extensions
default_md       = sha256 
    
[ distinguished_name ]
countryName         = IT
stateOrProvinceName         = Italia
localityName                = """+r+"""
organizationName = E-Voting system
organizationalUnitName = E-Voting system authority
commonName = Regione """+r+"""
emailAddress         ="""+r+"""@evoting.it

[ extensions ]
keyUsage = critical,digitalSignature,nonRepudiation,keyEncipherment,keyCertSign
basicConstraints = critical,CA:true,pathlen:2
# Used to sign node certificates.
[ signing_node_req ]
keyUsage = critical,digitalSignature,keyEncipherment

# Common policy for nodes and users.
[ signing_policy ]
organizationName = supplied
commonName = optional
countryName = optional
stateOrProvinceName = optional
organizationName = optional
organizationalUnitName = optional
emailAddress = optional

# Used to sign client certificates.
[ signing_client_req ]
keyUsage = critical,digitalSignature,keyEncipherment
""")

regioni = {"Abruzzo",
        "Basilicata",
        "Calabria",
        "Campania",
        "Emilia_Romagna",
        "Friuli_Venezia_Giulia",
        "Lazio",
        "Liguria",
        "Lombardia",
        "Marche",
        "Molise",
        "Piemonte",
        "Puglia",
        "Sardegna",
        "Sicilia",
        "Toscana",
        "Trentino_Alto_Adige",
        "Umbria",
        "Valle_d_Aosta",
        "Veneto"}
os.system('sudo rm -r keys/')
os.system('sudo rm -r certs/')  
os.system('sudo mkdir keys/') 
os.system('sudo mkdir certs/')       
os.system('sudo rm -f index.txt serial.txt')
os.system('sudo touch index.txt')
os.system("echo '01' > serial.txt")
# Generazione del certificato ROOT-CA per il ministero 
os.system('sudo openssl ecparam -name prime256v1 -genkey -noout -out keys/Ministero.key')
os.system('sudo openssl req -new -x509 -config ca.cnf -key keys/Ministero.key -out certs/Ministero.crt -days 365 -batch')
os.system('sudo openssl pkcs8 -topk8 -inform PEM -outform DER -in keys/Ministero.key  -nocrypt > keys/Ministero.p8')
print("ministero ok")
for r in regioni:
    # crea la chiave per ogni regione
    os.system('sudo openssl ecparam -name prime256v1 -genkey -noout -out keys/' + r + '.key')
     # esportala nel formato P8
    os.system('sudo openssl pkcs8 -topk8 -inform PEM -outform DER -in keys/'+r+'.key  -nocrypt > keys/' + r+'.p8')   
    
    # crea il csr 
    write_conf_file(r)
    os.system(f'sudo rm -f conf/index_{r}.txt conf/serial_{r}.txt')
    os.system(f'sudo touch conf/index_{r}.txt')
    os.system(f"echo '01' > conf/serial_{r}.txt")
    os.system(f'sudo openssl req -new -config conf/{r}.cnf -key keys/'+r+'.key -out certs/{r}.csr -batch')

    # autenticala con la root CA
    os.system(f'sudo openssl ca -config ca.cnf -keyfile keys/Ministero.key -cert certs/Ministero.crt -policy signing_policy -extensions signing_node_req -out certs/'+r+'.crt -outdir certs/ -in certs/{r}.csr -batch')
    os.system(f'sudo rm certs/{r}.csr')

    
    

