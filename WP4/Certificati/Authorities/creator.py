import os
# https://gist.github.com/fntlnz/cf14feb5a46b2eda428e000157447309
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
        "Trentino_Alto_adige",
        "Umbria",
        "Valle_d_Aosta",
        "Veneto"}

# Generazione del certificato ROOT-CA per il ministero 
#os.remove('keystore.jks')

os.system('openssl ecparam -name prime256v1 -genkey -noout -out Ministero.key')
os.system('openssl req -new -x509 -key Ministero.key -subj "/C=IT/ST=Italia/O=Ministero/CN=Ministero" -out Ministero.crt -days 365 -extensions v3_ca')
#os.system('keytool -importcert -file Ministero.crt -keystore keystore.jks -alias "Ministero" -storepass testevoting -noprompt')
os.system('openssl pkcs8 -topk8 -inform PEM -outform DER -in Ministero.key  -nocrypt > Ministero.p8')

for r in regioni:
    # crea la chiave per ogni regione
    os.system('openssl ecparam -name prime256v1 -genkey -noout -out ' + r + '.key')
    # crea il csr 
    #print('openssl req -new -sha256 -key ' +r+'.key -subj "/C=IT/ST=Italia/O=Regione '+r+'/CN='+r+' -out '+r+'.csr')
    os.system('openssl req -new  -key ' +r+'.key -subj "/C=IT/ST=Italia/O=Regione '+r+'/CN='+r+'" -out '+r+'.csr -extensions v3_ca')

    # autenticala con la root CA
    os.system('openssl x509 -req -in '+r+ '.csr -CA Ministero.crt -CAkey Ministero.key -CAcreateserial -out '+r+'.crt -days 365 -extensions v3_ca')
    os.remove(r+'.csr')
    
    #os.system('keytool -importcert -file '+r+'.crt -keystore keystore.jks -alias ' + r + ' -storepass testevoting -noprompt')
    os.system('openssl pkcs8 -topk8 -inform PEM -outform DER -in '+r+'.key  -nocrypt > ' + r+'.p8')

