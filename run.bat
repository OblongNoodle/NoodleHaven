@echo off
echo Checking for updates...
if not exist "build" mkdir build
if not exist "build\ver" echo. 2>nul > build\ver
cd build
java -jar ..\hafen-updater.jar update https://raw.githubusercontent.com/OblongNoodle/NoodleHaven/update/
if not exist "hafen.jar" (
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
  -jar hafen.jar game.havenandhearth.com