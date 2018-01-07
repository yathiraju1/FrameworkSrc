
  rem Set environmentvariable=%bamboo_Appscriptenvironment%
 rem  echo %bamboo_Appscriptenvironment%
 rem  echo %environmentvariable%
  
  cd C:\Users\ynanjaiah\workspace\Appscript_Development2

  set classpath=C:\Users\ynanjaiah\workspace\Appscript_Development2\bin;C:\Users\ynanjaiah\workspace\Appscript_Development2\LIB\*

  java org.testng.TestNG testng.xml
  
  timeout 15
  
 rem  xcopy /E /Y C:\frameworks\Appscript-staging\test-output C:\Bamboo\xml-data\build-dir\AP-GFES-JOB1\Target\Reports
  rem xcopy /S C:\frameworks\AppScript-Staging\test-output C:\Bamboo\xml-data\build-dir\AP-GFES-JOB1\Target\Reports /i /y
  rem pause
  
  rem pause

rem pause


rem java -cp C:\Users\ynanjaiah\Documents\AppScript-Backup\Appscript_24thMay\Appscript\Appscriptlib\Appscript lib\*;C:\Users\ynanjaiah\Documents\AppScript-Backup\Appscript_24thMay\Appscript\bin org.testng.TestNG.testng.xml


 rem java -cp   C:\Users\ynanjaiah\Documents\AppScript-Backup\Appscript_24thMay\Appscript\binC:\Users\ynanjaiah\Documents\AppScript-Backup\Appscript_24thMay\Appscript\Appscriptlib\Appscript lib\* org.testng.TestNG.testng.xml