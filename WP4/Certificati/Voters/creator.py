# https://gist.github.com/fntlnz/cf14feb5a46b2eda428e000157447309

import os
import names
import random

crt_dir = "../Authorities/"

rand_names = set()
while len(rand_names) != 10:
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
        "Trentino_Alto_adige",
        "Umbria",
        "Valle_d_Aosta",
        "Veneto"
    }

# os.system('rm *.crt *.p8 *.key')

for voter in rand_names:

    print(f'### CRT for {voter} ###')

    region = random.sample(regioni,1)[0]

    # crea la chiave per ogni votante
    print('> Creating key')
    os.system(f'openssl ecparam -name prime256v1 -genkey -noout -out {voter}.key')
    
    # crea il csr
    print('> Creating csr')
    os.system(f'openssl req -new -key {voter}.key -subj "/C=IT/ST=Italia/O=Regione {region}/CN={voter}" -out {voter}.csr -extensions v3_ca')

    # autenticala con la root CA
    print('> CA authentication')
    os.system(f'openssl x509 -req -in {voter}.csr -CA {crt_dir+region}.crt -CAkey {crt_dir+region}.key -CAcreateserial -out {voter}.crt -days 365 -extensions v3_ca')
    os.remove(voter + '.csr')
    
    print('> Creating p8')
    os.system(f'openssl pkcs8 -topk8 -inform PEM -outform DER -in {voter}.key  -nocrypt > {voter}.p8')

    print(f'### DONE ###\n')