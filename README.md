#CorpManager

CorpManager is a REST server from which several Eve Online API services are made available for Corporation directors.

Prerequesites
-------------
* Java 7+
* Tomcat 7+ : Optional (only if you use WAR file)

Installation
------------

1. Create **application.properties** file as below :

        # Character Id with enough rights (director) in order to expose corporation API
        corpo.characterId=<Insert characterId here>
        # Eve API KeyId
        corpo.keyId=<Insert Corpo keyId here>
        # Eve API vCode
        corpo.vCode=<Insert Corpo vCode here>

        server.context-path=/corp-manager
        spring.datasource.url=jdbc:hsqldb:file:<Insert where you want the database file>/db_corpmgr/corpmgrDB
        spring.jpa.hibernate.ddl-auto=update

2. Install in Tomcat 7 or 8

        cp corp-manager.war $CATALINA_HOME/webapps

3. Use embedded Tomcat 8. Make sure to have your application.properties file in the same directory as the WAR file and then :

        jar -jar corp-manager.war
