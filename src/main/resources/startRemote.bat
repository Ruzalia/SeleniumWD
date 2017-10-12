TITLE SeleniumServerHub
@set CHROME=-Dwebdriver.chrome.driver="%cd%\drivers\chromedriver.exe"
java %CHROME% -jar selenium-server-standalone-2.53.0.jar -port 4444 interactive