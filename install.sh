#!/bin/sh

# download jar
wget https://github.com/Project-Cepi/Sabre/releases/download/latest/sabre-1.0.0-all.jar -O sabre.jar

echo "#!/bin/bash
java -Xmx2024M -Xms2024M -jar sabre.jar" > run.sh

# give run.sh executable permissions
chmod +x ./run.sh

echo "Sucessfully installed sabre. Use ./run.sh to run the jar."