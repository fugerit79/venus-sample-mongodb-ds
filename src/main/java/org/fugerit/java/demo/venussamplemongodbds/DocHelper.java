package org.fugerit.java.demo.venussamplemongodbds;

import org.fugerit.java.doc.freemarker.process.FreemarkerDocProcessConfig;
import org.fugerit.java.doc.freemarker.process.FreemarkerDocProcessConfigFacade;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DocHelper {

    private FreemarkerDocProcessConfig docProcessConfig = FreemarkerDocProcessConfigFacade.loadConfigSafe( "cl://venus-sample-mongodb-ds/fm-doc-process-config.xml" );

    public FreemarkerDocProcessConfig getDocProcessConfig() { return this.docProcessConfig; }

}
