@echo off
cls

set DRIVE_LETTER=%1:
set arg0=%2
set arg1=%3
set arg2=%4
set arg3=%5

set PATH=%DRIVE_LETTER%\Java\bin;%DRIVE_LETTER%\Java\ant-1.9.9\bin;c:\Windows

::ant run -Ddrive-letter=%DRIVE_LETTER% -Darg0=%arg0% -Darg1=%arg1% -Darg2=%arg2% -Darg3=%arg3%
ant run -Ddrive-letter=%DRIVE_LETTER% -Darg0="cities.txt" -Darg1="city_mileage.txt" -Darg2=500 -Darg3=500
::ant run -Ddrive-letter=%DRIVE_LETTER% -Darg0="cities.txt" -Darg1="city_mileage.txt" -Darg2=10000 -Darg3=40
::ant run -Ddrive-letter=%DRIVE_LETTER% -Darg0="cities_20.txt" -Darg1="city_mileage_20.txt" -Darg2=10000 -Darg3=50