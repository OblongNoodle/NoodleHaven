@echo off
echo Checking for updates...

REM Create build directory if it doesn't exist
if not exist "build" mkdir build

REM Run updater from within build directory
cd build
java -jar ..\hafen-updater.jar update https://raw.githubusercontent.com/OblongNoodle/NoodleHaven/update/ -Djava.util.logging.config.file=..\logging.properties
cd ..

if not exist "build\hafen.jar" (
    echo ERROR: hafen.jar not found. Updater may have failed.
    pause
    exit /b 1
)

echo Launching NoodleHaven...
start "" javaw -Xms512m -Xmx3072m ^
  -Dsun.java2d.uiScale.enabled=false ^
  -Djava.net.preferIPv6Addresses=system ^
  -Dhaven.renderer=lwjgl ^
  --add-exports=java.base/java.lang=ALL-UNNAMED ^
  --add-exports=java.desktop/sun.awt=ALL-UNNAMED ^
  --add-exports=java.desktop/sun.java2d=ALL-UNNAMED ^
  --enable-native-access=ALL-UNNAMED ^
  -jar build/hafen.jar