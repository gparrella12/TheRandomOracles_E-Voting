# https://gist.github.com/fntlnz/cf14feb5a46b2eda428e000157447309

import os
import names
import random

crt_dir = "../Authorities/certs/"
key_dir = "../Authorities/keys/"

rand_names = set()
while len(rand_names) != 1000:
    rand_names.add(names.get_first_name())

with open(".voters_list.txt", "w") as outStream:
    for v in rand_names:
        outStream.write(str(v) + '\n')

regioni = {
        "Abruzzo",
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
        "Veneto"
    }

os.system('sudo rm -r keys/')
os.system('sudo rm -r certs/')  
os.system('sudo mkdir keys/') 
os.system('sudo mkdir certs/')   
for voter in rand_names:

    print(f'### CRT for {voter} ###')

    region = random.sample(regioni,1)[0]

    # crea la chiave per ogni votante
    print('> Creating key')
    os.system(f'sudo openssl ecparam -name prime256v1 -genkey -noout -out keys/{voter}.key')
    
    # crea il csr
    print('> Creating csr')
    os.system(f'sudo openssl req -new -config voter.cnf -key keys/{voter}.key -subj "/C=IT/ST=Italia/O=Regione {region}/CN={voter}" -out certs/{voter}.csr')

    # autenticala con la root CA
    print('> CA authentication')
    os.system(f'sudo openssl ca -config ../Authorities/conf/{region}.cnf -keyfile ../Authorities/keys/{region}.key -cert ../Authorities/certs/{region}.crt -policy signing_policy -extensions signing_node_req -out certs/{voter}.crt -outdir certs/ -in certs/{voter}.csr -batch')
    os.system('sudo rm certs/'+voter + '.csr')
    
    print('> Creating p8')
    os.system(f'sudo openssl pkcs8 -topk8 -inform PEM -outform DER -in keys/{voter}.key  -nocrypt > keys/{voter}.p8')
    print(f'### DONE ###\n')
