package test.org.fugerit.java.demo.venussamplemongodbds;

import io.quarkus.test.junit.QuarkusTestProfile;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.MountableFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MongoContainerProfile  implements QuarkusTestProfile {

    final GenericContainer mongoDBContainer = new GenericContainer( "mongo:8.0" )
            .withCopyToContainer(MountableFile.forHostPath( new File( "src/test/resources/mongo-db/mongo-init.js" ).getPath() ), "/docker-entrypoint-initdb.d/mongo-init.js" )
            .withExposedPorts( 27017 );

    private String mongoUrl;

    public MongoContainerProfile() {
        mongoDBContainer.start();
        int port = mongoDBContainer.getFirstMappedPort();
        this.mongoUrl = String.format( "mongodb://localhost:%s/venus-sample-mongodb-ds", port );
        log.info( "mongodbUrl : {}" , mongoUrl );
    }

    @Override
    public Map<String, String> getConfigOverrides() {
        Map<String, String> config = new HashMap<>();
        config.put( "quarkus.mongodb.connection-string", this.mongoUrl );
        return config;
    }

}