@echo off
echo Building NoodleHaven...
call ant
if %ERRORLEVEL% NEQ 0 (
    echo Build failed!
    PAUSE
    exit /b %ERRORLEVEL%
)
echo Build successful! Launching client...
cd bin
java -Xms512m -Xmx3072m ^
  -Dsun.java2d.uiScale.enabled=false ^
  -Djava.net.preferIPv6Addresses=system ^
  -Dhaven.renderer=lwjgl ^
  --add-exports=java.base/java.lang=ALL-UNNAMED ^
  --add-exports=java.desktop/sun.awt=ALL-UNNAMED ^
  --add-exports=java.desktop/sun.java2d=ALL-UNNAMED ^
  --enable-native-access=ALL-UNNAMED ^
  -jar hafen.jar game.havenandhearth.com
PAUSE